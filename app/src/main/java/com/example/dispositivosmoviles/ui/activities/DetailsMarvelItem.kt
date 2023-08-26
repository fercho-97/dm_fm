package com.example.dispositivosmoviles.ui.activities


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.data.entities.MarvelChars
import com.example.dispositivosmoviles.databinding.ActivityDetailsMarvelItemBinding
import com.example.dispositivosmoviles.logic.marvelLogic.MarvelComicLogic
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsMarvelItem : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsMarvelItemBinding
    private var marvelCharsItemsDB: MutableList<MarvelChars> = mutableListOf<MarvelChars>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsMarvelItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onStart() {
        super.onStart()

        val item = intent.getParcelableExtra<MarvelChars>("name")

        if (item != null) {
            binding.name.text = item.name
            Picasso.get().load(item.image).into(binding.imageMarvel)
            binding.imagelike.setOnClickListener {
                var checkInsert: Boolean = saveMarvelItem(
                    MarvelChars(
                        item.id,
                        binding.name.text.toString(),
                        item.comic,
                        item.image
                    )
                )
                if (checkInsert) {
                    // Mostrar la animación aquí
                    binding.imagelike.setAnimation(R.raw.joy)
                    binding.imagelike.playAnimation()

                    Snackbar.make(
                        binding.imageMarvel,
                        "Se agrego a favoritos",
                        Snackbar.LENGTH_SHORT
                    ).show()
                } else {
                    Snackbar.make(
                        binding.imageMarvel,
                        "No se puedo agregar o Ya esta agregado",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun saveMarvelItem(item: MarvelChars): Boolean {

        return if (item == null || marvelCharsItemsDB.contains(item)) {
            false
        } else {

            lifecycleScope.launch(Dispatchers.Main) {
                withContext(Dispatchers.IO) {
                    MarvelComicLogic().insertMarvelCharstoDB(listOf(item))
                    marvelCharsItemsDB = MarvelComicLogic().getAllMarvelCharsDB().toMutableList()
                }

            }
            true
        }

    }


    override fun onResume() {
        super.onResume()
        lifecycleScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                marvelCharsItemsDB = MarvelComicLogic().getAllMarvelCharsDB().toMutableList()
            }
        }
    }


}