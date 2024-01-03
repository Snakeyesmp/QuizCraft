package com.example.proyectofinaltrivial.main_package

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.CombinedVibration
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectofinaltrivial.DBHelper
import com.example.proyectofinaltrivial.JuegoFragment
import com.example.proyectofinaltrivial.R
import com.example.proyectofinaltrivial.TableroFragment
import com.example.proyectofinaltrivial.utils.ConnectivityReceiver
import com.example.proyectofinaltrivial.utils.InteractionUtils.disableUserInteractions
import com.example.proyectofinaltrivial.utils.InternetUtils.isInternetAvailable
import com.example.proyectofinaltrivial.utils.InternetUtils.showNoInternetDialog
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {
    companion object {
        const val PREFS_NAME = "Prefs_File"
        const val VIBRATION_MODE = "vibration_mode"
        const val SOUND_MODE = "sound_mode"

    }

    private var tableroFragment: TableroFragment? = null
    private var dbHelper: DBHelper? = null
    private var botonSettings: ImageView? = null

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var sharedPref: SharedPreferences
    private var connectivityReceiver: ConnectivityReceiver? = null
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        botonSettings = findViewById(R.id.btnSettings)
        mediaPlayer = MediaPlayer.create(this, R.raw.sound)
        mediaPlayer.isLooping = true
        sharedPref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        viewModel = MainViewModel(sharedPref, mediaPlayer, this)


        setupSettingsDialog()
        initializeFragments()
        initializeConnectivityReceiver()

        if (!isInternetAvailable(this)) {
            showNoInternetDialog(this)
            disableUserInteractions(window)
        }


    }

    /**
     * Configura y muestra el diálogo de ajustes al hacer clic en el botón de ajustes.
     * Este método configura la funcionalidad del botón de ajustes, crea el diálogo de ajustes
     * y gestiona las acciones dentro del diálogo.
     */
    private fun setupSettingsDialog() {
        // Obtener referencia al botón de ajustes en la interfaz de usuario
        val botonSettings = findViewById<ImageView>(R.id.btnSettings)
        // Configurar un listener para el botón de ajustes
        botonSettings.setOnClickListener {
            // Crear un diálogo de ajustes
            val builder = createSettingsDialog()
            val alertDialog = builder.create()
            alertDialog.show()

            // Obtener referencias a los botones dentro del diálogo
            val buttonGuardar = alertDialog.findViewById<Button>(R.id.btnGuardar)
            val buttonReiniciar = alertDialog.findViewById<Button>(R.id.btnReiniciar)
            val buttonCargarPartida = alertDialog.findViewById<Button>(R.id.btnCargarPartida)
            val buttonVibrate = alertDialog.findViewById<ImageView>(R.id.vibration)
            val buttonSound = alertDialog.findViewById<ImageView>(R.id.sound)

            // Configurar la interfaz de usuario del diálogo de ajustes
            configureSettingsDialogUI(buttonVibrate!!, buttonSound!!)

            // Configurar las acciones dentro del diálogo de ajustes
            configureSettingsDialogActions(
                alertDialog, buttonGuardar, buttonReiniciar, buttonCargarPartida
            )
        }
    }


    /**
     * Crea y devuelve un AlertDialog.Builder configurado con un diseño personalizado para el diálogo de ajustes.
     * @return Un AlertDialog.Builder configurado con el diseño y los elementos del diálogo de ajustes.
     */
    private fun createSettingsDialog(): AlertDialog.Builder {
        // Crear un AlertDialog.Builder con un estilo personalizado
        val builder = AlertDialog.Builder(this, R.style.CustomAlertDialog)

        // Obtener el inflater para inflar el diseño del diálogo de ajustes
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.activity_settings, null)

        // Configurar la vista del diálogo con el diseño personalizado
        builder.setView(dialogView)

        // Obtener referencias a los elementos dentro del diseño del diálogo
        val titleTextView = dialogView.findViewById<TextView>(R.id.alertTitle)
        val messageTextView = dialogView.findViewById<TextView>(R.id.alertMessage)

        // Establecer el título y el mensaje del diálogo
        titleTextView.text = "Configuración"
        messageTextView.text = "¿Qué desea realizar?"

        // Configurar el botón negativo para cerrar el diálogo al hacer clic
        builder.setNegativeButton("Cerrar") { dialog, _ ->
            dialog.dismiss()
        }

        return builder // Devolver el AlertDialog.Builder configurado
    }

    /**
     * Configura la interfaz de usuario del diálogo de ajustes según los modos de sonido y vibración.
     * @param buttonVibrate ImageView para controlar la representación del modo de vibración.
     * @param buttonSound ImageView para controlar la representación del modo de sonido.
     */
    private fun configureSettingsDialogUI(buttonVibrate: ImageView, buttonSound: ImageView) {
        // Obtener los modos de sonido y vibración desde las preferencias compartidas
        val soundMode = sharedPref.getBoolean(SOUND_MODE, true)
        var vibrationMode = sharedPref.getBoolean(VIBRATION_MODE, true)

        // Configurar la representación de la imagen del botón de vibración según el modo de vibración
        if (vibrationMode) {
            buttonVibrate.setImageResource(R.drawable.vibration_on)
        } else {
            buttonVibrate.setImageResource(R.drawable.vibration_off)
        }

        // Configurar la representación de la imagen del botón de sonido según el modo de sonido
        if (soundMode) {
            buttonSound.setImageResource(R.drawable.sound_on)
            mediaPlayer.start()
        } else {
            buttonSound.setImageResource(R.drawable.sound_off)
        }

        // Configurar los botones de vibración y sonido para gestionar sus acciones
        configureVibrationAndSoundButtons(buttonVibrate, buttonSound, soundMode, vibrationMode)
    }

    /**
     * Configura los botones de control de vibración y sonido para gestionar cambios en sus modos respectivos.
     * @param buttonVibrate ImageView para el control del modo de vibración.
     * @param buttonSound ImageView para el control del modo de sonido.
     * @param soundMode Estado actual del modo de sonido.
     * @param vibrationMode Estado actual del modo de vibración.
     */
    private fun configureVibrationAndSoundButtons(
        buttonVibrate: ImageView, buttonSound: ImageView, soundMode: Boolean, vibrationMode: Boolean
    ) {
        // Configura el listener del botón de vibración para manejar cambios en el modo de vibración
        buttonVibrate.setOnClickListener {
            handleVibrationModeChange(buttonVibrate, vibrationMode)
        }

        // Configura el listener del botón de sonido para manejar cambios en el modo de sonido
        buttonSound.setOnClickListener {
            handleSoundModeChange(buttonSound, soundMode)
        }
    }

    /**
     * Maneja el cambio de modo de vibración y actualiza la interfaz de usuario en consecuencia.
     * @param buttonVibrate ImageView que representa el modo de vibración.
     * @param vibrationMode Estado actual del modo de vibración.
     */
    private fun handleVibrationModeChange(buttonVibrate: ImageView, vibrationMode: Boolean) {
        // Cambia la representación de la imagen del botón y actualiza el modo de vibración en las preferencias
        if (vibrationMode) {
            buttonVibrate.setImageResource(R.drawable.vibration_off)
            with(sharedPref.edit()) {
                putBoolean(VIBRATION_MODE, false)
                apply()
            }
        } else {
            buttonVibrate.setImageResource(R.drawable.vibration_on)
            with(sharedPref.edit()) {
                putBoolean(VIBRATION_MODE, true)
                apply()
            }
        }
    }

    /**
     * Maneja el cambio de modo de sonido y actualiza la interfaz de usuario en consecuencia.
     * @param buttonSound ImageView que representa el modo de sonido.
     * @param soundMode Estado actual del modo de sonido.
     */
    private fun handleSoundModeChange(buttonSound: ImageView, soundMode: Boolean) {
        // Cambia la representación de la imagen del botón y actualiza el modo de sonido en las preferencias
        if (soundMode) {
            buttonSound.setImageResource(R.drawable.sound_off)
            with(sharedPref.edit()) {
                putBoolean(SOUND_MODE, false)
                apply()
            }
            mediaPlayer.pause()
        } else {
            buttonSound.setImageResource(R.drawable.sound_on)
            with(sharedPref.edit()) {
                putBoolean(SOUND_MODE, true)
                apply()
            }
            mediaPlayer.start()
        }
    }

    /**
     * Configura las acciones de los botones dentro del diálogo de ajustes.
     * @param alertDialog El AlertDialog que contiene los botones y acciones a configurar.
     * @param buttonGuardar Botón para guardar la partida actual.
     * @param buttonReiniciar Botón para reiniciar la actividad principal.
     * @param buttonCargarPartida Botón para cargar partidas guardadas.
     */
    private fun configureSettingsDialogActions(alertDialog: AlertDialog, buttonGuardar: Button?, buttonReiniciar: Button?, buttonCargarPartida: Button?) {
        buttonGuardar?.setOnClickListener {
            // Obtener el fragmento del tablero actual para guardar la partida
            tableroFragment =
                supportFragmentManager.findFragmentById(R.id.fragmentTablero) as TableroFragment?
            val datosGuardar = tableroFragment?.guardarPartida()
            dbHelper = DBHelper(this)

            // Obtener fecha y hora del sistema
            if (datosGuardar != null) {
                val nombrePartida = "Partida_${obtenerFechaYHora()}"
                dbHelper?.addPartida(nombrePartida, datosGuardar)
            }
            alertDialog.dismiss()
        }

        buttonReiniciar?.setOnClickListener {
            // Reiniciar la actividad principal al hacer clic en el botón 'Reiniciar'
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        buttonCargarPartida?.setOnClickListener {
            // Cargar partidas guardadas al hacer clic en el botón 'Cargar Partida'
            val dbHelper = DBHelper(this)
            val partidas = dbHelper.getAllPartidas()
            partidas.forEach { partida ->
                Log.d("Consult", "Partida: $partida")
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        // Desregistrar el receptor al destruir la actividad para evitar memory leaks
        connectivityReceiver?.let {
            unregisterReceiver(it)
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause()
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    /**
     * Obtiene la fecha y hora actual en un formato específico y lo devuelve como una cadena de texto.
     * @return Cadena de texto que representa la fecha y hora actuales en el formato "yyyyMMdd_HHmmss".
     */
    private fun obtenerFechaYHora(): String {
        // Obtener una instancia del calendario con la fecha y hora actuales
        val cal = Calendar.getInstance()

        // Crear un formato de fecha específico ("yyyyMMdd_HHmmss") usando el idioma por defecto del dispositivo
        val dateFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())

        // Formatear la fecha y hora actuales y devolverla como una cadena de texto
        return dateFormat.format(cal.time)
    }

    /**
     * Hace vibrar el dispositivo por una duración determinada, usando los servicios de vibración disponibles.
     * @param context El contexto de la aplicación.
     */
    fun vibrar(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager

            // Crea un efecto de vibración de un solo disparo con una duración de 2000 milisegundos
            val vibrationEffect = VibrationEffect.createWaveform(longArrayOf(0, 1500), -1)

            vibratorManager.vibrate(CombinedVibration.createParallel(vibrationEffect))
        } else {
            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

            // Vibración para versiones anteriores a Android 12
            vibrator.vibrate(VibrationEffect.createOneShot(1500, VibrationEffect.DEFAULT_AMPLITUDE))
        }
    }


    /**
     * Inicializa y reemplaza los fragmentos dentro de la actividad principal.
     * Crea e inicializa un TableroFragment y un JuegoFragment dentro de la actividad.
     */
    private fun initializeFragments() {
        // Reemplaza el fragmento en el contenedor 'fragmentTablero' con un nuevo TableroFragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentTablero, TableroFragment())
            .commit()

        // Reemplaza el fragmento en el contenedor 'fragmentJuego' con un nuevo JuegoFragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentJuego, JuegoFragment())
            .commit()
    }

    /**
     * Inicializa y registra un receptor de conectividad dentro de la actividad.
     * Crea una instancia de ConnectivityReceiver y registra el receptor para escuchar cambios de conectividad.
     */
    private fun initializeConnectivityReceiver() {
        // Crea una instancia del receptor de conectividad con el contexto actual
        connectivityReceiver = ConnectivityReceiver(this)

        // Crea un filtro de intenciones para capturar cambios en la conectividad
        val intentFilter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")

        // Registra el receptor para escuchar cambios de conectividad utilizando el filtro de intenciones
        registerReceiver(connectivityReceiver, intentFilter)
    }
}