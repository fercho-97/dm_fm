package com.example.dispositivosmoviles.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.compose.material3.Snackbar
import androidx.core.content.ContextCompat
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.Activity2Binding
import com.example.dispositivosmoviles.databinding.ActivityMainBinding
import com.example.dispositivosmoviles.fragment.FirstFragment
import com.example.dispositivosmoviles.fragment.SecondFragment
import com.example.dispositivosmoviles.fragment.ThirdFragment
import com.example.dispositivosmoviles.logic.convencionesLogic.ListItems
import com.example.dispositivosmoviles.ui.adapter.DatosAdapter
import com.example.dispositivosmoviles.ui.utilities.FragmentsManager
import com.google.android.material.carousel.CarouselLayoutManager
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

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount <= 1) {
            showLayout()
        }
        super.onBackPressed()
    }

    private fun showLayout() {
        binding.imgbtPerfil.visibility = View.VISIBLE
        binding.textView3.visibility = View.VISIBLE
        binding.rvdatos.visibility = View.VISIBLE

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
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.bar_inicio -> {

                    hideLayout()
                    FragmentsManager().replaceFragment(
                        supportFragmentManager,
                        binding.frmContainer.id,
                        FirstFragment()
                    )

                    true

                }
                R.id.bar_fav -> {

                    hideLayout()
                    FragmentsManager().replaceFragment(
                        supportFragmentManager,
                        binding.frmContainer.id,
                        SecondFragment()
                    )

                    true


                    true
                }
                R.id.bar_config ->{

                    hideLayout()
                    FragmentsManager().replaceFragment(
                        supportFragmentManager,
                        binding.frmContainer.id,
                        ThirdFragment()
                    )


                    true
                }
                else -> false
            }
        }

        binding.imgbtPerfil.setOnClickListener{
            val email = intent.getStringExtra("email")
            val provider = intent.getStringExtra("provider")
            val photoUrl = intent.getStringExtra("photoUrl")

            val perfilIntent= Intent(this, PerfilActivity::class.java).apply {
                putExtra("email", email)
                putExtra("provider", provider)
                putExtra("photoUrl", photoUrl)
            }
            startActivity(perfilIntent)

        }

        val datosAdapter = DatosAdapter(
            ListItems().getData()
        )

        val rvDatos = binding.rvdatos
        rvDatos.adapter=datosAdapter

        rvDatos.layoutManager= CarouselLayoutManager()

    }
    private fun hideLayout() {
        binding.rvdatos.visibility = View.GONE
        binding.textView3.visibility = View.GONE



    }


}