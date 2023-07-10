package com.example.dispositivosmoviles.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.data.entities.MarvelChars
import com.example.dispositivosmoviles.databinding.FragmentFirstBinding
import com.example.dispositivosmoviles.logic.marvelLogic.MarvelComicLogic
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


    private lateinit var binding: FragmentFirstBinding
    private lateinit var lmanager: LinearLayoutManager

    private var marvelCharacterItems: MutableList<MarvelChars> = mutableListOf<MarvelChars>()
    private var rvAdapter: MarvelAdapters = MarvelAdapters { sendMarvelItem(it) }

    private lateinit var marvelCharsItem: MutableList<MarvelChars>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFirstBinding.inflate(
            layoutInflater, container, false
        )

        lmanager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.VERTICAL,
            false
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
            ArrayAdapter<String>(
                requireActivity(),
                R.layout.spinner_item_layaout, names
            )
        binding.spinner.adapter = adapter
        // binding.listview.adapter = adapter

        chargeDataRV()

        binding.rvSwipe.setOnRefreshListener {

            chargeDataRV()
            binding.rvSwipe.isRefreshing = false
        }

        binding.rcMarvelCharter.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (dy > 0) {
                        val v = lmanager.childCount
                        val p = lmanager.findFirstVisibleItemPosition()
                        val t = lmanager.itemCount

                        if ((v + p) >= t) {

                            lifecycleScope.launch(Dispatchers.IO) {
                                val items = MarvelComicLogic().getAllMarvelChars(0,15)

                                withContext(Dispatchers.Main){
                                    rvAdapter.updateListItems(items)

                                }

                            }
                        }
                    }

                }

            })

        binding.txtfilter.addTextChangedListener { filterText ->

            val newItems = marvelCharsItem.filter { items ->
                items.name.contains(filterText.toString())
            }

            rvAdapter.replaceListItems(newItems)
        }


    }


    fun sendMarvelItem(item: MarvelChars) {
        /*
                val i = Intent(requireActivity(), DetailsMarvelItem::class.java)
                i.putExtra("name", item)
                startActivity(i)


         */
        val i = Intent(requireActivity(), DetailsMarvelItem::class.java)
        i.putExtra("name", item)
        i.putExtra("comic", item)
        i.putExtra("imagen", item)
        startActivity(i)
    }

    fun corrotine() {

        lifecycleScope.launch(Dispatchers.Main) {
            var name = "Fer"
            name = withContext((Dispatchers.IO)) {
                name = "Jairo"
                return@withContext name
            }
        }
    }

    /*
    fun chargeDataRV(pos: Int) {

        lifecycleScope.launch(Dispatchers.IO) {


            var marvelCharsItem = MarvelComicLogic().getAllAnimes("spider", page*2)

            rvAdapter.items =
                MarvelComicLogic().getAllAnimes(name = search, 10)
            //JikanAnimeLogic().getAllAnimes()

            withContext(Dispatchers.Main) {
                with(binding.rcMarvelCharter) {
                    this.adapter = rvAdapter
                    this.layoutManager = lmanager
                }
            }

        }

    }

     */

    private fun chargeDataRV() {


        lifecycleScope.launch(Dispatchers.Main) {
            marvelCharacterItems = withContext(Dispatchers.IO) {
                return@withContext (MarvelComicLogic().getAllMarvelChars(
                    0, 99


                ))
            } as MutableList<MarvelChars>

            rvAdapter.items =


                    //JikanAnimeLogic().getAllAnimes()
                MarvelComicLogic().getAllMarvelChars(0, 10)

            //ListItems().returnMarvelChar()
            /*   JikanAnimeLogic().getAllAnimes()
   ) { sendMarvelItems(it) }

*/



            binding.rcMarvelCharter.apply {
                this.adapter = rvAdapter
                //  this.layoutManager = lmanager
                this.layoutManager = lmanager
            }


        }
    }

}