package com.example.dispositivosmoviles.ui.viewmodels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dispositivosmoviles.data.entities.MarvelChars
import com.example.dispositivosmoviles.logic.marvelLogic.MarvelComicLogic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.EnumSet.range

class ProgressViewModel : ViewModel() {

    val progressSate = MutableLiveData<Int>()
    val items = MutableLiveData<List<MarvelChars>>()

    fun processBackgroud(value: Long) {

        viewModelScope.launch(Dispatchers.Main) {

            val state = View.VISIBLE
            progressSate.postValue(state)
            delay(value)
            val state1 = View.GONE
            progressSate.postValue(state1)
        }


    }

    fun sumInBackground(value: Long) {

        viewModelScope.launch(Dispatchers.IO) {

            val state = View.VISIBLE
            progressSate.postValue(state)

            var total = 0

            for (i in 1..3000) {

                total =+1
            }
            val state1 = View.GONE
            progressSate.postValue(state1)
        }

    }

    suspend fun getMarvelChars(offset: Int, limit: Int){

        progressSate.postValue(View.VISIBLE)
        val newItems=MarvelComicLogic().getAllMarvelChars(offset,limit)
        items.postValue(newItems)
        progressSate.postValue(View.GONE)

    }

}