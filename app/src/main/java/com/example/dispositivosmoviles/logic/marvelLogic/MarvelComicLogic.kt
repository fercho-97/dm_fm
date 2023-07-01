package com.example.dispositivosmoviles.logic.marvelLogic

import android.util.Log
import com.example.dispositivosmoviles.data.connections.ApiConnection
import com.example.dispositivosmoviles.data.endpoints.MarvelEndpoint
import com.example.dispositivosmoviles.data.entities.MarvelChars

class MarvelComicLogic {
    suspend fun getAllAnimes(name: String, limit: Int): List<MarvelChars> {
        var itemList = arrayListOf<MarvelChars>()
        var call = ApiConnection.getService(
            ApiConnection.typeApi.Marvel,
            MarvelEndpoint::class.java
        )

        if (call != null) {
            var response = call.getCharactersStartWith(name, limit)
            Log.d("UCE", response.toString())
            if (response.isSuccessful) {
                response.body()!!.data.results.forEach {
                    var commic: String = ""
                    if (it.comics.items.size > 0) {
                        commic = it.comics.items[0].name
                    }
                    val m = MarvelChars(
                        it.id,
                        it.name,
                        commic,
                        it.thumbnail.path + "." + it.thumbnail.extension
                    )
                    itemList.add(m)
                }
            } else {
                Log.d("UCE", response.toString())
            }
        }


        //Compruebo si la respuesta se ejecuto

        return itemList
    }
}