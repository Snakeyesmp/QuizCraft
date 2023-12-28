package com.example.proyectofinaltrivial

import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Consultas {
    fun obtenerPreguntaPorTipo(tipoPregunta: String, context: Context) {
        val databaseRef = FirebaseDatabase.getInstance().getReference().child("minijuegos")

        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val preguntas = mutableListOf<DataSnapshot>()

                // Recorre los minijuegos y encuentra las preguntas del tipo especificado
                for (minijuegoSnapshot in dataSnapshot.children) {
                    if (minijuegoSnapshot.child("tipo").getValue(String::class.java) == tipoPregunta) {
                        preguntas.addAll(minijuegoSnapshot.child("preguntas").children)
                    }
                }

                // Escoge una pregunta aleatoria del tipo especificado
                val random = (0 until preguntas.size).random()
                val pregunta = preguntas[random]

                // Obtiene el enunciado de la pregunta
                val enunciado = pregunta.child("enunciado").getValue(String::class.java)
                Log.d("Consultas", "Enunciado: $enunciado")

                // Obtiene las opciones de la pregunta
                val opcionesSnapshot = pregunta.child("opciones")
                val opciones = mutableListOf<String>()

                for (opcionSnapshot in opcionesSnapshot.children) {
                    val opcion = opcionSnapshot.getValue(String::class.java)
                    if (opcion != null) {
                        opciones.add(opcion)
                        Log.d("Consultas", "Opción: $opcion")
                    }
                }

                // Obtiene la opción correcta de la pregunta
                val respuestaCorrecta = pregunta.child("respuestaCorrecta").getValue(String::class.java)
                Log.d("Consultas", "Respuesta Correcta: $respuestaCorrecta")

                // Crea un intent para iniciar la actividad de la pregunta
                val intent = Intent(context, TestActivity::class.java)
                intent.putExtra("pregunta", enunciado)
                intent.putStringArrayListExtra("opciones", ArrayList(opciones))
                intent.putExtra("respuestaCorrecta", respuestaCorrecta)
                context.startActivity(intent)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("Consultas", "Error al obtener las preguntas: ${databaseError.message}")
            }
        })
    }
}
