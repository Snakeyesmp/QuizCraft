package com.example.proyectofinaltrivial.minijuegos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.proyectofinaltrivial.R

class FragmentParejas : Fragment() {

    private lateinit var buttonLeft1: Button
    private lateinit var buttonLeft2: Button
    private lateinit var buttonLeft3: Button
    private lateinit var buttonRight1: Button
    private lateinit var buttonRight2: Button
    private lateinit var buttonRight3: Button

    private var selectedButtonLeft: Button? = null
    private var selectedButtonRight: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.minijuego_parejas, container, false)

        buttonLeft1 = view.findViewById(R.id.buttonLeft1)
        buttonLeft2 = view.findViewById(R.id.buttonLeft2)
        buttonLeft3 = view.findViewById(R.id.buttonLeft3)
        buttonRight1 = view.findViewById(R.id.buttonRight1)
        buttonRight2 = view.findViewById(R.id.buttonRight2)
        buttonRight3 = view.findViewById(R.id.buttonRight3)

        setButtonListeners()

        return view
    }

    private fun setButtonListeners() {
        // Asigna los nombres y profesiones a los botones
        buttonLeft1.text = "Persona 1"
        buttonLeft2.text = "Persona 2"
        buttonLeft3.text = "Persona 3"
        buttonRight1.text = "Profesión 1"
        buttonRight2.text = "Profesión 2"
        buttonRight3.text = "Profesión 3"

        buttonLeft1.setOnClickListener { onLeftButtonClick(buttonLeft1) }
        buttonLeft2.setOnClickListener { onLeftButtonClick(buttonLeft2) }
        buttonLeft3.setOnClickListener { onLeftButtonClick(buttonLeft3) }

        buttonRight1.setOnClickListener { onRightButtonClick(buttonRight1) }
        buttonRight2.setOnClickListener { onRightButtonClick(buttonRight2) }
        buttonRight3.setOnClickListener { onRightButtonClick(buttonRight3) }
    }

    private fun onLeftButtonClick(button: Button) {
        // Lógica al hacer clic en un botón izquierdo
        selectedButtonLeft?.isSelected = false
        selectedButtonLeft = button
        button.isSelected = true

        checkForMatch()
    }

    private fun onRightButtonClick(button: Button) {
        // Lógica al hacer clic en un botón derecho
        selectedButtonRight?.isSelected = false
        selectedButtonRight = button
        button.isSelected = true

        checkForMatch()
    }

    private fun checkForMatch() {
        // Lógica para comprobar si hay una pareja seleccionada
        if (selectedButtonLeft != null && selectedButtonRight != null) {
            // Aquí puedes comparar los textos de los botones y tomar acciones según la coincidencia
            // Por ejemplo, mostrar un mensaje, reiniciar el juego, etc.
            resetSelection()
        }
    }

    private fun resetSelection() {
        // Reinicia la selección de botones
        selectedButtonLeft?.isSelected = false
        selectedButtonRight?.isSelected = false
        selectedButtonLeft = null
        selectedButtonRight = null
    }
}
