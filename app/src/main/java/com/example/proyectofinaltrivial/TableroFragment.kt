package com.example.proyectofinaltrivial

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class TableroFragment : Fragment() {
    private lateinit var viewModel: SharedViewModel
    private var pos_jugador1: Int = 0
    private var pos_jugador2: Int = 0
    private var turno: Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicialización del ViewModel compartido
        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        // Observador para la posición del jugador actualizada desde SharedViewModel
        viewModel.posicionJugador.observe(viewLifecycleOwner, Observer { posicion ->
            actualizarPosicion(posicion)
        })
    }

    private fun actualizarPosicion(posicion: Int) {
        val tableroGrid: GridLayout = requireView().findViewById(R.id.tableroGrid)

        // Registro en el Log de la posición actual
        Log.d("TableroFragment", "Posición: $posicion")

        // Lógica para actualizar la posición de los jugadores en el tablero
        if (turno) {
            // Turno del jugador 1
            var casilla = tableroGrid.getChildAt(pos_jugador1)
            pos_jugador1 += posicion

            // Registro en el Log de la posición del jugador 1
            Log.d("TableroFragment", "Posición1: $pos_jugador1")

            // Verificar si el jugador 1 ha llegado a la meta
            if (pos_jugador1 >= 20) {
                preguntaFinal()
                pos_jugador1 = 20
            }

            var jugador1: View

            // Actualizar la vista del tablero para reflejar el avance del jugador 1
            for (i in 0 until pos_jugador1) {
                casilla = tableroGrid.getChildAt(i)
                jugador1 = casilla.findViewById(R.id.jugador1)
                jugador1.background = context?.getDrawable(R.color.colorJugador1)
            }

            jugador1 = casilla.findViewById<View>(R.id.jugador1)
            jugador1.background = context?.getDrawable(R.drawable.jugador1)

            casilla = tableroGrid.getChildAt(pos_jugador1)
            jugador1 = casilla.findViewById(R.id.jugador1)
            jugador1.background = context?.getDrawable(R.drawable.jugador1)

            turno = false

        } else {
            // Turno del jugador 2
            var casilla = tableroGrid.getChildAt(pos_jugador2)
            pos_jugador2 += posicion

            // Registro en el Log de la posición del jugador 2
            Log.d("TableroFragment", "Posición2: $pos_jugador2")

            // Verificar si el jugador 2 ha llegado a la meta
            if (pos_jugador2 >= 20) {
                preguntaFinal()
                pos_jugador2 = 20
            }

            var jugador2: View

            // Actualizar la vista del tablero para reflejar el avance del jugador 2
            for (i in 0 until pos_jugador2) {
                casilla = tableroGrid.getChildAt(i)
                jugador2 = casilla.findViewById(R.id.jugador2)
                jugador2.background = context?.getDrawable(R.color.colorJugador2)
            }

            jugador2 = casilla.findViewById(R.id.jugador2)
            jugador2.background = context?.getDrawable(R.drawable.jugador2)

            casilla = tableroGrid.getChildAt(pos_jugador2)
            jugador2 = casilla.findViewById(R.id.jugador2)
            jugador2.background = context?.getDrawable(R.drawable.jugador2)

            turno = true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tablero, container, false)
        val tableroGrid: GridLayout = view.findViewById(R.id.tableroGrid)

        // Agregar las casillas al GridLayout
        val totalCasillas = 21
        for (i in 0 until totalCasillas) {
            val casilla = crearCasilla(requireContext(), i)
            val params = GridLayout.LayoutParams()

            params.width = 0
            params.height = GridLayout.LayoutParams.WRAP_CONTENT
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)

            casilla.layoutParams = params
            tableroGrid.addView(casilla)
        }

        return view
    }

    // Método para crear una casilla del tablero
    private fun crearCasilla(context: Context, id: Int): View {
        val casillaView = LayoutInflater.from(context).inflate(R.layout.layout_casilla, null)
        val jugador1 = casillaView.findViewById<View>(R.id.jugador1)
        val jugador2 = casillaView.findViewById<View>(R.id.jugador2)

        // Establecer tamaño fijo para las casillas (por ejemplo, 100dp x 100dp)
        val casillaSize = 100 // Obtener el tamaño desde resources

        // Configurar el ancho y alto de las casillas
        jugador1.layoutParams.height = casillaSize
        jugador2.layoutParams.height = casillaSize

        // Asignar imágenes a las casillas según la posición
        if (id == 0) {
            jugador1.background = context.getDrawable(R.drawable.jugador1)
            jugador2.background = context.getDrawable(R.drawable.jugador2)
        } else if (id == 20) {
            jugador1.background = context.getDrawable(R.drawable.meta)
            jugador2.background = context.getDrawable(R.drawable.meta)
        }

        return casillaView
    }

    // Método llamado al llegar a la meta
    private fun preguntaFinal() {
        Log.d("JuegoFragment", "Has llegado a la meta")
        // Aquí podrías realizar cualquier acción adicional al llegar a la meta
    }
}
