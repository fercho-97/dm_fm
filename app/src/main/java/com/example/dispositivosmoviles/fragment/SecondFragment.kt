package com.example.dispositivosmoviles.fragment

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dispositivosmoviles.data.entities.MarvelChars
import com.example.dispositivosmoviles.databinding.FragmentSecondBinding
import com.example.dispositivosmoviles.logic.marvelLogic.MarvelComicLogic
import com.example.dispositivosmoviles.ui.activities.DetailsMarvelItem
import java.util.Locale
import com.example.dispositivosmoviles.ui.adapter.MarvelAdaptersItems
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * A simple [Fragment] subclass.
 * Use the [SecondFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SecondFragment : Fragment() {

    private lateinit var binding: FragmentSecondBinding
    private lateinit var lmanager: LinearLayoutManager
    private var marvelCharacterItems: MutableList<MarvelChars> = mutableListOf<MarvelChars>()
    private lateinit var progressBar: ProgressBar
    private var rvAdapter: MarvelAdaptersItems =
        MarvelAdaptersItems({ sendMarvelItem(it) }, { saveMarvelItem(it) })

    val speechToText = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
        var message = ""
        when (activityResult.resultCode) {
            AppCompatActivity.RESULT_OK -> {
                // Devuelve el texto de voz
                val msg = activityResult.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0)

                if (!msg.isNullOrEmpty()) {
                    binding.txtBucar.text = Editable.Factory.getInstance().newEditable(msg)
                }
            }
            AppCompatActivity.RESULT_CANCELED -> {
                message = "Proceso cancelado"
            }
            else -> {
                message = "Ocurrió un error"
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(layoutInflater, container, false)
        lmanager = LinearLayoutManager(
            requireActivity(), LinearLayoutManager.VERTICAL, false
        )


        progressBar = binding.progressBar

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.txtBucar.addTextChangedListener { filteredText ->


            if (filteredText.toString().isNotEmpty()) {
                reset()
                chargeDataRV(filteredText.toString())

            } else {
                reset()
            }

        }

        binding.btnVoz.setOnClickListener {

            val intentSpeech = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intentSpeech.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            intentSpeech.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            intentSpeech.putExtra(RecognizerIntent.EXTRA_PROMPT, "Di algo...")
            speechToText.launch(intentSpeech)
        }

    }

    fun reset() {
        marvelCharacterItems = mutableListOf<MarvelChars>()
        rvAdapter.replaceListAdapter(marvelCharacterItems)
    }


    fun sendMarvelItem(item: MarvelChars) {
        val i = Intent(requireActivity(), DetailsMarvelItem::class.java)
        i.putExtra("name", item)
        i.putExtra("comic", item)
        i.putExtra("imagen", item)
        startActivity(i)
    }

    private fun saveMarvelItem(item: MarvelChars): Boolean {

        return if (item == null || marvelCharacterItems.contains(item)) {
            false
        } else {

            lifecycleScope.launch(Dispatchers.Main) {
                withContext(Dispatchers.IO) {
                    MarvelComicLogic().insertMarvelCharstoDB(listOf(item))
                    marvelCharacterItems = MarvelComicLogic().getAllMarvelCharsDB().toMutableList()
                }

            }
            true
        }

    }


    private fun chargeDataRV(nombre: String) {


        lifecycleScope.launch(Dispatchers.Main) {
            progressBar.visibility = View.VISIBLE
            marvelCharacterItems = withContext(Dispatchers.IO) {
                return@withContext (MarvelComicLogic().getMarvelChars(nombre, 5))
            } as MutableList<MarvelChars>
            if (marvelCharacterItems.size == 0) {
                var f = Snackbar.make(binding.txtBucar, "No se encontro", Snackbar.LENGTH_LONG)

                f.show()
            }
            rvAdapter.items =MarvelComicLogic().getMarvelChars(nombre, 10)

            binding.rvMarvel.apply {
                this.adapter = rvAdapter
                //  this.layoutManager = lmanager
                this.layoutManager = lmanager
            }
            progressBar.visibility = View.GONE

        }
    }

}