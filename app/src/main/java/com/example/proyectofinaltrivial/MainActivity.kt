package com.example.proyectofinaltrivial

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicialización del ViewModel compartido
        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]

        // Reemplazar el fragmento del tablero en el contenedor correspondiente
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentTablero, TableroFragment())
            .commit()

        // Reemplazar el fragmento del juego en el contenedor correspondiente
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentJuego, JuegoFragment())
            .commit()
    }
}
