package com.example.proyectofinaltrivial

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import com.example.proyectofinaltrivial.minijuegos.AhorcadoGame
import com.example.proyectofinaltrivial.minijuegos.FragmentParejas
import com.example.proyectofinaltrivial.minijuegos.FragmentTest
import com.example.proyectofinaltrivial.minijuegos.RepasoGame

class PreguntaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_pregunta)
        window.statusBarColor = Color.parseColor("#7BA1CE")

        val enunciado = findViewById<TextView>(R.id.enunciado)

        val tipoPregunta = intent.getStringExtra("tipoPregunta")
        Log.d("Consultas", "Tipo de pregunta: $tipoPregunta")

        if (tipoPregunta == "parejas") {
            enunciado.text = "Junta las parejas, a la izquierda los paises y a la derecha su capital"
        } else {
            val pregunta = intent.getStringExtra("pregunta")
            enunciado.text = pregunta        }

        when (tipoPregunta) {

            "palabra" -> {
                mostrarDialogoInstrucciones()
                val respuestaCorrecta = intent.getStringExtra("respuestaCorrecta")
                val fragmentRellenar = AhorcadoGame()

                val datos = Bundle()
                datos.putString("respuestaCorrecta", respuestaCorrecta)
                fragmentRellenar.arguments = datos
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_respuesta, fragmentRellenar)
                    .commit()
            }



            "repaso" -> {
                mostrarDialogoInstrucciones()
                val respuestaCorrecta = intent.getStringExtra("respuestaCorrecta")
                val fragmentRellenar = RepasoGame()

                val datos = Bundle()
                datos.putString("respuestaCorrecta", respuestaCorrecta)
                fragmentRellenar.arguments = datos
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_respuesta, fragmentRellenar)
                    .commit()

            }

            "test" -> {
                val opciones = intent.getStringArrayListExtra("opciones")
                val respuestaCorrecta = intent.getStringExtra("respuestaCorrecta")

                val fragmentTest = FragmentTest()
                val datos = Bundle()
                Log.d("FragmentTest", "Opciones: ${opciones?.get(0)}")
                datos.putStringArrayList("opciones", opciones)
                datos.putString("respuestaCorrecta", respuestaCorrecta)
                fragmentTest.arguments = datos
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_respuesta, fragmentTest)
                    .commit()
            }

            "parejas" -> {

                val fragmentParejas = FragmentParejas()
                val datos = Bundle()
                datos.putString("pregunta_0", intent.getStringExtra("pregunta_0"))
                datos.putString("pregunta_1", intent.getStringExtra("pregunta_1"))
                datos.putString("pregunta_2", intent.getStringExtra("pregunta_2"))

                fragmentParejas.arguments = datos
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_respuesta, fragmentParejas)
                    .commit()

            }

        }


        val callback = object : OnBackPressedCallback(
            true // default to enabled
        ) {
            override fun handleOnBackPressed() {
                showAreYouSureDialog()
            }
        }
        this.onBackPressedDispatcher.addCallback(this, callback)

    }


    /**
     * Muestra un diálogo de confirmación antes de salir de la aplicación.
     */
    private fun showAreYouSureDialog() {
        AlertDialog.Builder(this).setMessage("¿Salir de la aplicación?").setCancelable(false)
            .setPositiveButton("Si") { _, _ ->
                finishAffinity() // Sale de la aplicación.
            }.setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    /**
     * Devuelve el resultado del minijuego a la actividad que lo ha llamado.
     *
     * @param isTrue true si el minijuego se ha completado correctamente, false en caso contrario.
     */
    fun devolverResultado(isTrue: Boolean) {
        val intent = Intent()
        intent.putExtra("respuesta", isTrue)
        setResult(RESULT_OK, intent)
        finish()
    }

    /**
     * Muestra un diálogo con las instrucciones del minijuego.
     */
    private fun mostrarDialogoInstrucciones() {
        AlertDialog.Builder(this)
            .setTitle("Instrucciones")
            .setMessage("Para completar correctamenta el minijuego debes introducir la palabra correcta en el campo de texto.\nIMPORTANTE: Las palabras que contengan tilde hay que poner tilde.")
            .setCancelable(false)
            .setPositiveButton("Ok") { _, _ ->
                // No hacer nada
            }.show()
    }

}