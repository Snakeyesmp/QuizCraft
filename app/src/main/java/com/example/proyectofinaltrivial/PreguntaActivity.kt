package com.example.proyectofinaltrivial

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import com.example.proyectofinaltrivial.respuestas_fragments.FragmentTest

class PreguntaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_pregunta)
        window.statusBarColor = Color.parseColor("#7BA1CE")

        val enunciado = findViewById<TextView>(R.id.enunciado)

        val pregunta = intent.getStringExtra("pregunta")
        val tipoPregunta = intent.getStringExtra("tipoPregunta")



        enunciado.text = pregunta
        Log.d("FragmentTest", "TipoPregunta: $tipoPregunta")

        when (tipoPregunta) {
            "repaso" -> {

            }

            "palabra" -> {
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

    private fun showAreYouSureDialog() {
        AlertDialog.Builder(this).setMessage("¿Salir de la aplicación?").setCancelable(false)
            .setPositiveButton("Si") { _, _ ->
                finishAffinity() // Sale de la aplicación.
            }.setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }


    fun devolverResultado(isTrue: Boolean) {
        val intent = Intent()
        intent.putExtra("respuesta", isTrue)
        setResult(RESULT_OK, intent)
        finish()
    }

}