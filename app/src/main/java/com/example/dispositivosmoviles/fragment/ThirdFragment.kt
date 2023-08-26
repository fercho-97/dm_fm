package com.example.dispositivosmoviles.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dispositivosmoviles.data.entities.MarvelChars
import com.example.dispositivosmoviles.databinding.FragmentThirdBinding
import com.example.dispositivosmoviles.logic.marvelLogic.MarvelComicLogic
import com.example.dispositivosmoviles.ui.adapter.MarvelAdaptersItems
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ThirdFragment : Fragment() {
    private  lateinit var binding: FragmentThirdBinding


    private lateinit var rvAdapter: MarvelAdaptersItems
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentThirdBinding.inflate(
            layoutInflater, container,false
        )

        rvAdapter = MarvelAdaptersItems({ sendMarvelItem(it) }, { saveMarvelItem(it) })

        showFavoriteComics()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.rvMarvel.adapter = rvAdapter
        binding.rvMarvel.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun sendMarvelItem(item: MarvelChars) {

    }

    private fun showFavoriteComics() {
        lifecycleScope.launch(Dispatchers.Main) {
            val savedMarvelChars = withContext(Dispatchers.IO) {
                MarvelComicLogic().getSavedMarvelChars()
            }

            rvAdapter.replaceListAdapter(savedMarvelChars)
        }
    }

    private fun saveMarvelItem(item: MarvelChars): Boolean {

        return false
    }


}
