package com.example.dispositivosmoviles.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate((layoutInflater))
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        binding.buttonResultOk.setOnClickListener{
            val i = Intent()
            i.putExtra("result", "Resultado exitoso")
            setResult(RESULT_OK,i)

            finish()
        }

        binding.buttonResultFalse.setOnClickListener{
            val i = Intent()
            i.putExtra("result", "Resultado dudoso")
            setResult(RESULT_CANCELED,i)
            finish()
        }
    }
}