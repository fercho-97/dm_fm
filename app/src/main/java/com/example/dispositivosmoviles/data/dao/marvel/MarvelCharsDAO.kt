package com.example.dispositivosmoviles.data.dao.marvel

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.dispositivosmoviles.data.entities.marvel.database.MarvelCharsDB

@Dao
interface MarvelCharsDao {

    @Query("select * from MarvelCharsDB")
    fun getAllCharacters() : List<MarvelCharsDao>

    @Query("select * from MarvelCharsDB where id=:idd")
    fun getOneCharacters(idd: Int) : List<MarvelCharsDB>

    @Insert
    fun insertMarvelChar(ch : List<MarvelCharsDao>)
}

