package com.example.dispositivosmoviles.ui.activities

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.IngresoFoodBinding
import com.example.dispositivosmoviles.fragment.FirstFragment
import com.example.dispositivosmoviles.logic.validator.LoginValidator
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class Ingreso : AppCompatActivity() {

    //variable que se inicializara despues
    //Se tiene que poner el binding del activity en la que esta
    private lateinit var binding: IngresoFoodBinding

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = IngresoFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onStart() {
        super.onStart()
        initClass()

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    //Forma binding
    @SuppressLint("ResourceAsColor")
    private fun initClass() {
        binding.btIngreso.setOnClickListener {

            val check = LoginValidator().checkLogin(
                binding.editxtCorreo.text.toString(),
                binding.editxtContrasena.text.toString()
            )

            if (check) {
                lifecycleScope.launch(Dispatchers.IO) {
                    saveDataStore(binding.editxtCorreo.text.toString())

                }
                var intent = Intent(this, MainActivity::class.java)
                intent.putExtra(
                    "var1", ""
                )
                startActivity(intent)
                intent.putExtra(
                    "var2", 2
                )
                startActivity(intent)
            } else {
                Snackbar.make(
                    binding.textTituloIngreso, "Usuario o contraseña invalido", Snackbar.LENGTH_LONG
                ).show()
            }


        }
        binding.imageButtonTwitter.setOnClickListener {
/*
            val intent = Intent(Intent.ACTION_VIEW,
                Uri.parse("geo:-0.31397749016612786, -78.4852154783297"))
            //"geo:-0.31397749016612786, -78.4852154783297"
            //"tel:0987654321"
            startActivity(intent)

 */

            val intent = Intent(Intent.ACTION_WEB_SEARCH)
            intent.setClassName(
                "com.google.android.googlequicksearchbox",
                "com.google.android.googlequicksearchbox.SearchActivity"
            )
            intent.putExtra(SearchManager.QUERY, "UCE")

            startActivity(intent)
        }


        val appResultLocal =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultActivity ->


                var color = R.color.black
                var message = when (resultActivity.resultCode) {

                    RESULT_OK -> {
                        // "Resultado exitoso"
                        color = resources.getColor(R.color.aqua)
                        resultActivity.data?.getStringExtra(
                            "result"
                        ).orEmpty()
                    }

                    RESULT_CANCELED -> {
                        //"Resultado fallido"
                        color = resources.getColor(R.color.red)
                        resultActivity.data?.getStringExtra(
                            "result"
                        ).orEmpty()
                    }

                    else -> {
                        "Resultado dudoso"
                    }
                }

                val sn = Snackbar.make(
                    binding.textTituloIngreso,
                    message,
                    Snackbar.LENGTH_LONG
                )

                sn.setBackgroundTint(color)
                sn.show()
                /*
                                when (resultActivity.resultCode) {
                                    RESULT_OK -> {
                                        Log.d("UCE", "Resultado exitoso")

                                        Snackbar.make(
                                            binding.btIngreso,
                                            "Resultado exitoso",
                                            Snackbar.LENGTH_LONG
                                        ).show()
                                    }

                                    RESULT_CANCELED -> {

                                        Log.d("UCE", "Resultado faliido")
                                        Snackbar.make(
                                            binding.btIngreso,
                                            "Resultado fallido",
                                            Snackbar.LENGTH_LONG
                                        ).show()
                                    }

                                    else -> {
                                        Log.d("UCE", "Resultado dudoso")
                                        Snackbar.make(
                                            binding.btIngreso,
                                            "Resultado dudoso",
                                            Snackbar.LENGTH_LONG
                                        ).show()
                                    }

                                }
                 */
            }

       /* val speechToText = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ activityResult->

            val sn = Snackbar.make(binding.textTituloIngreso, "", Snackbar.LENGTH_LONG)
            var message = ""

            when(activityResult.resultCode){
                RESULT_OK -> {
                    // "Resultado exitoso"
                    message = activityResult.data?.
                    getStringExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0).
                    toString()
                }
                RESULT_CANCELED -> {
                    message = "Proceso cancelado"
                    sn.setBackgroundTint(resources.getColor(R.color.red))

                }
                else->{
                    message = "Ocurrio un error"
                    sn.setBackgroundTint(resources.getColor(R.color.red))


                }


            }

        }*/
        val speechToText = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ activityResult->
            val sn=Snackbar.make(binding.textIngresoCorreo, "", Snackbar.LENGTH_LONG)
            var message=""
            when(activityResult.resultCode){
                RESULT_OK->{

                    //Devuelve el texto de voz
                    val msg = activityResult
                        .data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0)
                        .toString()

                    //Para hacer una consulta con la voz
                    if(msg.isNotEmpty()){
                        val intent = Intent(
                            Intent.ACTION_WEB_SEARCH
                        )
                        intent.setClassName(
                            "com.google.android.googlequicksearchbox",
                            "com.google.android.googlequicksearchbox.SearchActivity"
                        )
                        intent.putExtra(SearchManager.QUERY, msg)
                        startActivity(intent)
                    }



                    sn.setBackgroundTint(resources.getColor(R.color.aqua))
                }
                RESULT_CANCELED->{
                    message="Proceso cancelado"
                    sn.setBackgroundTint(resources.getColor(R.color.red))}
                else->{
                    message="Ocurrio un error"
                    sn.setBackgroundTint(resources.getColor(R.color.red))
                }

            }

            sn.setText(message)
            sn.show()
        }


        binding.imageButtonFacebbok.setOnClickListener {
            //val resIntent = Intent(this, ResultActivity::class.java)
            //appResultLocal.launch(resIntent)

            val intentSpeech = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intentSpeech.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )

            intentSpeech.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
            Locale.getDefault())

            intentSpeech.putExtra(RecognizerIntent.EXTRA_PROMPT,"DI ALGO...")
            speechToText.launch(intentSpeech)
        }
    }

    private suspend fun saveDataStore(stringData: String) {
        dataStore.edit { prefs ->
            prefs[stringPreferencesKey("usuario")] = stringData
        }


    }

}