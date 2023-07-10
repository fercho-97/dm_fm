package com.example.dispositivosmoviles.data.connections

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dispositivosmoviles.data.dao.marvel.MarvelCharsDao

import com.example.dispositivosmoviles.data.entities.marvel.database.MarvelCharsDB

@Database(
    entities=[MarvelCharsDB::class],
    version = 1
)
abstract class MarvelConnectionDB: RoomDatabase() {
    abstract fun marvelDao(): MarvelCharsDao

}
