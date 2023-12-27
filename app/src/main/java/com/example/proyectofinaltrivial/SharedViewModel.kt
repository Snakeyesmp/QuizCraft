package com.example.proyectofinaltrivial

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    // LiveData mutable para la posición del jugador
    private val _posicionJugador = MutableLiveData<Int>()

    // LiveData inmutable para exponer la posición del jugador
    val posicionJugador: LiveData<Int>
        get() = _posicionJugador

    // Función para actualizar la posición del jugador
    fun actualizarPosicionJugador(posicion: Int) {
        // Asigna el nuevo valor de posición al LiveData
        _posicionJugador.value = posicion
    }
}
