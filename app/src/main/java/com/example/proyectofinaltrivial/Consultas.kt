import android.util.Log
import com.example.proyectofinaltrivial.utils.Pregunta
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Consultas {


    fun obtenerPreguntaPorTipo(
        tipoPregunta: String,
        callback: (Pregunta?) -> Unit // Callback para manejar la devolución de la pregunta
    ) {
        val databaseRef = FirebaseDatabase.getInstance().reference.child("minijuegos")

        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var preguntaObj : Pregunta? = null

                val preguntas = mutableListOf<DataSnapshot>()

                // Recorre los minijuegos y encuentra las preguntas del tipo especificado
                for (minijuegoSnapshot in dataSnapshot.children) {
                    if (minijuegoSnapshot.child("tipo")
                            .getValue(String::class.java) == tipoPregunta
                    ) {
                        preguntas.addAll(minijuegoSnapshot.child("preguntas").children)
                    }
                }

                // Escoge una pregunta aleatoria del tipo especificado
                val random = (0 until preguntas.size).random()
                val preguntaSnapshot = preguntas[random]

                // Obtiene los datos de la pregunta
                val enunciado = preguntaSnapshot.child("enunciado").getValue(String::class.java)
                val respuestaCorrecta =
                    preguntaSnapshot.child("respuestaCorrecta").getValue(String::class.java)

                if (tipoPregunta == "test") {
                    val opcionesSnapshot = preguntaSnapshot.child("opciones")
                    val opciones = ArrayList<String>()

                    for (opcionSnapshot in opcionesSnapshot.children) {
                        val opcion = opcionSnapshot.getValue(String::class.java)
                        if (opcion != null) {
                            opciones.add(opcion)

                        }
                    }
                     preguntaObj = Pregunta(enunciado, respuestaCorrecta, opciones)

                } else {
                     preguntaObj = Pregunta(enunciado, respuestaCorrecta)
                }

                // Devuelve la pregunta mediante el callback
                callback(preguntaObj)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("Consultas", "Error al obtener las preguntas: ${databaseError.message}")
                callback(null) // Manejo del error, devolviendo null
            }
        })
    }
}
