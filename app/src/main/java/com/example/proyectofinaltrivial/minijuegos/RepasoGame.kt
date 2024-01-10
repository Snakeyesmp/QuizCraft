import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
/*
class RepasoActivity : AppCompatActivity() {

    private lateinit var repasoGame: RepasoGame
    private lateinit var preguntaActual: Pregunta

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repaso)

        val preguntasJSON = leerJSONPreguntas()
        repasoGame = RepasoGame(preguntasJSON)

        // Mostrar la primera pregunta
        mostrarNuevaPregunta()

        // Configurar el botón para pasar a la siguiente pregunta
        val siguientePreguntaButton: Button = findViewById(R.id.siguientePreguntaButton)
        siguientePreguntaButton.setOnClickListener {
            mostrarNuevaPregunta()
        }
    }

    private fun leerJSONPreguntas(): String {
        try {
            // Lee el archivo JSON desde assets
            val inputStream = assets.open("nombre_del_archivo.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            return String(buffer, Charsets.UTF_8)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return ""
    }

    private fun mostrarNuevaPregunta() {
        // Obtener nueva pregunta del juego
        preguntaActual = repasoGame.obtenerNuevaPregunta()

        // Actualizar la interfaz con las respuestas
        mostrarRespuestas(preguntaActual)
    }

    private fun mostrarRespuestas(pregunta: Pregunta) {
        val opcionesLayout: View = findViewById(R.id.opcionesLayout)

        // Limpiar las opciones anteriores
        opcionesLayout.removeAllViews()

        // Crear botón por cada respuesta
        for (i in 0 until pregunta.respuestas.length()) {
            val respuesta = pregunta.respuestas.getString(i)
            val button = Button(this)
            button.text = respuesta
            button.setOnClickListener {
                verificarRespuesta(pregunta, respuesta)
            }
            opcionesLayout.addView(button)
        }
    }

    private fun verificarRespuesta(pregunta: Pregunta, respuesta: String) {
        val esCorrecta = repasoGame.verificarRespuesta(pregunta, respuesta)
        if (esCorrecta) {
            Toast.makeText(this, "Respuesta Correcta", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Respuesta Incorrecta", Toast.LENGTH_SHORT).show()
        }

        // Mostrar la siguiente pregunta
        mostrarNuevaPregunta()
    }
}

 */