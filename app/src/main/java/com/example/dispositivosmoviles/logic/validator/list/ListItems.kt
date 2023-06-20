package com.example.dispositivosmoviles.logic.validator.list

import com.example.dispositivosmoviles.data.entities.MarvelChars

class ListItems {

    fun returnChars():List<MarvelChars>{

        val items = listOf(
            MarvelChars(1,
                "Iron Man",
                "The Avengers",
                "https://comicvine.gamespot.com/a/uploads/original/11138/111389575/7790677-5188796742-68818.jpg" ),
            MarvelChars(2,
                "Gambit",
                "X-Mannen", "https://comicvine.gamespot.com/a/uploads/original/11126/111269625/7324346-excalibur%20variant.jpg" ),
            MarvelChars(3,
                "Scarlet Witch",
                "Avengers West Coast (1989)",
                "https://comicvine.gamespot.com/a/uploads/original/12/124259/8338231-e5qvi6wuyaipz5n.jfif.jpg"),
            MarvelChars(4,
                "Batman",
                "Batman(1940)",
                "https://comicvine.gamespot.com/a/uploads/scale_small/11144/111442876/8759934-jrjrhr.jpg")


        )
        return items
    }


}