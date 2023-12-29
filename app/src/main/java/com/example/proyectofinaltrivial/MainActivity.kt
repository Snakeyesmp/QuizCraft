package com.example.proyectofinaltrivial

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
