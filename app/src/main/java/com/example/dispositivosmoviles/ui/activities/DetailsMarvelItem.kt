package com.example.dispositivosmoviles.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.data.entities.MarvelChars
import com.example.dispositivosmoviles.databinding.ActivityDetailsMarvelItemBinding
import com.example.dispositivosmoviles.databinding.MarvelCharterBinding
import com.squareup.picasso.Picasso

class DetailsMarvelItem : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsMarvelItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsMarvelItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        /*
        var name : String? = ""
        intent.extras?.let {
            name = it.getString("name")
        }
        if(!name.isNullOrEmpty()){
            binding.name.text = name
        }

         */

        val item = intent.getParcelableExtra<MarvelChars>("name")

        if(item != null){
            binding.name.text = item.name
            Picasso.get().load(item.image).into(binding.imageMarvel)
        }
    }
}