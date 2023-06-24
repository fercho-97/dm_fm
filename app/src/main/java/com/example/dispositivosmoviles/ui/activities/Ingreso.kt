package com.example.dispositivosmoviles.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dispositivosmoviles.databinding.IngresoFoodBinding
import com.example.dispositivosmoviles.logic.validator.LoginValidator
import com.google.android.material.snackbar.Snackbar

class Ingreso : AppCompatActivity() {

    //variable que se inicializara despues
    //Se tiene que poner el binding del activity en la que esta
    private lateinit var binding: IngresoFoodBinding

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= IngresoFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
    override fun onStart(){
        super.onStart()
        initClass()

    }

    override fun onDestroy() {
        super.onDestroy()
    }
    //Forma binding
    private fun initClass(){
        binding.btIngreso.setOnClickListener {

            val check=LoginValidator().checkLogin(binding.editxtCorreo.text.toString(), binding.editxtContrasena.text.toString())

            if(check){
                var intent= Intent(this, Activity2::class.java)
                intent.putExtra("var1"
                    ,"")
                startActivity(intent)
                intent.putExtra("var2"
                    ,2)
                startActivity(intent)
            }else{
                Snackbar.make(
                    binding.textTituloIngreso,"Usuario o contraseña invalido", Snackbar.LENGTH_LONG
                ).show()
            }

        }

        }

}