package com.example.proyectofinaltrivial.minijuegos

import Consultas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.proyectofinaltrivial.R
import com.example.proyectofinaltrivial.utils.Parejas
import com.example.proyectofinaltrivial.utils.PreguntaParejas

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

        setButtonListeners()
        loadParejasData()

        return view
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
        selectedButtonLeft?.isSelected = false
        selectedButtonLeft = button
        button.isSelected = true
        checkForMatch()
    }

    private fun onRightButtonClick(button: Button) {
        selectedButtonRight?.isSelected = false
        selectedButtonRight = button
        button.isSelected = true
        checkForMatch()
    }

    private fun checkForMatch() {
        if (selectedButtonLeft != null && selectedButtonRight != null) {
            if (selectedButtonLeft?.text == selectedButtonRight?.text) {
                // Coincidencia, puedes realizar acciones correspondientes
                resetSelection()
            } else {
                // No coincidencia, puedes manejarlo según tus necesidades
            }
        }
    }

    private fun resetSelection() {
        selectedButtonLeft?.isSelected = false
        selectedButtonRight?.isSelected = false
        selectedButtonLeft = null
        selectedButtonRight = null
    }

    private fun loadParejasData() {
        consultas.obtenerPreguntaPorTipo("Parejas") { resultado ->
            val parejas = resultado as Parejas
            if (parejas != null) {
                val listaDeParejas = parejas.preguntas

                if (listaDeParejas != null && listaDeParejas.size >= 3) {
                    buttonLeft1.text = listaDeParejas[0]["elemento1"].toString()
                    buttonLeft2.text = listaDeParejas[1]["elemento1"].toString()
                    buttonLeft3.text = listaDeParejas[2]["elemento1"].toString()

                    buttonRight1.text = listaDeParejas[0]["elemento2"].toString()
                    buttonRight2.text = listaDeParejas[1]["elemento2"].toString()
                    buttonRight3.text = listaDeParejas[2]["elemento2"].toString()
                } else {
                    // Maneja el caso en el que los elementos sean nulos o no contengan suficientes elementos
                }
            } else {
                // Maneja el caso en el que no se pueda obtener la información de parejas
            }
        }
    }
}