package com.example.dispositivosmoviles.data.dao.marvel

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.dispositivosmoviles.data.entities.marvel.database.MarvelCharsDB

@Dao
interface MarvelCharsDao {

    @Query("select * from MarvelCharsDB")
    fun getAllCharacters() : List<MarvelCharsDB>

    @Query("select * from MarvelCharsDB where id=:pk")
    fun getOneCharacters(pk: Int) : MarvelCharsDB

    @Insert
    fun insertMarvelChar(ch:List<MarvelCharsDB>)

}

