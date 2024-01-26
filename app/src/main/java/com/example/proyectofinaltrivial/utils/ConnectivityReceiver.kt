package com.example.proyectofinaltrivial.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.proyectofinaltrivial.main_package.MainActivity

class ConnectivityReceiver(private val activity: MainActivity) : BroadcastReceiver() {



    override fun onReceive(context: Context?, intent: Intent?) {
        if (InternetUtils.isInternetAvailable(activity)) {
            InternetUtils.dismissDialog()
            InteractionUtils.enableUserInteractions(window = activity.window)
        } else {
            InternetUtils.showNoInternetDialog(activity)
            InteractionUtils.disableUserInteractions(window = activity.window)
        }
    }
}
