package com.example.proyectofinaltrivial.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.proyectofinaltrivial.R

object InternetUtils {
    private var alertDialog: AlertDialog? = null

    /**
     * Verifica si hay una conexión activa a Internet.
     * @param context El contexto de la aplicación.
     * @return true si hay conexión a Internet, de lo contrario, false.
     */
    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
                ?: return false

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }

    /**
     * Muestra un diálogo de alerta cuando no hay conexión a Internet.
     * @param context El contexto de la aplicación.
     */
    fun showNoInternetDialog(context: Context) {
        if (alertDialog?.isShowing == true) {
            // El diálogo ya está mostrándose, no es necesario crear uno nuevo
            return
        }

        val builder = AlertDialog.Builder(context, R.style.CustomAlertDialog)
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val dialogView = inflater.inflate(R.layout.custom_alert_dialog, null)
        builder.setView(dialogView)

        // Configurar elementos del AlertDialog personalizado
        val titleTextView = dialogView.findViewById<TextView>(R.id.alertTitle)
        val messageTextView = dialogView.findViewById<TextView>(R.id.alertMessage)
        titleTextView.text = "Sin conexión a Internet"
        messageTextView.text =
            "No se ha detectado conexión a Internet. Por favor, revisa tu conexión."

        builder.setCancelable(false)
        alertDialog = builder.create()
        alertDialog?.show()
    }

    /**
     * Desactiva el diálogo de alerta cuando se restablece la conexión.
     */
    fun dismissDialog() {
        Log.d("ConnectivityReceiver", "cerrar")
        alertDialog?.dismiss()
        alertDialog = null
    }
}