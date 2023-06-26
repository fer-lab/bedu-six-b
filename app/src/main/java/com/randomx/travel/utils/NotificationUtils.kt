package com.randomx.travel.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.randomx.travel.R
import com.randomx.travel.RandomApp
import com.randomx.travel.activity.MainActivity
import com.randomx.travel.activity.category.CategoryProductActivity
import com.randomx.travel.activity.destination.DestinationProductActivity
import com.randomx.travel.model.ProductCallerModel


class NotificationUtils {

    companion object
    {
        const val ACTION_RECEIVED: String = "com.randomx.travel.ACTION_RECEIVED"
        const val ID_WISHLIST: Int = 1001


        @SuppressLint("MissingPermission")
        fun wishlistAdded(activity: Activity, productCaller: ProductCallerModel, productId: String) {

            val intentActivity: Class<*>? = if (productCaller.isCategory()) {
                CategoryProductActivity::class.java
            } else if (productCaller.isDestination()) {
                DestinationProductActivity::class.java
            } else {

                null
            }

            if (intentActivity !== null && productId.isNotEmpty())
            {
                val intent = Intent(activity, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    putExtra("${productCaller.callerType}_id", productCaller.callerId)
                    putExtra("product_id", productId)
                }

                val pendingIntent = PendingIntent.getActivity(activity, 0, intent, PendingIntent.FLAG_IMMUTABLE)

                with(activity) {
                    val builder = NotificationCompat.Builder(this, RandomApp.CHANNEL_ID)
                        .setSmallIcon(R.drawable.logo)
                        .setColor(getColor(R.color.colorPrimary))
                        .setContentTitle(getString(R.string.wishlist_notification_title))
                        .setContentText(getString(R.string.wishlist_notification_body))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)

                    NotificationManagerCompat.from(this).run {
                        notify(ID_WISHLIST, builder.build())
                    }

                }
            }



        }


    }


}