package com.example.proyectofinaltrivial.minijuegos

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectofinaltrivial.R
import java.util.*

class AhorcadoGame : AppCompatActivity() {

    private val images = listOf(
        R.drawable.ahorcado_0,
        R.drawable.ahorcado_1,
        R.drawable.ahorcado_2,
        R.drawable.ahorcado_3,
        R.drawable.ahorcado_4,
        R.drawable.ahorcado_5,
        R.drawable.ahorcado_6
    )
    private var imagenActualIndice = 0


    private lateinit var wordToGuess: String
    private lateinit var guessedWord: CharArray
    private var lives = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.minijuego_ahorcado)

        val wordTextView = findViewById<TextView>(R.id.wordTextView)
        val livesTextView = findViewById<TextView>(R.id.livesTextView)
        val guessEditText = findViewById<EditText>(R.id.guessEditText)
        val confirmButton = findViewById<Button>(R.id.confirmButton)

        // Generar una palabra aleatoria
        wordToGuess = generateRandomWord()
        guessedWord = CharArray(wordToGuess.length) { '_' }

        wordTextView.text = String(guessedWord)
        livesTextView.text = "Vidas: $lives"

        confirmButton.setOnClickListener {
            val guess = guessEditText.text.toString().toLowerCase(Locale.ROOT)
            if (guess.isNotEmpty()) {
                manejarAcierto(guess[0], wordTextView, livesTextView)
                guessEditText.text.clear()
            }
        }
    }

    private fun generateRandomWord(): String {
        // Aquí puedes generar una palabra aleatoria de la forma que prefieras
        // En este ejemplo, simplemente devolvemos una palabra fija
        return "android"
    }

    private fun manejarAcierto(guess: Char, wordTextView: TextView, livesTextView: TextView) {
        var guessedCorrectly = false

        for (i in wordToGuess.indices) {
            if (wordToGuess[i] == guess) {
                guessedWord[i] = guess
                guessedCorrectly = true
            }
        }

        val imageView = findViewById<ImageView>(R.id.imageView)

        if (guessedCorrectly) {
            wordTextView.text = String(guessedWord)
            if (!guessedWord.contains('_')) {
                // El usuario ha adivinado la palabra completa
                livesTextView.text = "¡Has ganado!"
            }
        } else {
            lives--
            if (lives > 0) {
                livesTextView.text = "Vidas: $lives"
                // Cambia la imagen si el usuario adivina incorrectamente
                imagenActualIndice++
                if (imagenActualIndice < images.size) {
                    imageView.setImageResource(images[imagenActualIndice])
                }
            } else {
                // El usuario ha perdido todas las vidas
                livesTextView.text = "¡Has perdido!"
            }
        }
    }
}