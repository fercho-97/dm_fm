package com.example.dispositivosmoviles.data.entities.convenciones

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Datos(
    val texto:String,
    val imagen: String,
    val fecha: String

): Parcelable
