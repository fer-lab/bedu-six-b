package com.randomx.travel.utils


import android.content.Context
import com.google.gson.Gson
import com.randomx.travel.model.UserModel
import kotlinx.coroutines.runBlocking

object UserSessionUtils {


    private const val KEY_USER = "user"
    private lateinit var dataStoreManager: DataStoreManagerUtils
    private val gson = Gson()
    private var user: UserModel? = null

    fun init(context: Context) {
        dataStoreManager = DataStoreManagerUtils.getInstance(context)
    }
    fun setUser(user: UserModel) {
        this.user = user
        runBlocking { dataStoreManager.setString(KEY_USER, gson.toJson(user)) }
    }

    fun getUser(): UserModel {

        return if (user !== null && !user!!.email.isNullOrEmpty()) {
            user as UserModel
        } else {
            val userJson = runBlocking { dataStoreManager.getString(KEY_USER, "") }
            user = try {
                gson.fromJson(userJson, UserModel::class.java)
            } catch (e: Exception) {
                UserModel("", "", "", "", "")
            }

            if (user == null) {
                user = UserModel("", "", "", "", "")
            }
            user as UserModel
        }

    }

    fun clearUser() {
        runBlocking { dataStoreManager.removeString(KEY_USER) }
        user = UserModel("", "", "", "", "")
    }

    fun isUserLogged(): Boolean {
        return !getUser().email.isNullOrEmpty()
    }

    fun isUserNotLogged(): Boolean {
        return !isUserLogged()
    }

}