package com.example.dispositivosmoviles.ui.activities


import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.dispositivosmoviles.databinding.ActivityBiometricBinding
import com.example.dispositivosmoviles.ui.viewmodels.BiometricViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class BiometricActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBiometricBinding

    private val biometricViewModel by viewModels<BiometricViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBiometricBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAutentication.setOnClickListener {
            autenticateBiometric()
        }

        biometricViewModel.isLoading.observe(this){isLoading->

            if (isLoading) {
                binding.lyMainOriginal.visibility = View.GONE
                binding.lyMainCopia.visibility = View.VISIBLE

            }else{

                binding.lyMainOriginal.visibility = View.VISIBLE
                binding.lyMainCopia.visibility = View.GONE
            }


        }
        lifecycleScope.launch {
            biometricViewModel.chargingData()
        }

    }

    private fun autenticateBiometric() {

        checkBiometric()
        if (checkBiometric()) {
            //Para contexto general
            val executor = ContextCompat.getMainExecutor(this)

            val biometricPrompt = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Autenticación requerida")
                .setSubtitle("Ingrese su huella digital")
                .setAllowedAuthenticators(BIOMETRIC_STRONG)
                .setNegativeButtonText("canccelar")
                .build()

            val biometricManager = BiometricPrompt(
                this,
                executor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                    }

                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        startActivity(Intent(this@BiometricActivity, CameraActivity::class.java))

                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                    }
                })

            biometricManager.authenticate(biometricPrompt)
        } else {
            Snackbar.make(binding.btnAutentication, "usuario denegado", Snackbar.LENGTH_SHORT)
                .show()

        }
    }


    private fun checkBiometric(): Boolean {
        Log.d("uce", "38")
        var returnValid: Boolean = false
        val biometricManager = BiometricManager.from(this)


        Log.d("uce", biometricManager.canAuthenticate(BIOMETRIC_STRONG).toString())
        when (biometricManager.canAuthenticate(
            BIOMETRIC_STRONG
        )) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                Log.d("uce", "1")
                returnValid = true
            }

            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                Log.d("uce", "2")
                returnValid = false
            }

            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                Log.d("uce", "3")
                returnValid = false
            }

            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                Log.d("uce", "Ingreso")
                val intentPrompt = Intent(Settings.ACTION_BIOMETRIC_ENROLL)
                intentPrompt.putExtra(
                    Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                    BIOMETRIC_STRONG
                )
                startActivity(intentPrompt)
                returnValid = false
            }
        }
        return returnValid
    }


}