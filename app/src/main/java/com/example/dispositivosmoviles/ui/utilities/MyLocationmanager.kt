package com.example.dispositivosmoviles.ui.utilities

import android.content.Context
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.SettingsClient

class MyLocationmanager(val context: Context) {


    //var context: Context? = null
    private lateinit var client: SettingsClient

    private fun initVar(){

        if(context != null){

            client = LocationServices.getSettingsClient(context!!)

        }
    }

    fun getUserLocation(){

        initVar()
    }



}