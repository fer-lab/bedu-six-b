package com.randomx.travel.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.randomx.travel.utils.NotificationUtils
import com.randomx.travel.utils.ToolsUtils

class NotificationReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent?.action == NotificationUtils.ACTION_RECEIVED && context != null)
        {
            ToolsUtils.toast(context, "Acción recibida")
        }

    }
}