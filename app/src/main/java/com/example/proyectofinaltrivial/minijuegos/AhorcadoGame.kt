package com.example.proyectofinaltrivial.minijuegos

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.proyectofinaltrivial.PreguntaActivity
import com.example.proyectofinaltrivial.R
import java.util.*

class AhorcadoGame : Fragment() {

    private val images = listOf(
        R.drawable.ahorcado_0,
        R.drawable.ahorcado_1,
        R.drawable.ahorcado_2,
        R.drawable.ahorcado_3,
        R.drawable.ahorcado_4,
        R.drawable.ahorcado_5,
        R.drawable.ahorcado_6
    )
    private var imagenActualIndice = 0
    private var intentos = 6
    private var respuestaCorrecta: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.minijuego_ahorcado, container, false)

        respuestaCorrecta = arguments?.getString("respuestaCorrecta")
        view.findViewById<ImageView>(R.id.imagenAhorcado)
            .setImageResource(images[imagenActualIndice])

        view.findViewById<TextView>(R.id.vidas)?.text = "Intentos restantes: $intentos"

        respuestaCorrecta?.forEach { _ ->
            view.findViewById<TextView>(R.id.palabra).append("_ ")
        }

        view.findViewById<Button>(R.id.buttonComprobar).setOnClickListener {
            val letra = view.findViewById<EditText>(R.id.letra).text.toString()
            Log.d("Consultas", "Letra: $letra")
            if (letra.isNotEmpty()) {
                comprobarLetra(letra, view)
            }else{
                Toast.makeText(context, "Introduce una letra", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    private fun comprobarLetra(letra: String, view: View?) {
        var palabra = view?.findViewById<TextView>(R.id.palabra)
        val contiene = respuestaCorrecta?.contains(letra)
        Log.d("Consultas", "Letra:$letra contiene: $contiene")

        if (contiene == true) {
            val palabraActual = palabra?.text.toString().toCharArray()
            respuestaCorrecta?.forEachIndexed { index, c ->
                if (c == letra[0]) {
                    palabraActual[index * 2] =
                        letra[0] // Multiplicamos por 2 para tener en cuenta los espacios "_ "
                }
            }
            view?.findViewById<EditText>(R.id.letra)?.text?.clear()

            palabra?.text = String(palabraActual)
            if (!palabra?.text.toString().contains("_")) {
                val preguntaActivity = activity as? PreguntaActivity
                preguntaActivity?.devolverResultado(true)
            }
        } else {
            imagenActualIndice++
            view?.findViewById<ImageView>(R.id.imagenAhorcado)
                ?.setImageResource(images[imagenActualIndice])
            view?.findViewById<TextView>(R.id.letrasFalladas)?.text =
                view?.findViewById<TextView>(R.id.letrasFalladas)?.text.toString() + letra + " "

            intentos--
            view?.findViewById<TextView>(R.id.vidas)?.text = "Intentos restantes: $intentos"
            Toast.makeText(context, "Has fallado", Toast.LENGTH_SHORT).show()
            view?.findViewById<EditText>(R.id.letra)?.text?.clear()
            if (intentos <= 0) {
                val preguntaActivity = activity as? PreguntaActivity
                preguntaActivity?.devolverResultado(false)
            }
        }


    }


}