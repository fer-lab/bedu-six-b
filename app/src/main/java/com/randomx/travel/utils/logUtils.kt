package com.randomx.travel.utils

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.randomx.travel.RandomApp

object logUtils {

    private fun FirebaseCrashlytics.setCustomKeys(params: Map<String, Any>) {
        params.forEach { (key, value) ->
            when (value) {
                is String -> setCustomKey(key, value)
                is Boolean -> setCustomKey(key, value.toString())
                is Float -> setCustomKey(key, value.toDouble())
                is Int -> setCustomKey(key, value.toLong())
                is Long -> setCustomKey(key, value)
                is Double -> setCustomKey(key, value)
            }
        }
    }

    fun logError(e: Throwable, params: Map<String, Any>? = null) {

        params?.let {
            FirebaseCrashlytics.getInstance().setCustomKeys(it)
        }

        FirebaseCrashlytics.getInstance().recordException(e)
    }

    fun logEvent(context: Context, eventName: String, params: Map<String, Any>) {
        val analytics = FirebaseAnalytics.getInstance(context)
        val bundle = Bundle().apply {
            for ((key, value) in params) {
                putString(key, value.toString())
            }
        }
        analytics.logEvent(eventName, bundle)
    }

    fun logEvent(eventName: String, params: Map<String, Any>) {
        logEvent(RandomApp.getInstance().applicationContext, eventName, params)
    }

}