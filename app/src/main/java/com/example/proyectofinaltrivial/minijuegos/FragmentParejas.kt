package com.example.proyectofinaltrivial.minijuegos

import Consultas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
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

    private var parejas: String = ""
    private var intentos = 3
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
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


    /**
     * Configura los listeners para los botones.
     */
    private fun setButtonListeners() {
        buttonLeft1.setOnClickListener { onLeftButtonClick(buttonLeft1) }
        buttonLeft2.setOnClickListener { onLeftButtonClick(buttonLeft2) }
        buttonLeft3.setOnClickListener { onLeftButtonClick(buttonLeft3) }

        buttonRight1.setOnClickListener { onRightButtonClick(buttonRight1) }
        buttonRight2.setOnClickListener { onRightButtonClick(buttonRight2) }
        buttonRight3.setOnClickListener { onRightButtonClick(buttonRight3) }
    }


    /**
     * Maneja el clic en los botones de la izquierda.
     *
     * @param button El botón de la izquierda que se ha clicado.
     */
    private fun onLeftButtonClick(button: Button) {
        button.setBackgroundColor(resources.getColor(R.color.seleccionado))

        parejas = ""
        selectedButtonLeft?.isSelected = false
        selectedButtonLeft = button
        button.isSelected = true
        parejas += button.text.toString() + "_"
    }


    /**
     * Maneja el clic en los botones de la derecha.
     *
     * @param button El botón de la derecha que se ha clicado.
     */
    private fun onRightButtonClick(button: Button) {
        selectedButtonRight?.isSelected = false
        selectedButtonRight = button
        button.isSelected = true

        parejas += button.text.toString()

        if (parejas.contains("_")) {
            button.setBackgroundColor(resources.getColor(R.color.seleccionado))
            checkForMatch(parejas)
        } else {
            Toast.makeText(
                context, "Selecciona primero una opción de la izquierda", Toast.LENGTH_SHORT
            ).show()
        }

    }


    /**
     * Comprueba si las parejas seleccionadas son correctas.
     *
     * @param pareja La pareja seleccionada.
     */
    private fun checkForMatch(pareja: String) {
        val pareja1 = arguments?.getString("pregunta_0")
        val pareja2 = arguments?.getString("pregunta_1")
        val pareja3 = arguments?.getString("pregunta_2")

        if (pareja == pareja1 || pareja == pareja2 || pareja == pareja3) {
            selectedButtonLeft?.isSelected = false
            selectedButtonRight?.isSelected = false
            selectedButtonLeft?.isEnabled = false
            selectedButtonRight?.isEnabled = false
            selectedButtonLeft?.setBackgroundColor(resources.getColor(R.color.acierto))
            selectedButtonRight?.setBackgroundColor(resources.getColor(R.color.acierto))
            selectedButtonLeft = null
            selectedButtonRight = null
            parejas = ""
            // Check if all buttons are disabled
            if (isCheckAllButtonsDisabled()) {
                val preguntaActivity = activity as? PreguntaActivity
                preguntaActivity?.devolverResultado(true)
            }


        } else {
            // Enable all buttons
            enableAllButtons()

            selectedButtonLeft?.isSelected = true
            selectedButtonRight?.isSelected = true
            selectedButtonLeft = null
            selectedButtonRight = null
            parejas = ""
            if (intentos == 0) {
                AlertDialog.Builder(requireContext())
                    .setTitle("Has perdido")
                    .setMessage("Las parejas  son: \n$pareja1, \n$pareja2, \n$pareja3")
                    .setPositiveButton("Aceptar") { _, _ ->
                        val preguntaActivity = activity as? PreguntaActivity
                        preguntaActivity?.devolverResultado(false)
                    }
                    .setCancelable(false)
                    .show()

            }
        }
    }

    /**
     * Comprueba si todos los botones están deshabilitados.
     *
     * @return True si todos los botones están deshabilitados, false en caso contrario.
     */
    private fun isCheckAllButtonsDisabled(): Boolean {
        return !buttonLeft1.isEnabled && !buttonLeft2.isEnabled && !buttonLeft3.isEnabled && !buttonRight1.isEnabled && !buttonRight2.isEnabled && !buttonRight3.isEnabled
    }


    /**
     * Habilita todos los botones.
     */
    private fun enableAllButtons() {
        intentos--
        Toast.makeText(context, "Fallaste, te quedan $intentos intentos", Toast.LENGTH_SHORT)
            .show()
        // Pner fondo por defecto
        buttonLeft1.setBackgroundColor(resources.getColor(R.color.defecto))
        buttonLeft2.setBackgroundColor(resources.getColor(R.color.defecto))
        buttonLeft3.setBackgroundColor(resources.getColor(R.color.defecto))
        buttonRight1.setBackgroundColor(resources.getColor(R.color.defecto))
        buttonRight2.setBackgroundColor(resources.getColor(R.color.defecto))
        buttonRight3.setBackgroundColor(resources.getColor(R.color.defecto))
        buttonLeft1.isEnabled = true
        buttonLeft2.isEnabled = true
        buttonLeft3.isEnabled = true
        buttonRight1.isEnabled = true
        buttonRight2.isEnabled = true
        buttonRight3.isEnabled = true
    }


    /**
     * Carga los datos de las parejas.
     */
    private fun loadParejasData() {
        val pregunta0 = arguments?.getString("pregunta_0")
        val pregunta1 = arguments?.getString("pregunta_1")
        val pregunta2 = arguments?.getString("pregunta_2")

        // Crear una lista de preguntas y mezclarla aleatoriamente
        val preguntas = mutableListOf(
            pregunta0?.split("_")?.toMutableList(),
            pregunta1?.split("_")?.toMutableList(),
            pregunta2?.split("_")?.toMutableList()
        )

        preguntas.shuffle()

        // Asignar las preguntas a los botones de manera aleatoria
        buttonLeft1.text = preguntas[0]?.get(0) ?: ""
        buttonRight3.text = preguntas[0]?.get(1) ?: ""

        buttonLeft3.text = preguntas[1]?.get(0) ?: ""
        buttonRight2.text = preguntas[1]?.get(1) ?: ""

        buttonLeft2.text = preguntas[2]?.get(0) ?: ""
        buttonRight1.text = preguntas[2]?.get(1) ?: ""
    }

}