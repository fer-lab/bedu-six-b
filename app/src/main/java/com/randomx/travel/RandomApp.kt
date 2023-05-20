package com.randomx.travel

import android.app.Application

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
}