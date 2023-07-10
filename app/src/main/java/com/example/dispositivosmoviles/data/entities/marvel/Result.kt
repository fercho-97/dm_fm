package com.example.dispositivosmoviles.data.entities.marvel

import com.example.dispositivosmoviles.data.entities.MarvelChars

data class Result(
    val comics: Comics,
    val description: String,
    val events: Events,
    val id: Int,
    val modified: String,
    val name: String,
    val resourceURI: String,
    val series: Series,
    val stories: Stories,
    val thumbnail: Thumbnail,
    val urls: List<Url>
)

fun Result.getMarvelChars(): MarvelChars {
    var commic: String = ""
    if (comics.items.size > 0) {
        commic = comics.items[0].name
    }

    val a = MarvelChars(
        id,
        name,
        commic,
        thumbnail.path + "." + thumbnail.extension
    )
    return a
}