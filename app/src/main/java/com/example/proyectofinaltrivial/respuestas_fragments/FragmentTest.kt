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

// ... (código previo)

class FragmentTest : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_test, container, false)

        val opciones = arguments?.getStringArrayList("opciones")
        val respuestaCorrecta = arguments?.getString("respuestaCorrecta")

        val botones = listOf<Button>(
            view.findViewById(R.id.opcion1),
            view.findViewById(R.id.opcion2),
            view.findViewById(R.id.opcion3),
            view.findViewById(R.id.opcion4)
        )

        for ((index, boton) in botones.withIndex()) {
            boton.text = opciones?.get(index).toString()
            boton.setOnClickListener {
                handleButtonClick(boton, respuestaCorrecta, botones)
            }
        }

        return view
    }

    private fun handleButtonClick(
        boton: Button,
        respuestaCorrecta: String?,
        botones: List<Button>
    ) {
        val isTrue = respuestaCorrecta == boton.text

        if (!isTrue) {
            boton.setBackgroundColor(Color.parseColor("#bf3434"))
        }
        mostarResultado(respuestaCorrecta, botones)

        Handler(Looper.getMainLooper()).postDelayed({
            val preguntaActivity = activity as? PreguntaActivity
            preguntaActivity?.devolverResultado(isTrue)
        }, 1500)
    }

    private fun mostarResultado(
        opcionCorrecta: String?,
        botones: List<Button>
    ) {
        opcionCorrecta?.let { correcta ->
            for (boton in botones) {
                if (boton.text.toString() == correcta) {
                    boton.setBackgroundColor(Color.parseColor("#34bf5b"))
                    break
                }
            }
        }
    } }
