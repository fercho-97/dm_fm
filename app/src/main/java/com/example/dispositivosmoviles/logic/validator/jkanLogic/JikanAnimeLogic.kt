package com.example.dispositivosmoviles.logic.validator.jkanLogic

import com.example.dispositivosmoviles.data.connections.ApiConnection
import com.example.dispositivosmoviles.data.endpoints.JikanEndpoint
import com.example.dispositivosmoviles.data.entities.MarvelChars

class JikanAnimeLogic {

    suspend fun getAllAnimes(): List<MarvelChars> {
        /*
        var call = ApiConnection.getService(
            ApiConnection.typeApi.Jikan,
            JikanEndpoint::class.java)

        val response = call.create(JikanEndpoint::class.java).getAllAnimes()


         */

        var itemList = arrayListOf<MarvelChars>()

        val response = ApiConnection.getService(
            ApiConnection.typeApi.Jikan,
            JikanEndpoint::class.java
        ).getAllAnimes()


//Compruebo si la respuesta se ejecuto
        if (response.isSuccessful) {
            response.body()!!.data.forEach {
                val m = MarvelChars(
                    it.mal_id,
                    it.title,
                    it.titles[0].title,
                    it.images.jpg.image_url
                )
                itemList.add(m)
            }

        }
        return itemList
    }
}