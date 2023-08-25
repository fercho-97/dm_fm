package com.example.dispositivosmoviles.ui.activities

import android.app.AlertDialog
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.ActivityRegistroBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegistroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroBinding

    private lateinit var auth: FirebaseAuth

    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        setup()

        mediaPlayer = MediaPlayer.create(this, R.raw.sound_rg)
    }

    private fun setup() {
        binding.btRegistro.setOnClickListener {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                binding.editxtCorreo.text.toString(),
                binding.editxtContrasena.text.toString()
            ).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    CoroutineScope(Dispatchers.Main).launch {
                        Toast.makeText(
                            baseContext,
                            "Creacion exitosa.",
                            Toast.LENGTH_SHORT,
                        ).show()
                        mediaPlayer.start()
                        charge()
                        showHome()
                    }
                } else {
                    showAlert()
                }
            }
        }
    }
    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome() {

        val homeIntent = Intent(this, Ingreso::class.java)
        startActivity(homeIntent)
    }

    suspend fun charge(){

        delay(5000)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

}