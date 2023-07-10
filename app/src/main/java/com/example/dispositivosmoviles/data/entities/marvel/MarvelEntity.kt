package com.example.dispositivosmoviles.data.entities.marvel

import com.example.dispositivosmoviles.data.entities.MarvelChars
import com.example.dispositivosmoviles.data.entities.marvel.Result

data class MarvelEntity(
    val attributionHTML: String,
    val attributionText: String,
    val code: Int,
    val copyright: String,
    val `data`: Data,
    val etag: String,
    val status: String

)
