package com.example.proyectofinaltrivial

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val _posicionJugador = MutableLiveData<Int>()
    private val _turnoJugador = MutableLiveData<Boolean>()


    val posicionJugador: LiveData<Int>
        get() = _posicionJugador

    val turnoJugador: LiveData<Boolean>
        get() = _turnoJugador

    // Métodos para actualizar la posición del jugador y el turno
    fun actualizarPosicionJugador(nuevaPosicion: Int) {
        _posicionJugador.value = nuevaPosicion
    }

    fun cambiarTurno(turno: Boolean) {
        _turnoJugador.value = turno
    }
}
