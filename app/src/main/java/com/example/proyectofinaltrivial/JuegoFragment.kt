package com.example.proyectofinaltrivial

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class JuegoFragment : Fragment() {
    private lateinit var viewModel: SharedViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicialización del ViewModel compartido
        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        // Obtener referencia al botón de avance y asignar un Listener
        val botonAvanzar: Button = view.findViewById(R.id.botonAvanzar)
        botonAvanzar.setOnClickListener {
            avanzarJugador()
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
    private fun avanzarJugador() {
        // Generar un número aleatorio entre 1 y 6
        val random = generarNumeroAleatorio()

        // Registrar en el Log el número aleatorio generado
        Log.d("JuegoFragment", "Número aleatorio: $random")

        //Actualizar la posicion sumando el número aleatorio
        viewModel.actualizarPosicionJugador(random)
    }

    // Función para generar un número aleatorio entre 1 y 6
    private fun generarNumeroAleatorio(): Int {
        return (1..6).random()
    }
}
