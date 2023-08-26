package com.example.dispositivosmoviles.logic.convencionesLogic

import com.example.dispositivosmoviles.data.entities.convenciones.Datos

class ListItems {fun getData(): List<Datos> {
    val data = arrayListOf<Datos>(
        Datos(
            "Comic-Con Praga",
            "https://www.bpofexperience.com/src/events/event_1147/editor/thumbnails/ei_CC_Republica_Checa-1682742446840_w600_q80.jpg",
            "Fecha: 14 al 16 de Abril 2023"

        ),
        Datos(
            "Comic Con Perú",
            "https://www.bpofexperience.com/src/events/event_1147/editor/thumbnails/ei_CC_Peru-1682741706325_w1920_q80.jpg",
            "Fecha: 27 de Abril al 01 de Mayo 2023"
        ),
        Datos(
            "Comic Con Nápoles",
            "https://www.bpofexperience.com/src/events/event_1147/editor/thumbnails/ei_CC_Italia-1682740089191_w1024_q80.jpg",
            "Fecha: 28 de Abril al 01 de Mayo 2023"
        ),
        Datos(
            "Comic Con Holanda",
            "https://www.bpofexperience.com/src/events/event_1147/editor/thumbnails/ei_CC_Holanda-1683477862498_w1024_q80.jpg",
            "Fecha: 29 y 30 de Abril 2023"
        ),
        Datos(
            "Comic Con Osaka",
            "https://www.bpofexperience.com/src/events/event_1147/editor/thumbnails/ei_CC_Japon-1682740401788_w1024_q80.jpg",
            "Fecha: 10 y 11 de Mayo 2023"
        ),
        Datos(
            "Comic Con Bruselas",
            "https://www.bpofexperience.com/src/events/event_1147/editor/thumbnails/ei_CC_Colombia_Medellin-1682481447427_w1024_q80.jpg",
            "Fecha: 20 y 21 de Mayo 2023"
        ),
        Datos(
            "MCM Londres Comic Con",
            "https://www.bpofexperience.com/src/events/event_1147/editor/thumbnails/ei_CC_Inglaterra-1682622216989_w1024_q80.jpg",
            "Fecha: 26 al 28 de Mayo 2023"
        ),
        Datos(
            "Comic Con San Diego",
            "https://www.bpofexperience.com/src/events/event_1147/editor/thumbnails/ei_CC_San_Diego_02-1683655824245_w1920_q80.jpg",
            "Fecha: 20 al 23 de Julio 2023"
        )

    )
    return data
}
}