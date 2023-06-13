package com.example.dispositivosmoviles.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.compose.material3.Snackbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.Activity2Binding
import com.example.dispositivosmoviles.databinding.ActivityMainBinding
import com.example.dispositivosmoviles.fragment.FirstFragment
import com.example.dispositivosmoviles.fragment.SecondFragment
import com.example.dispositivosmoviles.fragment.ThirdFragment
import com.example.dispositivosmoviles.ui.utilities.FrgamentsManager
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.snackbar.Snackbar

class Activity2 : AppCompatActivity() {

    private lateinit var binding:Activity2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("UCE","Entrando a create")
        binding=Activity2Binding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart(){
        super.onStart()
        //Funcion de ambiente: permite recuperar informacion de otra acticity
        //extras.!! ->para confirmar que una dato nunca va hacer nulo
        //it?. -> para decir que un dato puede ser nulo
        var name:String=""
       // intent.extras.let {
       //     name=it?.getString("var1")!!

        //}
        Log.d("UCE","Entrando a start: ${name}")
        binding.textname.text="Bienvenido: "+name.toString()
        binding.boton1.setOnClickListener {
            var intent =Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.bar_inicio -> {
                    // Respond to navigation item 1 click

                    /*var sum = 0
                    for (i in 1..10){
                        sum++
                    }

                    Snackbar.make(binding.textname,"La suma es $sum", Snackbar.LENGTH_LONG
                    ).show()

                     */
/*
                    val frag = FirstFragment()
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(binding.frmContainer.id, frag)
                    transaction.addToBackStack(null)
                    transaction.commit()

 */
                    FrgamentsManager().replaceFrgament(supportFragmentManager,
                        binding.frmContainer.id,
                        FirstFragment()
                    )
                    true
                }
                R.id.bar_fav -> {
                    // Respond to navigation item 2 click
/*
                    var sum = 0
                    for (i in listOf(7,8,9,10)){
                        sum+= i
                    }

                    Snackbar.make(binding.textname,"La suma es $sum", Snackbar.LENGTH_LONG
                    ).show()

 */
                    /*
                    val frag2 = SecondFragment()
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(binding.frmContainer.id, frag2)
                    transaction.addToBackStack(null)
                    transaction.commit()


                     */
                    FrgamentsManager().replaceFrgament(supportFragmentManager,
                        binding.frmContainer.id,
                        SecondFragment()
                    )

                    true
                }
                R.id.bar_config ->{

                    val frag3 = ThirdFragment()
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(binding.frmContainer.id, frag3)
                    transaction.addToBackStack(null)
                    transaction.commit()


                    true
                }
                else -> false
            }
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }



}