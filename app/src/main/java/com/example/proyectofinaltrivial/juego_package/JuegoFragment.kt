package com.example.proyectofinaltrivial.juego_package

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.proyectofinaltrivial.R
import com.example.proyectofinaltrivial.SharedViewModel
import com.example.proyectofinaltrivial.main_package.MainActivity


class JuegoFragment : Fragment() {

    private lateinit var viewModel: SharedViewModel
    private var textoJugador: TextView? = null
    private var perfilJugador: ImageView? = null
    private lateinit var resultadoDados: TextView
    private var botonAvanzarHabilitado = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Inicialización de textoJugador y perfilJugador
        resultadoDados = view.findViewById(R.id.simulacionDado)
        textoJugador = view.findViewById(R.id.jugador)
        perfilJugador = view.findViewById(R.id.perfilJugador)

        // Inicialización del ViewModel compartido
        viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]


        // Obtener referencia al botón de avance y asignar un Listener
        val botonAvanzar: ImageView = view.findViewById(R.id.botonAvanzar)

        botonAvanzar.setOnClickListener {
            val sharedPref = activity?.getSharedPreferences("Prefs_File", 0)
            val vibration = sharedPref?.getBoolean(MainActivity.VIBRATION_MODE, true)
            if (vibration == true) {
                val mainActivity = requireActivity() as MainActivity
                mainActivity.vibrar(requireContext())
            }

            if (botonAvanzarHabilitado) {
                botonAvanzarHabilitado = false
                // Iniciar la animación de rotación del botón de avance
                // (para simular el lanzamiento de un dado
                val rotateAnimation = RotateAnimation(
                    0f, 360f, // Grados de inicio y fin de la rotación
                    Animation.RELATIVE_TO_SELF, 0.5f, // Punto de pivote (centro en este caso)
                    Animation.RELATIVE_TO_SELF, 0.5f
                )
                rotateAnimation.interpolator =
                    LinearInterpolator() // Interpolador lineal para una rotación suave
                rotateAnimation.duration = 1000 // Duración de la animación en milisegundos
                rotateAnimation.repeatCount = 0 // No repetir la animación

                // Iniciar la animación y ocultar la imagen después de 1 segundo
                botonAvanzar.startAnimation(rotateAnimation)
                Handler(Looper.getMainLooper()).postDelayed({
                    val dado = generarNumeroAleatorio() // Generar un número aleatorio entre 1 y 6

                    resultadoDados.text = dado.toString()
                    Log.d("Consult", "Dado: $dado")
                    avanzarJugador(dado) // Llamar a la función para avanzar el jugador

                }, 1500)
            }
        }


        // Observar cambios en el turno del jugador
        viewModel.turnoJugador.observe(viewLifecycleOwner) { turno ->
            // Actualizar la interfaz para mostrar de quién es el turno
            if (turno) {
                // Turno jugador 1
                textoJugador?.text = getString(R.string.turno_de_jugador_1)
                perfilJugador?.setImageResource(R.drawable.jugador1)
            } else {
                // Turno jugador 2
                textoJugador?.text = getString(R.string.turno_de_jugador_2)
                perfilJugador?.setImageResource(R.drawable.jugador2)
            }

        }
        cambiarTurno()
    }

    // Inflar el layout del fragmento
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_juego, container, false)
    }

    override fun onResume() {
        super.onResume()
        botonAvanzarHabilitado = true
        resultadoDados.text = ""

    }

    // Función para avanzar el jugador
    private fun avanzarJugador(numero: Int) {
        viewModel.actualizarPosicionJugador(numero)
    }

    // Función para generar un número aleatorio entre 1 y 6
    private fun generarNumeroAleatorio(): Int {
        return (1..6).random()
    }

    private fun cambiarTurno() {
        val turnoActual = viewModel.turnoJugador.value ?: false
        viewModel.cambiarTurno(!turnoActual)
    }

}
