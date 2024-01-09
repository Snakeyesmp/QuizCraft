package com.example.proyectofinaltrivial.respuestas_fragments

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.proyectofinaltrivial.R
import com.example.proyectofinaltrivial.PreguntaActivity
import kotlinx.coroutines.CoroutineScope

class FragmentTest : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_test, container, false)


        val opciones = arguments?.getStringArrayList("opciones")
        val respuestaCorrecta = arguments?.getString("respuestaCorrecta")

        val btnOpcion1 = view.findViewById<Button>(R.id.opcion1)
        val btnOpcion2 = view.findViewById<Button>(R.id.opcion2)
        val btnOpcion3 = view.findViewById<Button>(R.id.opcion3)
        val btnOpcion4 = view.findViewById<Button>(R.id.opcion4)

        Log.d("FragmentTest", "Opciones: ${opciones?.get(0)}")
        Log.d("FragmentTest", "RespuestaCorrecta: $respuestaCorrecta")

        btnOpcion1.text = opciones?.get(0).toString()
        btnOpcion2.text = opciones?.get(1).toString()
        btnOpcion3.text = opciones?.get(2).toString()
        btnOpcion4.text = opciones?.get(3).toString()
        val botones = listOf<Button>(btnOpcion1, btnOpcion2, btnOpcion3, btnOpcion4)

        val preguntaActivity = activity as? PreguntaActivity
        var isTrue: Boolean
        btnOpcion1.setOnClickListener {
            isTrue = respuestaCorrecta == btnOpcion1.text

            if (!isTrue) {
                btnOpcion1.setBackgroundColor(Color.parseColor("#bf3434"))
            }
            mostarResultado(respuestaCorrecta.toString(), botones)

            Handler(Looper.getMainLooper()).postDelayed({
                preguntaActivity?.devolverResultado(isTrue)


            }, 1500)
        }
        btnOpcion2.setOnClickListener {
            isTrue = respuestaCorrecta == btnOpcion2.text
            if (!isTrue) {
                btnOpcion2.setBackgroundColor(Color.parseColor("#bf3434"))
            }
            mostarResultado(respuestaCorrecta.toString(), botones)

            Handler(Looper.getMainLooper()).postDelayed({
                preguntaActivity?.devolverResultado(isTrue)


            }, 1500)
        }
        btnOpcion3.setOnClickListener {
            isTrue = respuestaCorrecta == btnOpcion3.text
            if (!isTrue) {
                btnOpcion3.setBackgroundColor(Color.parseColor("#bf3434"))
            }
            mostarResultado(respuestaCorrecta.toString(), botones)

            Handler(Looper.getMainLooper()).postDelayed({
                preguntaActivity?.devolverResultado(isTrue)


            }, 1500)
        }
        btnOpcion4.setOnClickListener {
            isTrue = respuestaCorrecta == btnOpcion4.text
            if (!isTrue) {
                btnOpcion4.setBackgroundColor(Color.parseColor("#bf3434"))
            }
            mostarResultado(respuestaCorrecta.toString(), botones)

            Handler(Looper.getMainLooper()).postDelayed({
                preguntaActivity?.devolverResultado(isTrue)


            }, 1500)

        }


        return view
    }

    private fun mostarResultado(
        opcionCorrecta: String,
        botones: List<Button>
    ) {

        for (boton in botones) {
            if (boton.text.toString() == opcionCorrecta) {
                boton.setBackgroundColor(Color.parseColor("#34bf5b"))
                break // Detener el bucle una vez que se encuentre el botón correspondiente
            }
        }
    }


}