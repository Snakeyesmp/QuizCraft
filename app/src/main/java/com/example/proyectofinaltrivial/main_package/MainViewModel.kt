package com.example.proyectofinaltrivial.main_package

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import com.example.proyectofinaltrivial.main_package.MainActivity.Companion.SOUND_MODE

class MainViewModel(
    private val sharedPref: SharedPreferences,
    private val mediaPlayer: MediaPlayer,
    private val context: Context
) {
    fun onPause() {
        mediaPlayer.pause()
    }

    fun onResume() {
        val soundMode = sharedPref.getBoolean(SOUND_MODE, true)
        if (soundMode) {
            mediaPlayer.start()
        }
    }
}