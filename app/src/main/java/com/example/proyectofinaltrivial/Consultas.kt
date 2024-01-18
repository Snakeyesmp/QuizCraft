import android.util.Log
import com.example.proyectofinaltrivial.utils.Parejas
import com.example.proyectofinaltrivial.utils.Pregunta
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Consultas {

    /**
     * Devuelve una pregunta según el tipo especificado
     */
    fun obtenerPreguntaPorTipo(
        tipoPregunta: String,
        callback: (Any?) -> Unit
    ) {
        val databaseRef = FirebaseDatabase.getInstance().reference.child("minijuegos")

        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var preguntaObj: Any? = null

                val preguntas = mutableListOf<DataSnapshot>()

                // Recorre los minijuegos y encuentra las preguntas del tipo especificado
                for (minijuegoSnapshot in dataSnapshot.children) {
                    if (minijuegoSnapshot.child("tipo")
                            .getValue(String::class.java) == tipoPregunta
                    ) {
                        preguntas.addAll(minijuegoSnapshot.child("preguntas").children)
                    }
                }


                // Utiliza when para manejar diferentes tipos de preguntas
                preguntaObj = when (tipoPregunta) {
                    "test" -> obtenerPreguntaTest(preguntas)
                    "parejas" -> obtenerPreguntaParejas(preguntas)
                    // Puedes agregar más casos según los diferentes tipos de preguntas
                    else -> Log.e("Consultas", "Tipo de pregunta no reconocido")
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

    // Función para obtener pregunta de tipo "test"
    private fun obtenerPreguntaTest(preguntas: MutableList<DataSnapshot>): Pregunta {
        val random = (0 until preguntas.size).random()
        val preguntaSnapshot = preguntas[random]


        val enunciado = preguntaSnapshot.child("enunciado").getValue(String::class.java)!!
        val respuestaCorrecta = preguntaSnapshot.child("respuestaCorrecta").getValue(String::class.java)!!
        val opcionesSnapshot = preguntaSnapshot.child("opciones")
        val opciones = ArrayList<String>()

        for (opcionSnapshot in opcionesSnapshot.children) {
            val opcion = opcionSnapshot.getValue(String::class.java)
            if (opcion != null) {
                opciones.add(opcion)
            }
        }

        return Pregunta(enunciado, respuestaCorrecta, opciones)
    }

    // Función para obtener pregunta de tipo "parejas"
    private fun obtenerPreguntaParejas(preguntas: MutableList<DataSnapshot>): Parejas {
        val listaParejas = ArrayList<Map<String, String>>()

        val preguntasDisponibles = ArrayList(preguntas)

        for (i in 0 until 3) {
            if (preguntasDisponibles.isEmpty()) {
                // Si no quedan más preguntas, salir del bucle
                break
            }

            val random = (0 until preguntasDisponibles.size).random()
            val snapshot = preguntasDisponibles.removeAt(random)

            val elemento1 = snapshot.child("enunciado").getValue(String::class.java)
            val elemento2 = snapshot.child("respuestaCorrecta").getValue(String::class.java)
            Log.d("Consultas", "Elemento 1_$i: $elemento1")
            Log.d("Consultas", "Elemento 2_$i: $elemento2")

            if (elemento1 != null && elemento2 != null) {
                val pareja = mapOf("enunciado" to elemento1, "respuestaCorrecta" to elemento2)
                listaParejas.add(pareja)
            } else {
                // Manejar el caso en que los valores son nulos
                Log.e("Consultas", "Elemento 1 o Elemento 2 es nulo para algunas parejas")
            }
        }

        listaParejas.forEach() {
            Log.d("Parejas", "Pareja: ${it["enunciado"]} - ${it["respuestaCorrecta"]}")
        }

        return Parejas(listaParejas)
    }


}
