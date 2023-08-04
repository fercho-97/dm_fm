package com.example.dispositivosmoviles.ui.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.ActivityNotificationBinding
import com.example.dispositivosmoviles.databinding.ActivityProgressBinding
import com.example.dispositivosmoviles.ui.viewmodels.ProgressViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProgressActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProgressBinding
    private val progressviewmodel by viewModels<ProgressViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProgressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressviewmodel.progressSate.observe(this, {

            binding.progressBar.visibility = it
        })

        progressviewmodel.items.observe(this, {
            Toast.makeText(this, it[0].name, Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, NotificationActivity::class.java))
        })

        binding.buttonProgress.setOnClickListener{

            progressviewmodel.processBackgroud(3000)

        }

        binding.buttonProgress2.setOnClickListener{
            lifecycleScope.launch(Dispatchers.IO) {
                progressviewmodel.getMarvelChars(0,90)

            }

        }
    }
}