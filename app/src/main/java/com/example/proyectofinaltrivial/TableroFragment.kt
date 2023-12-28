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
    private val Consulta = Consultas()
    private lateinit var viewModel: SharedViewModel
    private var pos_jugador1: Int = 0
    private var pos_jugador2: Int = 0
    private var turno: Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Inicialización del ViewModel compartido
        viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        // Observa los cambios en la posición del jugador
        viewModel.posicionJugador.observe(viewLifecycleOwner, Observer { posicion ->
            actualizarPosicion(posicion)
        })
    }

    private fun actualizarPosicion(posicion: Int) {
        val tableroGrid: GridLayout = requireView().findViewById(R.id.tableroGrid)

        Log.d("TableroFragment", "Posición: $posicion")
        // Lógica para actualizar la posición del jugador 1 o jugador 2
        if (turno) {
            var casilla = tableroGrid.getChildAt(pos_jugador1)
            pos_jugador1 += posicion
            Log.d("TableroFragment", "Posición1: $pos_jugador1")
            // Control del límite máximo de la posición del jugador 1
            if (pos_jugador1 >= 20) {
                pos_jugador1 = 20
            }
            var jugador1: View

            // Itera sobre las casillas para dibujar el progreso del jugador 1
            for (i in 0 until pos_jugador1) {
                casilla = tableroGrid.getChildAt(i)
                jugador1 = casilla.findViewById(R.id.jugador1)
                jugador1.background = context?.getDrawable(R.color.colorJugador1)
            }
            jugador1 = casilla.findViewById<View>(R.id.jugador1)
            jugador1.background = context?.getDrawable(R.color.colorJugador1)
            casilla = tableroGrid.getChildAt(pos_jugador1)
            Log.d("Consulta", "Casilla: ${casilla.tag}")
            jugador1 = casilla.findViewById(R.id.jugador1)
            jugador1.background = context?.getDrawable(R.drawable.jugador1)
            Consulta.obtenerPreguntaPorTipo(casilla.tag.toString(), this.requireContext())
            turno = false

        } else {
            var casilla = tableroGrid.getChildAt(pos_jugador2)
            pos_jugador2 += posicion
            Log.d("TableroFragment", "Posición2: $pos_jugador2")
            // Control del límite máximo de la posición del jugador 2
            if (pos_jugador2 >= 20) {
                pos_jugador2 = 20
            }
            var jugador2: View

            // Itera sobre las casillas para dibujar el progreso del jugador 2
            for (i in 0 until pos_jugador2) {
                casilla = tableroGrid.getChildAt(i)
                jugador2 = casilla.findViewById(R.id.jugador2)
                jugador2.background = context?.getDrawable(R.color.colorJugador2)
            }
            jugador2 = casilla.findViewById(R.id.jugador2)
            jugador2.background = context?.getDrawable(R.color.colorJugador2)
            casilla = tableroGrid.getChildAt(pos_jugador2)
            Log.d("Consulta", "Casilla: ${casilla.tag}")
            jugador2 = casilla.findViewById(R.id.jugador2)
            jugador2.background = context?.getDrawable(R.drawable.jugador2)
            Consulta.obtenerPreguntaPorTipo(casilla.tag.toString(), this.requireContext())
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
            // Crea las casillas y las añade al GridLayout
            val casilla = crearCasilla(requireContext(), i)
            val params = GridLayout.LayoutParams()

            // Configuración de tamaño para las casillas
            params.width = 0
            params.height = GridLayout.LayoutParams.WRAP_CONTENT
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)


            // TODO Cambair random a 4
            var tipoPregunto = /*(1..4).random()*/ 3
            when (tipoPregunto) {
                1 -> {
                    casilla.tag = "repaso"
                }

                2 -> {
                    casilla.tag = "palabra"
                }

                3 -> {
                    casilla.tag = "test"
                }

                4 -> {
                    casilla.tag = "parejas"
                }

            }
            if (i == 0) {
                casilla.tag = "inicio"
            }
            if (i == 20) {
                casilla.tag = "preguntaFinal"
            }
            casilla.layoutParams = params
            tableroGrid.addView(casilla)
        }

        return view
    }

    private fun crearCasilla(context: Context, id: Int): View {
        val casillaView = LayoutInflater.from(context).inflate(R.layout.layout_casilla, null)
        val jugador1 = casillaView.findViewById<View>(R.id.jugador1)
        val jugador2 = casillaView.findViewById<View>(R.id.jugador2)

        // Establecer tamaño fijo para las casillas (por ejemplo, 100dp x 100dp)
        val casillaSize = 100 // Obtener el tamaño desde resources

        // Configurar el ancho y alto de las casillas
        jugador1.layoutParams.height = casillaSize
        jugador2.layoutParams.height = casillaSize

        // Configuración inicial de las casillas
        if (id == 0) {
            jugador1.background = context.getDrawable(R.drawable.jugador1)
            jugador2.background = context.getDrawable(R.drawable.jugador2)

        } else if (id == 20) {
            jugador1.background = context.getDrawable(R.drawable.meta)
            jugador2.background = context.getDrawable(R.drawable.meta)
        }

        return casillaView
    }

    private fun preguntaFinal() {
        Log.d("JuegoFragment", "Has llegado a la meta")
    }
}
