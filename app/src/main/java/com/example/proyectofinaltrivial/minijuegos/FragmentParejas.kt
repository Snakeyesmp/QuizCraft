package com.example.proyectofinaltrivial.minijuegos

import Consultas
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.proyectofinaltrivial.R
import com.example.proyectofinaltrivial.utils.Parejas
import com.example.proyectofinaltrivial.utils.PreguntaParejas
import java.util.Locale

class FragmentParejas : Fragment() {

    private lateinit var buttonLeft1: Button
    private lateinit var buttonLeft2: Button
    private lateinit var buttonLeft3: Button
    private lateinit var buttonRight1: Button
    private lateinit var buttonRight2: Button
    private lateinit var buttonRight3: Button

    private var selectedButtonLeft: Button? = null
    private var selectedButtonRight: Button? = null

    private val consultas = Consultas()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.minijuego_parejas, container, false)

        buttonLeft1 = view.findViewById(R.id.button1)
        buttonLeft2 = view.findViewById(R.id.button2)
        buttonLeft3 = view.findViewById(R.id.button3)
        buttonRight1 = view.findViewById(R.id.button4)
        buttonRight2 = view.findViewById(R.id.button5)
        buttonRight3 = view.findViewById(R.id.button6)

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtén las parejas de preguntas desde Firebase
        consultas.obtenerPreguntaPorTipo("parejas") { pregunta ->
            if (pregunta is Parejas) {
                // Asigna los textos a los botones
                buttonLeft1.text = pregunta.preguntas[0]["enunciado"]
                buttonRight1.text = pregunta.preguntas[0]["respuestaCorrecta"]

                buttonLeft2.text = pregunta.preguntas[1]["enunciado"]
                buttonRight2.text = pregunta.preguntas[1]["respuestaCorrecta"]

                buttonLeft3.text = pregunta.preguntas[2]["enunciado"]
                buttonRight3.text = pregunta.preguntas[2]["respuestaCorrecta"]
            }
        }

        // Configura los listeners de los botones
        setButtonListeners()
    }

    private fun setButtonListeners() {
        buttonLeft1.setOnClickListener { onLeftButtonClick(buttonLeft1) }
        buttonLeft2.setOnClickListener { onLeftButtonClick(buttonLeft2) }
        buttonLeft3.setOnClickListener { onLeftButtonClick(buttonLeft3) }

        buttonRight1.setOnClickListener { onRightButtonClick(buttonRight1) }
        buttonRight2.setOnClickListener { onRightButtonClick(buttonRight2) }
        buttonRight3.setOnClickListener { onRightButtonClick(buttonRight3) }
    }

    private fun onLeftButtonClick(button: Button) {
        selectedButtonLeft = button
        checkForMatch()
    }

    private fun onRightButtonClick(button: Button) {
        selectedButtonRight = button
        checkForMatch()
    }

    private fun checkForMatch() {
        if (selectedButtonLeft != null && selectedButtonRight != null) {
            val leftText = selectedButtonLeft!!.text.toString().trim().toLowerCase(Locale.ROOT)
            val rightText = selectedButtonRight!!.text.toString().trim().toLowerCase(Locale.ROOT)

            if (leftText == rightText) {
                // Los botones coinciden
                selectedButtonLeft!!.isEnabled = false
                selectedButtonRight!!.isEnabled = false
                selectedButtonLeft!!.setBackgroundColor(Color.GREEN)
                selectedButtonRight!!.setBackgroundColor(Color.GREEN)
            } else {
                // Los botones no coinciden
                Toast.makeText(context, "Los elementos seleccionados no coinciden", Toast.LENGTH_SHORT).show()
            }
            // Reinicia los botones seleccionados
            selectedButtonLeft = null
            selectedButtonRight = null
        }
    }
}