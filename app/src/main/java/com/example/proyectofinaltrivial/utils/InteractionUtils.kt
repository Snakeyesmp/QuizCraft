package com.example.proyectofinaltrivial.utils

import android.view.Window
import android.view.WindowManager

object InteractionUtils {
    /**
     * Habilita las interacciones del usuario en la ventana especificada.
     * @param window La ventana en la que se habilitarán las interacciones del usuario.
     */
    fun enableUserInteractions(window: Window) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    /**
     * Deshabilita las interacciones del usuario en la ventana especificada.
     * @param window La ventana en la que se deshabilitarán las interacciones del usuario.
     */
    fun disableUserInteractions(window: Window) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }
}
