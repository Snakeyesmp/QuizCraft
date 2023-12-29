package com.example.proyectofinaltrivial

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Si hay conexión a Internet, reemplazar el fragmento del tablero en el contenedor correspondiente
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentTablero, TableroFragment())
            .commit()

        // Reemplazar el fragmento del juego en el contenedor correspondiente
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentJuego, JuegoFragment())
            .commit()

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
            messageTextView.text = "No se ha detectado conexión a Internet. Por favor, revisa tu conexión."

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

        // If we check only for "NET_CAPABILITY_INTERNET", we get "true" if we are connected to a wifi
        // which has no access to the internet. "NET_CAPABILITY_VALIDATED" also verifies that we
        // are online
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }


}
