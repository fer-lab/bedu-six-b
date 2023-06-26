package com.randomx.travel

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.randomx.travel.data.local.WishlistDb
import com.randomx.travel.data.local.WishlistRepository

class RandomApp : Application() {

    companion object {
        private lateinit var instance: RandomApp
        const val CHANNEL_ID = "DEFAULT_CHANNEL"

        fun getInstance(): RandomApp {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        setNotificationChannel()
    }

    val wishlistRepository: WishlistRepository
        get() = WishlistRepository(
            WishlistDb.getInstance(this)!!.wishlistDao()
        )

    private fun setNotificationChannel(){
        val name = getString(R.string.channel_default_name)
        val descriptionText = getString(R.string.channel_default_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT

        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }
}