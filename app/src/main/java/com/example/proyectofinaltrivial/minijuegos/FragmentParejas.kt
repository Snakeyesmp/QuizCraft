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

        loadParejasData()

        return view
    }

   /* private fun setButtonListeners() {
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
*/




    private fun loadParejasData() {

    }
}