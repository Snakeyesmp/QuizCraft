package com.example.proyectofinaltrivial.minijuegos

import Consultas
import android.os.Bundle
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.proyectofinaltrivial.PreguntaActivity
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

    private var parejas = ""
    private var intentos = 3

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


        loadParejasData()
        setButtonListeners()

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
        parejas = ""
        selectedButtonLeft?.isSelected = false
        selectedButtonLeft = button
        button.isSelected = true
        parejas += button.text.toString() + "_"
    }


    private fun onRightButtonClick(button: Button) {
        selectedButtonRight?.isSelected = false
        selectedButtonRight = button
        button.isSelected = true
        parejas += button.text.toString()
        checkForMatch(parejas)
    }

    private fun checkForMatch(pareja: String) {
        val pareja1 = arguments?.getString("pregunta_0")
        val pareja2 = arguments?.getString("pregunta_1")
        val pareja3 = arguments?.getString("pregunta_2")

        if (pareja == pareja1 || pareja == pareja2 || pareja == pareja3) {
            selectedButtonLeft?.isEnabled = false
            selectedButtonRight?.isEnabled = false
            selectedButtonLeft?.isSelected = false
            selectedButtonRight?.isSelected = false
            selectedButtonLeft = null
            selectedButtonRight = null
            parejas = ""

            checkIfAllMatched()


        } else {
            // Enable all buttons
            enableAllButtons()

            parejas = ""
            intentos--
            if (intentos == 0) {
                val preguntaActivity = activity as? PreguntaActivity
                preguntaActivity?.devolverResultado(false)
            }
        }
    }

    // Comprobar si se han emparejado todas las parejas
    private fun checkIfAllMatched() {
        if (!buttonLeft1.isEnabled && !buttonLeft2.isEnabled && !buttonLeft3.isEnabled &&
            !buttonRight1.isEnabled && !buttonRight2.isEnabled && !buttonRight3.isEnabled
        ) {
            val preguntaActivity = activity as? PreguntaActivity
            preguntaActivity?.devolverResultado(true)
        }
    }

    private fun enableAllButtons() {
        buttonLeft1.isEnabled = true
        buttonLeft2.isEnabled = true
        buttonLeft3.isEnabled = true
        buttonRight1.isEnabled = true
        buttonRight2.isEnabled = true
        buttonRight3.isEnabled = true
    }

    private fun loadParejasData() {
        val pareja1 = arguments?.getString("pregunta_0")
        val pareja2 = arguments?.getString("pregunta_1")
        val pareja3 = arguments?.getString("pregunta_2")

        // Pareja 1
        buttonLeft1.text = pareja1?.split("_")?.get(0) ?: ""
        buttonRight2.text = pareja1?.split("_")?.get(1) ?: ""
        // Pareja 2
        buttonLeft3.text = pareja2?.split("_")?.get(0) ?: ""
        buttonRight1.text = pareja2?.split("_")?.get(1) ?: ""
        // Pareja 3
        buttonLeft2.text = pareja3?.split("_")?.get(0) ?: ""
        buttonRight3.text = pareja3?.split("_")?.get(1) ?: ""


    }

}