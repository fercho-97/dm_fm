package com.example.dispositivosmoviles.ui.activities

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.location.Location

import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.speech.RecognizerIntent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.PermissionChecker.PermissionResult
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
import com.example.dispositivosmoviles.ui.utilities.MyLocationmanager
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


class Ingreso : AppCompatActivity() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    //variable que se inicializara despues
    //Se tiene que poner el binding del activity en la que esta
    private lateinit var binding: IngresoFoodBinding

    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    private var currentLocation: Location? = null

    private lateinit var client: SettingsClient
    private lateinit var locationSettingsRequest: LocationSettingsRequest


    private val speechToText =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            val sn = Snackbar.make(binding.textIngresoCorreo, "", Snackbar.LENGTH_LONG)
            var message = ""
            when (activityResult.resultCode) {
                RESULT_OK -> {

                    //Devuelve el texto de voz
                    val msg = activityResult
                        .data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0)
                        .toString()

                    //Para hacer una consulta con la voz
                    if (msg.isNotEmpty()) {
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

                RESULT_CANCELED -> {
                    message = "Proceso cancelado"
                    sn.setBackgroundTint(resources.getColor(R.color.red))
                }

                else -> {
                    message = "Ocurrio un error"
                    sn.setBackgroundTint(resources.getColor(R.color.red))
                }

            }

            sn.setText(message)
            sn.show()
        }

    @SuppressLint("MissingPermission")
    private val locationContract =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->

            when (isGranted) {

                true -> {
                    client.checkLocationSettings(locationSettingsRequest).apply {

                        addOnSuccessListener {

                            val task = fusedLocationProviderClient.lastLocation
                            task.addOnSuccessListener { location ->

                                fusedLocationProviderClient.requestLocationUpdates(
                                    locationRequest,
                                    locationCallback,
                                    Looper.getMainLooper()
                                )
                            }
                        }
                        addOnFailureListener { ex ->

                            if (ex is ResolvableApiException) {
                                // startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                                ex.startResolutionForResult(
                                    this@Ingreso,
                                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED
                                )
                            }
                        }
                    }
                }

                shouldShowRequestPermissionRationale(
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) -> {

                }

                false -> {
                    Snackbar.make(
                        binding.textIngresoCorreo,
                        "DENEGADO",
                        Snackbar.LENGTH_LONG
                    ).show()

                }

            }

        }


    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = IngresoFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)



        locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            1000,
        ).build()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)

                if (locationResult != null) {
                    locationResult.locations.forEach {
                        currentLocation = it
                        Log.d(
                            "UCE",
                            "Ubicaión: ${it.latitude}, " + "${it.longitude}"
                        )
                    }
                }
            }
        }
        client = LocationServices.getSettingsClient(this)
        locationSettingsRequest = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest).build()

    }

    override fun onStart() {
        super.onStart()
        initClass()

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)

    }

    //Forma binding
    @SuppressLint("ResourceAsColor", "MissingPermission")
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
            locationContract.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
            /*
                        val intent = Intent(Intent.ACTION_VIEW,
                            Uri.parse("geo:-0.31397749016612786, -78.4852154783297"))
                        //"geo:-0.31397749016612786, -78.4852154783297"
                        //"tel:0987654321"
                        startActivity(intent)

             */
            /*
                        val intent = Intent(Intent.ACTION_WEB_SEARCH)
                        intent.setClassName(
                            "com.google.android.googlequicksearchbox",
                            "com.google.android.googlequicksearchbox.SearchActivity"
                        )
                        intent.putExtra(SearchManager.QUERY, "UCE")



                        startActivity(intent)*/
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



        binding.imageButtonFacebbok.setOnClickListener {
            //val resIntent = Intent(this, ResultActivity::class.java)
            //appResultLocal.launch(resIntent)

            val intentSpeech = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intentSpeech.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )

            intentSpeech.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault()
            )

            intentSpeech.putExtra(RecognizerIntent.EXTRA_PROMPT, "DI ALGO...")
            speechToText.launch(intentSpeech)
        }
    }

    private suspend fun saveDataStore(stringData: String) {
        dataStore.edit { prefs ->
            prefs[stringPreferencesKey("usuario")] = stringData
        }


    }


    private  fun test(){

        var location = MyLocationmanager(this)


        location.getUserLocation()
    }

}