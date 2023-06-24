package com.example.dispositivosmoviles.data.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MarvelChars(val id:Int, val name: String, val comic: String, val image: String   ): Parcelable {
}