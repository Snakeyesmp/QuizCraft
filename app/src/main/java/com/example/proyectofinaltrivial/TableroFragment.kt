package com.example.proyectofinaltrivial

import Consultas
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.proyectofinaltrivial.utils.Pregunta

class TableroFragment : Fragment() {
    private lateinit var consulta: Consultas
    private lateinit var viewModel: SharedViewModel
    var pos_jugador1: Int = 0
    var pos_jugador2: Int = 0
    var turno: Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        consulta = Consultas()
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
        Log.d("Consult", "Posicion: $posicion")
        Log.d("Consult", "Turnofuera: $turno")
        // Lógica para actualizar la posición del jugador 1 o jugador 2
        if (turno) {
            var casilla = tableroGrid.getChildAt(pos_jugador1)
            pos_jugador1 += posicion
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
            jugador1 = casilla.findViewById(R.id.jugador1)
            jugador1.background = context?.getDrawable(R.drawable.jugador1)

            consulta.obtenerPreguntaPorTipo(
                casilla.tag.toString()
            ) { pregunta ->
                mostrarPregunta(pregunta, casilla.tag.toString(), { respuesta ->
                    Log.d("Consult", "Respuesta: $respuesta")
                    if (respuesta) {
                        turno = true
                        viewModel.cambiarTurno(turno)
                    } else {
                        turno = false
                        viewModel.cambiarTurno(turno)
                    }
                    viewModel.cambiarTurno(turno)

                }, requireActivity().activityResultRegistry)
            }

        } else {
            var casilla = tableroGrid.getChildAt(pos_jugador2)
            pos_jugador2 += posicion
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
            jugador2 = casilla.findViewById(R.id.jugador2)
            jugador2.background = context?.getDrawable(R.drawable.jugador2)
            consulta.obtenerPreguntaPorTipo(
                casilla.tag.toString()
            ) { pregunta ->
                mostrarPregunta(pregunta, casilla.tag.toString(), { respuesta ->
                    if (respuesta) {
                        turno = false
                        viewModel.cambiarTurno(turno)
                    } else {
                        turno = true
                        viewModel.cambiarTurno(turno)
                    }
                    viewModel.cambiarTurno(turno)

                }, requireActivity().activityResultRegistry)
            }
        }
    }

    private fun mostrarPregunta(
        pregunta: Pregunta?,
        tipoPregunta: String, callback: (Boolean) -> Unit, registry: ActivityResultRegistry
    ) {
        val startForResult =
            registry.register("key", ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == AppCompatActivity.RESULT_OK) {
                    val isRespuestaCorrecta =
                        result.data?.getBooleanExtra("respuesta", false) ?: false
                    Log.d("Consultas", "RespuestaCons: $isRespuestaCorrecta")
                    callback(isRespuestaCorrecta)

                }
            }


        val intent = Intent(context, PreguntaActivity::class.java)
        intent.putExtra("tipoPregunta", tipoPregunta)
        intent.putExtra("pregunta", pregunta?.enunciado)
        if (tipoPregunta == "test"){
            intent.putStringArrayListExtra("opciones", ArrayList(pregunta?.opciones))
        }
        intent.putExtra("respuestaCorrecta", pregunta?.respuestaCorrecta)
        startForResult.launch(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
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
            when ((1..4).random()) {
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

    fun guardarPartida(): ArrayList<String> {
        val datos = ArrayList<String>()
        val posicion1 = pos_jugador1 // Ejemplo, sustituye con tus datos reales
        val posicion2 = pos_jugador2 // Ejemplo, sustituye con tus datos reales
        val turnoGuardar = turno // Ejemplo, sustituye con tus datos reales
        var tipoPregunta: ArrayList<String> = ArrayList()

        val tablero = requireView().findViewById<GridLayout>(R.id.tableroGrid)
        for (i in 0 until tablero.childCount) {
            val casilla = tablero.getChildAt(i)
            tipoPregunta.add(casilla.tag.toString())
        }
        Log.d("Consult", "Datos: $posicion1")
        Log.d("Consult", "Datos: $posicion2")
        Log.d("Consult", "Datos: $turnoGuardar")
        Log.d("Consult", "Datos: $tipoPregunta")
        datos.add(posicion1.toString())
        datos.add(posicion2.toString())
        datos.add(turnoGuardar.toString())
        val preguntas = tipoPregunta.joinToString(",")
        datos.add(preguntas)

        return datos
    }

    fun cargarPartida(partidaSeleccionada: Partida) {

        pos_jugador1 = partidaSeleccionada.posJugador1
        pos_jugador2 = partidaSeleccionada.posJugador2
        turno = partidaSeleccionada.turno.toBoolean()
        Log.d("Consult", "Datos: ${partidaSeleccionada.tiposPreguntas}")
        Log.d("Consult", "Datos: $pos_jugador1")
        Log.d("Consult", "Datos: $pos_jugador2")
        Log.d("Consult", "Datos: $turno")

        val tablero = requireView().findViewById<GridLayout>(R.id.tableroGrid)
        val tipoPreguntas = partidaSeleccionada.tiposPreguntas.split(",")
        for (i in 0 until tablero.childCount) {
            val casilla = tablero.getChildAt(i)
            casilla.tag = tipoPreguntas[i]
        }
        var casilla: Any
        var jugador1: View
        var jugador2: View
        for (i in 0 until pos_jugador1) {
            casilla = tablero.getChildAt(i)
            jugador1 = casilla.findViewById(R.id.jugador1)
            jugador1.background = context?.getDrawable(R.color.colorJugador1)
            if (i == pos_jugador1 - 1) {
                jugador1.background = context?.getDrawable(R.drawable.jugador1)
            }
        }
        for (i in 0 until pos_jugador2) {
            casilla = tablero.getChildAt(i)
            jugador2 = casilla.findViewById(R.id.jugador2)
            jugador2.background = context?.getDrawable(R.color.colorJugador2)
            if (i == pos_jugador2 - 1) {
                jugador2.background = context?.getDrawable(R.drawable.jugador2)
            }
        }


    }


}