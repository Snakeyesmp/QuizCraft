package com.example.proyectofinaltrivial

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private var connectivityReceiver: ConnectivityReceiver? = null
    private var tableroFragment: TableroFragment? = null
    private var dbHelper: DBHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val botonSettings = findViewById<ImageView>(R.id.btnSettings)


        botonSettings.setOnClickListener {
            val builder = AlertDialog.Builder(this, R.style.CustomAlertDialog)
            val inflater = layoutInflater
            val dialogView = inflater.inflate(R.layout.activity_settings, null)
            builder.setView(dialogView)

            val buttonReiniciar = dialogView.findViewById<Button>(R.id.btnReiniciar)
            val buttonGuardar = dialogView.findViewById<Button>(R.id.btnGuardar)
            val buttonCargarPartida = dialogView.findViewById<Button>(R.id.btnCargarPartida)
            // Configurar elementos del AlertDialog personalizado
            val titleTextView = dialogView.findViewById<TextView>(R.id.alertTitle)
            val messageTextView = dialogView.findViewById<TextView>(R.id.alertMessage)
            titleTextView.text = "Configuracion"
            messageTextView.text =
                "¿Qué desea realizar?"

            builder.setNegativeButton("Cerrar") { dialog, _ ->
                dialog.dismiss()
            }
            val alertDialog = builder.create()
            alertDialog.show()

            buttonGuardar.setOnClickListener {
                tableroFragment = supportFragmentManager.findFragmentById(R.id.fragmentTablero) as TableroFragment?
                val datosGuardar = tableroFragment?.guardarPartida()
                dbHelper = DBHelper(this)

                //Obtener fecha y hora sistema

                if (datosGuardar != null) {
                    val nombrePartida = "Partida_${obtenerFechaYHora()}"

                    dbHelper?.addPartida(nombrePartida, datosGuardar)
                }
                alertDialog.dismiss()

            }

            buttonReiniciar.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

            buttonCargarPartida.setOnClickListener {
                val dbHelper = DBHelper(this)
                val partidas = dbHelper.getAllPartidas()
                partidas.forEach { partida ->
                    Log.d("Consult", "Partida: ${partida}")
                }
            }

        }


        // Si hay conexión a Internet, reemplazar el fragmento del tablero en el contenedor correspondiente
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentTablero, TableroFragment())
            .commit()

        // Reemplazar el fragmento del juego en el contenedor correspondiente
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentJuego, JuegoFragment())
            .commit()

        connectivityReceiver = ConnectivityReceiver()
        val intentFilter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
        registerReceiver(connectivityReceiver, intentFilter)

        // Verificar la conexión a Internet
        if (!isInternetAvailable()) {
            showNoInternetDialog()
            disableUserInteractions()
        }
    }

    private fun disableUserInteractions() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    private fun showNoInternetDialog() {
        val builder = AlertDialog.Builder(this, R.style.CustomAlertDialog)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.custom_alert_dialog, null)
        builder.setView(dialogView)

        // Configurar elementos del AlertDialog personalizado
        val titleTextView = dialogView.findViewById<TextView>(R.id.alertTitle)
        val messageTextView = dialogView.findViewById<TextView>(R.id.alertMessage)
        titleTextView.text = "Sin conexión a Internet"
        messageTextView.text =
            "No se ha detectado conexión a Internet. Por favor, revisa tu conexión."

        builder.setPositiveButton("Aceptar") { dialog, _ ->
            dialog.dismiss()
        }
        builder.setCancelable(false)
        val alertDialog = builder.create()
        alertDialog.show()


    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager = this.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager?
            ?: return false

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false


        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }

    private fun enableUserInteractions() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    inner class ConnectivityReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (isInternetAvailable()) {
                enableUserInteractions()
            } else {
                showNoInternetDialog()
                disableUserInteractions()
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

    fun obtenerFechaYHora(): String {
        val cal = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
        return dateFormat.format(cal.time)
    }
}
