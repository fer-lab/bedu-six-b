package com.randomx.travel.utils

import android.content.Context
import android.content.SharedPreferences

object PreferenceUtils {
    private const val PREF_NAME = "AppPreferences"
    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveString(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    // Otros métodos para guardar/recuperar otros tipos de datos según sea necesario
}
