package com.example.dispositivosmoviles.ui.activities


import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.IngresoFoodBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class Ingreso : AppCompatActivity() {

    private val GOOGLE_SIGN_IN = 100

    //variable que se inicializara despues
    //Se tiene que poner el binding del activity en la que esta
    private lateinit var binding: IngresoFoodBinding
    private lateinit var auth: FirebaseAuth

    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = IngresoFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        setUp()
        mediaPlayer = MediaPlayer.create(this, R.raw.sound_ingreso)
        mediaPlayer.start()
    }

    private fun setUp() {
        binding.btIngreso.setOnClickListener {
            val correo = binding.editxtCorreo.text.toString()
            val contrasena = binding.editxtContrasena.text.toString()

            if (correo.isNotEmpty() && contrasena.isNotEmpty()) {
                signInWithEmail(correo, contrasena)
            }
        }

        binding.btGooggle.setOnClickListener {
            signInWithGoogle()
        }

        binding.btRegistro.setOnClickListener {
            val rgIntent = Intent(this, RegistroActivity::class.java)
            startActivity(rgIntent)
        }
    }

    private fun signInWithEmail(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    CoroutineScope(Dispatchers.Main).launch {
                        val user = FirebaseAuth.getInstance().currentUser
                        val provider = ProviderType.BASIC
                        val photoUrl = user?.photoUrl?.toString()
                        Snackbar.make(
                            binding.btIngreso,
                            "Bienvenido ${user?.email}",
                            Snackbar.LENGTH_LONG
                        )
                            .show()
                        charge()
                        showHome(user?.email ?: "", provider, photoUrl)
                    }
                } else {
                    showAlert()
                }
            }
    }

    private fun signInWithGoogle() {
        val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val googleClient = GoogleSignIn.getClient(this, googleConf)
        googleClient.signOut()
        startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    val displayName = account.displayName
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener { authTask ->
                            if (authTask.isSuccessful) {
                                CoroutineScope(Dispatchers.Main).launch {
                                val user = FirebaseAuth.getInstance().currentUser
                                val provider = ProviderType.GOOGLE
                                val photoUrl = user?.photoUrl?.toString()
                                Snackbar.make(
                                    binding.btIngreso,
                                    "Bienvenido ${displayName}",
                                    Snackbar.LENGTH_LONG
                                )
                                    .show()
                                    charge()
                                showHome(user?.email ?: "", provider, photoUrl)
                                }
                            } else {
                                showAlert()
                            }
                        }
                }
            } catch (e: ApiException) {
                Log.d("GoogleSignIn", "Error: ${e.message}")
                showAlert()
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

    private fun showHome(email: String, provider: ProviderType, photoUrl: String?) {


        val homeIntent = Intent(this, Activity2::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
            putExtra("photoUrl", photoUrl)
        }
        startActivity(homeIntent)
    }

    suspend fun charge(){

        delay(2200)
    }
}