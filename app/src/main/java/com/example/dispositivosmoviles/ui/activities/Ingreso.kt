package com.example.dispositivosmoviles.ui.activities

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
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


        val appResultLocal = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ resultActivity->
            when(resultActivity.resultCode){
                RESULT_OK->{
                    Log.d("UCE", "Resultado exitoso")

                    Snackbar.make(binding.btIngreso,
                        "Resultado exitoso",
                        Snackbar.LENGTH_LONG).show()
                }
                RESULT_CANCELED->{

                    Log.d("UCE", "Resultado faliido")
                    Snackbar.make(binding.btIngreso,
                        "Resultado fallido",
                        Snackbar.LENGTH_LONG).show()
                }
                else->{
                    Log.d("UCE", "Resultado dudoso")
                    Snackbar.make(binding.btIngreso,
                        "Resultado dudoso",
                        Snackbar.LENGTH_LONG).show()
                }

            }

        }

        binding.imageButtonFacebbok.setOnClickListener{
            val resIntent = Intent(this, ResultActivity::class.java)
            appResultLocal.launch(resIntent)
        }
    }

    private suspend fun saveDataStore(stringData: String) {
        dataStore.edit { prefs ->
            prefs[stringPreferencesKey("usuario")] = stringData
        }


    }

}