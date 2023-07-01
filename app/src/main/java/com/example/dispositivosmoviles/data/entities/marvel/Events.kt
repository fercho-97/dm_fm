package com.example.dispositivosmoviles.data.entities.marvel

data class Events(
    val available: Int,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Int
)