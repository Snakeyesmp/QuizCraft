package com.example.proyectofinaltrivial

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

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
            if (opcion1.text == respuestaCorrecta) {
                Log.d("TestActivity", "Respuesta correcta")
            } else {
                Log.d("TestActivity", "Respuesta incorrecta")
            }
            finish()
        }
        opcion2.setOnClickListener {
            if (opcion2.text == respuestaCorrecta) {
                Log.d("TestActivity", "Respuesta correcta")
            } else {
                Log.d("TestActivity", "Respuesta incorrecta")
            }
            finish()
        }
        opcion3.setOnClickListener {
            if (opcion3.text == respuestaCorrecta) {
                Log.d("TestActivity", "Respuesta correcta")
            } else {
                Log.d("TestActivity", "Respuesta incorrecta")
            }
            finish()
        }
        opcion4.setOnClickListener {
            if (opcion4.text == respuestaCorrecta) {
                Log.d("TestActivity", "Respuesta correcta")
            } else {
                Log.d("TestActivity", "Respuesta incorrecta")
            }
            finish()
        }
    }
    //TODO: Hacer que si la respuesta es correcta, se conserve el turno del jugador
}