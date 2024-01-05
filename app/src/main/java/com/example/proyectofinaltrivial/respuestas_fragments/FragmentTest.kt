package com.example.proyectofinaltrivial.respuestas_fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.proyectofinaltrivial.R
import com.example.proyectofinaltrivial.PreguntaActivity

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
        val preguntaActivity = activity as? PreguntaActivity
        var isTrue: Boolean
        btnOpcion1.setOnClickListener {
            isTrue = respuestaCorrecta == btnOpcion1.text
            preguntaActivity?.devolverResultado(isTrue)
        }
        btnOpcion2.setOnClickListener {
            isTrue = respuestaCorrecta == btnOpcion2.text
            preguntaActivity?.devolverResultado(isTrue)
        }
        btnOpcion3.setOnClickListener {
            isTrue = respuestaCorrecta == btnOpcion3.text
            preguntaActivity?.devolverResultado(isTrue)
        }
        btnOpcion4.setOnClickListener {
            isTrue = respuestaCorrecta == btnOpcion4.text
            preguntaActivity?.devolverResultado(isTrue)
        }


        return view
    }
}