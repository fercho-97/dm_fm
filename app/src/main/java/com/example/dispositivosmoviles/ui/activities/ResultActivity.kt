package com.example.dispositivosmoviles.ui.activities

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
            setResult(RESULT_OK)
            finish()
        }

        binding.buttonResultFalse.setOnClickListener{
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}