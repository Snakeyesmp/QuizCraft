package com.example.proyectofinaltrivial.minijuegos


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.proyectofinaltrivial.PreguntaActivity
import com.example.proyectofinaltrivial.R


class RepasoGame : Fragment() {

    private var intentos = 3
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val respuestaCorrecta = arguments?.getString("respuestaCorrecta")
        val botonComprobar = view.findViewById<Button>(R.id.botonComprobar)
        val respuesta = view.findViewById<EditText>(R.id.respuesta)
        val inetntosText = view.findViewById<TextView>(R.id.intentos)
        inetntosText.text = "Intentos restantes: $intentos"
            if (intentos!= 0){
                botonComprobar.setOnClickListener {
                    if (comprobarRespuesta(respuesta.text.toString(), respuestaCorrecta)) {

                        respuesta.setBackgroundColor(resources.getColor(R.color.acierto))
                        Toast.makeText(context, "Has acertado", Toast.LENGTH_SHORT).show()

                        Handler(Looper.getMainLooper()).postDelayed({
                            val preguntaActivity = activity as? PreguntaActivity
                            preguntaActivity?.devolverResultado(true)
                            respuesta.background = resources.getDrawable(R.drawable.transparent_background)
                        }, 1500)
                    } else {
                        intentos--
                        if (intentos == 0) {

                            respuesta.setBackgroundColor(resources.getColor(R.color.fallo))
                            Toast.makeText(context, "Has fallado, no te quedan intentos", Toast.LENGTH_SHORT).show()

                            Handler(Looper.getMainLooper()).postDelayed({
                                val preguntaActivity = activity as? PreguntaActivity
                                preguntaActivity?.devolverResultado(false)
                                respuesta.background = resources.getDrawable(R.drawable.borde_linear_layout)

                            }, 1500)
                        } else {
                            respuesta.setBackgroundColor(resources.getColor(R.color.fallo))
                            Toast.makeText(context, "Has fallado, te quedan $intentos intentos", Toast.LENGTH_SHORT).show()
                            Handler(Looper.getMainLooper()).postDelayed({
                                respuesta.text.clear()
                                respuesta.background = resources.getDrawable(R.drawable.borde_linear_layout)
                                inetntosText.text = "Intentos restantes: $intentos"


                            }, 1500)
                        }
                    }
                }

            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.minijuego_repaso, container, false)

    }

    private fun comprobarRespuesta(respuesta: String, respuestaCorrecta: String?): Boolean {

        return respuesta == respuestaCorrecta
    }


}

