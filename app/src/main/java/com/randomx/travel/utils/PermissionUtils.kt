package com.randomx.travel.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat


object PermissionUtils
{
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun checkNotificationPermission(context: Context): Boolean{
        return ActivityCompat
            .checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestPermissions(activity: Activity) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
            1
        )
    }

    fun executeOrRequestPermission(activity: Activity, callback: () -> Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (!checkNotificationPermission(activity)) {
                requestPermissions(activity)
            } else{
                Log.d("PermissionUtils", "executeOrRequestPermission: has permission 36")
                callback()
            }
        } else {
            Log.d("PermissionUtils", "executeOrRequestPermission: has permission 40")
            callback()
        }
    }
}

