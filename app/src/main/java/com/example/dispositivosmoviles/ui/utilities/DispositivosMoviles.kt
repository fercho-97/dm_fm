package com.example.dispositivosmoviles.ui.utilities

import android.app.Application
import androidx.room.Room
import com.example.dispositivosmoviles.data.connections.MarvelConnectionDB
import com.example.dispositivosmoviles.data.entities.marvel.database.MarvelCharsDB

class DispositivosMoviles : Application(){

    override fun onCreate() {
        super.onCreate()
        db= Room.databaseBuilder(
            applicationContext,
            MarvelConnectionDB::class.java,
            "marvelDB"
            ).build()

    }

    companion object{

       private var db: MarvelConnectionDB? =null

        fun getDBInstance(): MarvelConnectionDB{
            return db!!
        }
    }
}