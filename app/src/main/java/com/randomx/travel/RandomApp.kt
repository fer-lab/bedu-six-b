package com.randomx.travel

import android.app.Application
import com.randomx.travel.data.local.WishlistDb
import com.randomx.travel.data.local.WishlistRepository

class RandomApp : Application() {

    companion object {
        private lateinit var instance: RandomApp

        fun getInstance(): RandomApp {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    val wishlistRepository: WishlistRepository
        get() = WishlistRepository(
            WishlistDb.getInstance(this)!!.wishlistDao()
        )
}