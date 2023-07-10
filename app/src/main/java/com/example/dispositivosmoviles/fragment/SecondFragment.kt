package com.example.dispositivosmoviles.fragment


import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.data.entities.MarvelChars
import com.example.dispositivosmoviles.databinding.FragmentSecondBinding
import com.example.dispositivosmoviles.logic.marvelLogic.MarvelComicLogic
import com.example.dispositivosmoviles.ui.activities.DetailsMarvelItem
import com.example.dispositivosmoviles.ui.adapter.MarvelAdapters
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
    /*
    private  lateinit var binding: FragmentSecondBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSecondBinding.inflate(
            layoutInflater, container,false

        )

        var like = false


        binding.imageView.setOnClickListener {
            like = likeAnimation(binding.imageView, R.raw.bandai_dokkan, like )
        }


        return binding.root
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_first, container, false)// se necesita 3 cosas ,
    }

    private fun likeAnimation(imageView: LottieAnimationView,
                              animation: Int,
                              like: Boolean) : Boolean {

        if (!like) {
            imageView.setAnimation(animation)
            imageView.playAnimation()
        } else {
            imageView.animate()
                .alpha(0f)
                .setDuration(200)
                .setListener(object : AnimatorListenerAdapter() {

                    override fun onAnimationEnd(animator: Animator) {

                        imageView.setImageResource(R.drawable.corazones_100)
                        imageView.alpha = 1f
                    }

                })

        }

        return !like
    }
*/

    private lateinit var binding: FragmentSecondBinding
    private lateinit var lmanager: LinearLayoutManager
    private var marvelCharacterItems: MutableList<MarvelChars> = mutableListOf<MarvelChars>()
    private lateinit var progressBar: ProgressBar
    private var rvAdapter: MarvelAdapters = MarvelAdapters { sendMarvelItems(it) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
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
//            val newItems= marvelCharacterItems.filter {
//                    items->
//                items.nombre.lowercase(). contains(filteredText.toString().lowercase())
//
//            }
//

        }
    }

    fun reset() {
        marvelCharacterItems = mutableListOf<MarvelChars>()
        rvAdapter.replaceListAdapter(marvelCharacterItems)
    }

    fun sendMarvelItems(item: MarvelChars) {

        val i = Intent(requireActivity(), DetailsMarvelItem::class.java)
        i.putExtra("name", item)
        startActivity(i)
    }

    private fun chargeDataRV(nombre: String) {


        lifecycleScope.launch(Dispatchers.Main) {
            progressBar.visibility = View.VISIBLE
            marvelCharacterItems = withContext(Dispatchers.IO) {
                return@withContext (MarvelComicLogic().getMarvelChars(nombre, 5)


                        )
            } as MutableList<MarvelChars>
            if (marvelCharacterItems.size == 0) {
                var f = Snackbar.make(binding.txtBucar, "No se encontro", Snackbar.LENGTH_LONG)

                f.show()
            }
            rvAdapter.items =


                MarvelComicLogic().getMarvelChars(nombre, 5)




            binding.rvMarvel.apply {
                this.adapter = rvAdapter
                //  this.layoutManager = lmanager
                this.layoutManager = lmanager
            }
            progressBar.visibility = View.GONE


        }
    }


}