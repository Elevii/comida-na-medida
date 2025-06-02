package com.elevii.comidanamedida

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.elevii.comidanamedida.ui.settings.SettingsFragment.Companion.KEY_THEME_MODE
import com.elevii.comidanamedida.ui.settings.SettingsFragment.Companion.PREFS_NAME
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val savedMode = prefs.getInt(KEY_THEME_MODE, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        AppCompatDelegate.setDefaultNightMode(savedMode)
    }
}

// https://preview--raw-to-cooked-convert.lovable.app/
