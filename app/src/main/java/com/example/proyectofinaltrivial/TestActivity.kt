package com.example.proyectofinaltrivial

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_test)

        val pregunta = intent.getStringExtra("pregunta")
        val opciones = intent.getStringArrayListExtra("opciones")


        val respuestaCorrecta = intent.getStringExtra("respuestaCorrecta")

        val enunciado = findViewById<TextView>(R.id.enunciado)
        val opcion1 = findViewById<Button>(R.id.opcion1)
        val opcion2 = findViewById<Button>(R.id.opcion2)
        val opcion3 = findViewById<Button>(R.id.opcion3)
        val opcion4 = findViewById<Button>(R.id.opcion4)

        enunciado.text = pregunta
        opcion1.text = opciones?.get(0).toString()
        opcion2.text = opciones?.get(1).toString()
        opcion3.text = opciones?.get(2).toString()
        opcion4.text = opciones?.get(3).toString()


        opcion1.setOnClickListener {
            val isTrue = respuestaCorrecta == opcion1.text
            devolverResultado(isTrue)

        }
        opcion2.setOnClickListener {
            val isTrue = respuestaCorrecta == opcion2.text
            devolverResultado(isTrue)

        }
        opcion3.setOnClickListener {
            val isTrue = respuestaCorrecta == opcion3.text
            devolverResultado(isTrue)

        }
        opcion4.setOnClickListener {
            val isTrue = respuestaCorrecta == opcion4.text
            devolverResultado(isTrue)
        }
    }

    private fun devolverResultado(isTrue: Boolean) {
        val intent = Intent()
        intent.putExtra("respuesta", isTrue)
        setResult(AppCompatActivity.RESULT_OK, intent)
        finish()
    }
    //TODO: Hacer que si la respuesta es correcta, se conserve el turno del jugador
}