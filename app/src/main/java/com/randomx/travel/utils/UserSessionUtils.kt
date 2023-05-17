package com.randomx.travel.utils


import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.randomx.travel.model.UserModel

object UserSessionUtils {

    private const val PREF_NAME = ""
    private const val KEY_NAME = ""
    private const val KEY_LAST_NAME = ""
    private const val KEY_EMAIL = ""

    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun setUser(user: UserModel) {
        Log.d("UserSessionUtils", "setUser: $user")
        val editor = sharedPreferences.edit()
        editor.putString(KEY_NAME, user.name)
        editor.putString(KEY_LAST_NAME, user.lastName)
        editor.putString(KEY_EMAIL, user.email)
        // Guarda otros datos del usuario si es necesario
        editor.apply()
    }

    fun getUser(): UserModel {
        val name = sharedPreferences.getString(KEY_NAME, null)
        val lastName = sharedPreferences.getString(KEY_LAST_NAME, null)
        val email = sharedPreferences.getString(KEY_EMAIL, null)

        Log.d("UserSessionUtils", "getUser: $name $lastName $email")
        return UserModel(name, lastName, email, null, null)
    }

    fun clearUser() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

    }

    fun isUserLogged(): Boolean {
        return sharedPreferences.contains(KEY_EMAIL) && !sharedPreferences.getString(KEY_EMAIL, "").isNullOrEmpty()
    }

    fun isUserNotLogged(): Boolean {
        return !isUserLogged()
    }

}