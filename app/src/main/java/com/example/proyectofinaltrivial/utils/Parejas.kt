package com.example.proyectofinaltrivial.utils

import android.util.Log

class Parejas(
    val preguntas: List<Map<String, String>>


) {
    override fun toString(): String {
        return "Parejas(preguntas=${preguntas.joinToString()})"
    }

}
