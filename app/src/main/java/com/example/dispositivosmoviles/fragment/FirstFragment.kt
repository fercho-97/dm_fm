package com.example.dispositivosmoviles.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.data.entities.MarvelChars
import com.example.dispositivosmoviles.databinding.FragmentFirstBinding
import com.example.dispositivosmoviles.logic.validator.jkanLogic.JikanAnimeLogic
import com.example.dispositivosmoviles.ui.activities.DetailsMarvelItem
import com.example.dispositivosmoviles.ui.adapter.MarvelAdapters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass.
 * Use the [FirstFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FirstFragment : Fragment() {
    // TODO: Rename and change types of parameters

    // se debe poner igual viewbinding


    private  lateinit var binding: FragmentFirstBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFirstBinding.inflate(
            layoutInflater, container,false
        )

        return binding.root
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_first, container, false)// se necesita 3 cosas ,
    }

    override fun onStart() {
        super.onStart()

        val names = arrayListOf<String>(
            "Juan",
            "Josue",
            "Antony",
            "Dome"
        )

        val adapter =
            ArrayAdapter<String>(requireActivity(),
            R.layout.spinner_item_layaout, names)
        binding.spinner.adapter = adapter
        // binding.listview.adapter = adapter

        chargeDataRV()

        binding.rvSwipe.setOnRefreshListener {

            chargeDataRV()
            binding.rvSwipe.isRefreshing = false
        }






    }


    fun sendMarvelItem(item: MarvelChars){

        val i = Intent(requireActivity(), DetailsMarvelItem::class.java)
        i.putExtra("name", item)
        startActivity(i)
    }

    fun chargeDataRV(){

        lifecycleScope.launch(Dispatchers.IO){
            val rvAdapter = MarvelAdapters(
                JikanAnimeLogic().getAllAnimes()
            ) { sendMarvelItem(it) }

            withContext(Dispatchers.Main){
                with(binding.rcMarvelCharter){

                    this.adapter = rvAdapter
                    this.layoutManager = LinearLayoutManager(
                        requireActivity(),
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                }

            }


        }




    }


}