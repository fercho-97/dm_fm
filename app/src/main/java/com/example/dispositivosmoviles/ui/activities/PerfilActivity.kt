package com.example.dispositivosmoviles.ui.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.Manifest
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.Locale
import com.bumptech.glide.Glide
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.ActivityPerfilBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import java.io.IOException


enum class ProviderType {
    BASIC,
    GOOGLE
}

class PerfilActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPerfilBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private lateinit var localitation: FusedLocationProviderClient

    private val CAMERA_PERMISSION_REQUEST_CODE = 101
    private val CAMERA_IMAGE_REQUEST_CODE = 102


    private lateinit var userProvider: ProviderType



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")
        val photoUrl = bundle?.getString("photoUrl")
        setUp(email?:"",provider?:"", photoUrl?:"")
        userProvider = ProviderType.valueOf(provider ?: "BASIC")
        //Guardar Datos

        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("email", email)
        prefs.putString("provider", provider)
        prefs.putString("photoUrl", photoUrl)
        prefs.apply()



        binding.buttonChangePhoto.setOnClickListener {

            if (userProvider == ProviderType.BASIC) {
                checkCameraPermission()
            } else {
                val message = "Solo los usuarios BASIC pueden cambiar la foto de perfil."
                Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
            }
        }



        val defaultLocale = Locale.getDefault()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        requestLocationAndShowCity(defaultLocale)
    }



    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            openCamera()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_IMAGE_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as? Bitmap
            if (imageBitmap != null) {

                binding.profileImageView.setImageBitmap(imageBitmap)
            }
        }
    }
    //lf@uce.com

    private fun requestLocationAndShowCity(locale: Locale) {
        if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    location?.let {
                        // Obtener la ciudad
                        val cityName = getCityName(it.latitude, it.longitude, locale)
                        // Mostrar la ciudad en la vista
                        binding.editxtCiudad.text = "Ciudad: $cityName"
                    }
                }
        } else {

        }
    }

    private fun getCityName(latitude: Double, longitude: Double, locale: Locale): String {
        val geocoder = Geocoder(this, locale)
        var cityName = ""
        try {
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses != null) {
                if (addresses.isNotEmpty()) {
                    cityName = addresses?.get(0)?.locality ?: "Unknown City"
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return cityName
    }




    private fun setUp(email: String, provider: String, photoUrl: String) {
        title = "Inicio"

        binding.editxtCorreo.text = email
        binding.editxtType.text = provider


        if (photoUrl.isNotEmpty()) {
            Glide.with(this)
                .load(photoUrl)
                .into(binding.profileImageView)
        }

        binding.btSalir.setOnClickListener{
            val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, Ingreso::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

    }


}