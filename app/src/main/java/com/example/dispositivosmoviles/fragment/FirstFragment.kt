package com.example.dispositivosmoviles.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.stringPreferencesKey
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
import com.example.dispositivosmoviles.ui.activities.dataStore
import com.example.dispositivosmoviles.ui.adapter.MarvelAdapters
import com.example.dispositivosmoviles.ui.data.UserDataStore
import com.example.dispositivosmoviles.ui.utilities.DispositivosMoviles
import com.example.dispositivosmoviles.ui.utilities.Metodos
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
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
    private var marvelCharsItemsDB: MutableList<MarvelChars> = mutableListOf<MarvelChars>()

    private var rvAdapter: MarvelAdapters =
        MarvelAdapters({ sendMarvelItem(it) }, { saveMarvelItem(it) })
    private var page: Int = 1
    private var offset: Int = 0
    private val limit: Int = 99

    // private lateinit var marvelCharsItem: MutableList<MarvelChars>
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
    /*
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
                                    val items = MarvelComicLogic().getAllMarvelChars(0, 10)

                                    withContext(Dispatchers.Main) {
                                        rvAdapter.updateListItems(items)

                                    }

                                }
                            }
                        }

                    }

                })

            binding.txtfilter.addTextChangedListener { filterText ->

                val newItems = marvelCharacterItems.filter { items ->
                    items.name.contains(filterText.toString())
                }

                rvAdapter.replaceListItems(newItems)
            }


        }

     */

    override fun onStart() {
        super.onStart()

        lifecycleScope.launch(Dispatchers.IO){
            getDataStore().collect(){
                Log.d("UCE", it.toString())
            }

        }

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

        chargeDataRVInit(offset, limit)
        binding.rvSwipe.setOnRefreshListener {
            chargeDataRV(offset, limit)
            binding.rvSwipe.isRefreshing = false
        }

        //Para cargar mas contenido
        binding.rcMarvelCharter.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(
                    recyclerView,
                    dx,
                    dy
                ) //dy es para el scroll de abajo y dx es de izquierda a derech para buscar elementos

                if (dy > 0) {
                    val v = lmanager.childCount  //cuantos elementos han pasado
                    val p = lmanager.findFirstVisibleItemPosition() //posicion actual
                    val t = lmanager.itemCount //cuantos tengo en total

                    //necesitamos comprobar si el total es mayor igual que los elementos que han pasado entonces ncesitamos actualizar ya que estamos al final de la lista
                    if ((v + p) >= t) {

                        var newItems = listOf<MarvelChars>()
                        if (offset < 99) {
                            //Log.i("En el scrollview Offset if","$offset")
                            updateDataRV(limit, offset)
                            lifecycleScope.launch((Dispatchers.Main)) {
                                this@FirstFragment.offset += limit
                                newItems = withContext(Dispatchers.IO) {
                                    return@withContext (MarvelComicLogic().getAllMarvelChars(
                                        offset,
                                        limit
                                    ))

                                }
                                rvAdapter.updateListItems(newItems)

                            }
                        } else {
                            //Log.i("En el scrollview Offset else","$offset")
                            if (offset == 99) {
                                updateDataRV(limit, offset)
                            } else {
                                updateAdapterRV()
                                lifecycleScope.launch((Dispatchers.Main)) {
                                    rvAdapter.updateListItems(listOf())

                                }
                            }


                        }


                    }
                }
            }


        })
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

    /*private fun chargeDataRV() {

        lifecycleScope.launch(Dispatchers.Main) {
            marvelCharacterItems = withContext(Dispatchers.IO) {
                return@withContext (MarvelComicLogic().getAllMarvelChars(
                    0, 99


                ))
            } as MutableList<MarvelChars>

            rvAdapter.items =


                    //JikanAnimeLogic().getAllAnimes()
                MarvelComicLogic().getAllMarvelChars(0, 50)

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


     */
    fun chargeDataRV(limit: Int, offset: Int) {


        lifecycleScope.launch(Dispatchers.Main) {

            marvelCharacterItems = withContext(Dispatchers.IO) {


                return@withContext (MarvelComicLogic().getAllMarvelChars(offset, limit))
            }


            rvAdapter.items = marvelCharacterItems

            binding.rcMarvelCharter.apply {
                this.adapter = rvAdapter
                this.layoutManager = lmanager
            }
            this@FirstFragment.offset += limit
        }


    }

    fun updateDataRV(limit: Int, offset: Int) {

        var items: List<MarvelChars> = listOf()
        lifecycleScope.launch(Dispatchers.Main) {

            items = withContext(Dispatchers.IO) {


                return@withContext (MarvelComicLogic().getAllMarvelChars(offset, limit))
            }


            rvAdapter.updateListItems(items)

            binding.rcMarvelCharter.apply {
                this.adapter = rvAdapter
                this.layoutManager = lmanager
            }
        }


    }

    fun updateAdapterRV() {
        lifecycleScope.launch(Dispatchers.Main) {
            binding.rcMarvelCharter.apply {
                this.adapter = rvAdapter
                this.layoutManager = lmanager
            }
        }


    }

    private fun chargeDataRVDB() {


        lifecycleScope.launch(Dispatchers.Main) {

            marvelCharacterItems = withContext(Dispatchers.IO) {

                var marvelCharacterItems = MarvelComicLogic().getAllMarvelCharsDB().toMutableList()


                if (marvelCharacterItems.isEmpty()) {

                    marvelCharacterItems =
                        (MarvelComicLogic().getAllMarvelChars(0, 99).toMutableList())
                    MarvelComicLogic().insertMarvelCharstoDB(marvelCharacterItems)
                }

                return@withContext marvelCharacterItems
            }

            rvAdapter.items = marvelCharacterItems

            binding.rcMarvelCharter.apply {
                this.adapter = rvAdapter
                this.layoutManager = lmanager
            }
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


    fun chargeDataRVInit(offset: Int, limit: Int) {

        if (Metodos().isOnline(requireActivity())) {


            lifecycleScope.launch(Dispatchers.Main) {

                marvelCharacterItems = withContext(Dispatchers.IO) {
                    return@withContext (MarvelComicLogic().getAllMarvelChars(offset, limit))
                }

                rvAdapter.items = marvelCharacterItems

                binding.rcMarvelCharter.apply {
                    this.adapter = rvAdapter
                    this.layoutManager = lmanager
                }
                this@FirstFragment.offset += limit


            }

        } else {
            Snackbar.make(
                binding.spinner,
                "No hay conexion",
                Snackbar.LENGTH_LONG
            )
                .show()
        }
    }

    private fun getDataStore() = requireActivity().dataStore.data.map { prefs ->
        UserDataStore(
            name=prefs[stringPreferencesKey("usuario")].orEmpty(),
            email=prefs[stringPreferencesKey("email")].orEmpty(),
            session=prefs[stringPreferencesKey("session")].orEmpty()
        )
    }



}