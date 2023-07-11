package com.example.dispositivosmoviles.logic.marvelLogic

import com.example.dispositivosmoviles.data.connections.ApiConnection
import com.example.dispositivosmoviles.data.endpoints.MarvelEndpoint
import com.example.dispositivosmoviles.data.entities.MarvelChars
import com.example.dispositivosmoviles.data.entities.getMarvelCharsDB
import com.example.dispositivosmoviles.data.entities.marvel.database.MarvelCharsDB
import com.example.dispositivosmoviles.data.entities.marvel.getMarvelChars
import com.example.dispositivosmoviles.ui.utilities.DispositivosMoviles

class MarvelComicLogic {
    suspend fun getMarvelChars(name: String, limit: Int): ArrayList<MarvelChars> {
        var itemList = arrayListOf<MarvelChars>()

        var response = ApiConnection.getService(
            ApiConnection.typeApi.Marvel,
            MarvelEndpoint::class.java
        ).getCharactersStartWith(name, limit)

        if (response.isSuccessful) {
            response.body()!!.data.results.forEach {
                val m = it.getMarvelChars()
                itemList.add(m)
            }
        }
        return itemList
    }

    suspend fun getAllMarvelChars(offset: Int, limit: Int): ArrayList<MarvelChars> {
        var itemList = arrayListOf<MarvelChars>()

        var response = ApiConnection.getService(
            ApiConnection.typeApi.Marvel,
            MarvelEndpoint::class.java
        ).getAllMarvelChars(offset, limit)

        if (response.isSuccessful) {
            response.body()!!.data.results.forEach {
                val m = it.getMarvelChars()
                itemList.add(m)
            }
        }
        return itemList
    }

    suspend fun getAllMarvelCharsDB(): List<MarvelChars> {
        val items: ArrayList<MarvelChars> = arrayListOf()
        val itemsAux = DispositivosMoviles.getDBInstance().marvelDao().getAllCharacters()


        itemsAux.forEach {
            items.add(
                MarvelChars(
                    it.id,
                    it.name,
                    it.comic,
                    it.image
                )
            )

        }
        return items

    }

    suspend fun insertMarvelCharstoDB(items: List<MarvelChars>) {


        var itemsDB = arrayListOf<MarvelCharsDB>()

        items.forEach {
            itemsDB.add(it.getMarvelCharsDB())
        }

        DispositivosMoviles.getDBInstance().marvelDao().insertMarvelChar(itemsDB)


    }
}