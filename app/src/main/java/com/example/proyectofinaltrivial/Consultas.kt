import android.util.Log
import com.example.proyectofinaltrivial.utils.Parejas
import com.example.proyectofinaltrivial.utils.Pregunta
import com.example.proyectofinaltrivial.utils.PreguntaParejas
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Consultas {


    /**
     * Devuelve distintos tipo de objeto, según la pregunta que salga
     */
    fun obtenerPreguntaPorTipo(
        tipoPregunta: String,
        callback: (Any?) -> Unit // Callback para manejar la devolución de la pregunta
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

                } else if (tipoPregunta == "parejas") {

                    val listaParejas = ArrayList<Map<String, String>>()

                    for (parejaSnapshot in preguntaSnapshot.children) {
                        val elemento1 = parejaSnapshot.child("elemento1").getValue(String::class.java)
                        val elemento2 = parejaSnapshot.child("elemento2").getValue(String::class.java)
                        val pareja = mapOf("elemento1" to elemento1!!, "elemento2" to elemento2!!)
                        listaParejas.add(pareja)
                    }

                    preguntaObj = Parejas(listaParejas)

                }else {
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
