package com.example.proyectofinaltrivial

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider


class JuegoFragment : Fragment() {
    private lateinit var viewModel: SharedViewModel
    private var turnoJugador = false // Variable para controlar el turno, inicialmente jugador 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicialización del ViewModel compartido
        viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        // Obtener referencia al botón de avance y asignar un Listener
        val botonAvanzar: Button = view.findViewById(R.id.botonAvanzar)
        botonAvanzar.setOnClickListener {

            val imagenDado = view.findViewById<ImageView>(R.id.simulacionDado)
            imagenDado.background= context?.getDrawable(R.drawable.dado_general)
            imagenDado.visibility = View.VISIBLE

            val rotateAnimation = RotateAnimation(
                0f, 360f, // Grados de inicio y fin de la rotación
                Animation.RELATIVE_TO_SELF, 0.5f, // Punto de pivote (centro en este caso)
                Animation.RELATIVE_TO_SELF, 0.5f
            )
            rotateAnimation.interpolator = LinearInterpolator() // Interpolador lineal para una rotación suave
            rotateAnimation.duration = 1000 // Duración de la animación en milisegundos
            rotateAnimation.repeatCount = 0 // No repetir la animación

            // Iniciar la animación y ocultar la imagen después de 1 segundo
            imagenDado.startAnimation(rotateAnimation)
            Handler(Looper.getMainLooper()).postDelayed({
                val dado = generarNumeroAleatorio() // Generar un número aleatorio entre 1 y 6
                avanzarJugador(dado) // Llamar a la función para avanzar el jugador
                cambiarTurno() // Cambiar el turno después de avanzar el jugador
                imagenDado.visibility = View.INVISIBLE // Ocultar la imagen del dado
            }, 1000)
        }

    }

    // Inflar el layout del fragmento
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_juego, container, false)
    }

    // Función para avanzar el jugador
    private fun avanzarJugador(numero: Int ) {
        val posicionActual = viewModel.posicionJugador.value ?: 0
        viewModel.actualizarPosicionJugador(posicionActual + numero)

    }

    // Función para generar un número aleatorio entre 1 y 6
    private fun generarNumeroAleatorio(): Int {
        return (1..6).random()
    }
    private fun cambiarTurno() {
        val textoJugador = view?.findViewById<TextView>(R.id.jugador)
        val perfilJugador = view?.findViewById<ImageView>(R.id.perfilJugador)
        if (turnoJugador) {
            // Turno jugador 1
            textoJugador?.text = "Turno de Jugador 1"
            perfilJugador?.setImageResource(R.drawable.jugador1)

        } else {
           // Turno jugador 2
            textoJugador?.text = "Turno de Jugador 2"
            perfilJugador?.setImageResource(R.drawable.jugador2)

        }
        turnoJugador = !turnoJugador

    }
}
