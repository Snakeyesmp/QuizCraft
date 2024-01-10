package com.example.proyectofinaltrivial.utils

class Pregunta {
    var enunciado: String? = null
    var respuestaCorrecta: String? = null
    var opciones: ArrayList<String>? = null


    // Constructor con enunciado y respuesta correcta
    constructor(enunciadoVal: String?, respuestaCorrectaVal: String?) {
        this.enunciado = enunciadoVal
        this.respuestaCorrecta = respuestaCorrectaVal
    }

    // Constructor con enunciado, respuesta correcta y opciones
    constructor(enunciadoVal: String?, respuestaCorrectaVal: String?, opcionesVal: ArrayList<String>) {
        this.enunciado = enunciadoVal
        this.respuestaCorrecta = respuestaCorrectaVal
        this.opciones = opcionesVal
    }

    // Constructor solo con elemento 1 y elemento 2

}
