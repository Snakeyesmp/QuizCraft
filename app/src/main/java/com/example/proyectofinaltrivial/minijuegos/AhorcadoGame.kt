
package com.example.proyectofinaltrivial.minijuegos
/*
class AhorcadoActivity : AppCompatActivity() {

    private lateinit var ahorcado: Ahorcado

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ahorcado)

        // Aquí deberías inicializar la instancia de Ahorcado con la palabra deseada
        ahorcado = Ahorcado("ANDROID")

        actualizarVista()

        val gridLayoutLetras: GridLayout = findViewById(R.id.gridLayoutLetras)

        // Generar botones para cada letra del abecedario
        for (letra in 'A'..'Z') {
            val button = Button(this)
            button.text = letra.toString()
            button.setOnClickListener {
                // Manejar el clic del botón
                onLetraClick(letra)
            }

            // Agregar el botón al GridLayout
            gridLayoutLetras.addView(button)
        }
    }

    private fun onLetraClick(letra: Char) {
        val estado = ahorcado.intento(letra)
        actualizarVista()

        // Aquí puedes manejar el estado del juego según lo que devuelva Ahorcado
        when (estado) {
            EstadoJuego.LETRA_CORRECTA -> {
                // Letra correcta, podrías cambiar el color del botón a verde
                // (puedes acceder al botón correspondiente mediante su letra)
            }
            EstadoJuego.LETRA_INCORRECTA -> {
                // Letra incorrecta, podrías cambiar el color del botón a rojo
                // (puedes acceder al botón correspondiente mediante su letra)
            }
            // ... (manejar otros casos según sea necesario)
        }
    }

    private fun actualizarVista() {
        // Actualizar la vista con la palabra actual y otras informaciones del juego
        val textViewPalabra: TextView = findViewById(R.id.textViewPalabra)
        textViewPalabra.text = ahorcado.obtenerPalabraActual()
    }
}

 */