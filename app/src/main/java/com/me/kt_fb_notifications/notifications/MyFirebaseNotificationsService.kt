package com.me.kt_fb_notifications.notifications

import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.me.kt_fb_notifications.R
import com.me.kt_fb_notifications.util.Const.Companion.CHANNEL_ID
import com.me.kt_fb_notifications.util.Const.Companion.CHANNEL_NAME
import com.me.kt_fb_notifications.util.Const.Companion.NOTIFICATION_ID
import com.me.kt_fb_notifications.util.Const.Companion.TAG

class MyFirebaseNotificationsService: FirebaseMessagingService() {

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        Log.d(TAG, "MESSAGE RECEIVED!!");
        remoteMessage.notification?.let{
            Log.i(TAG, "RECEIVED TITLE: ${it.title} \n \"RECEIVED TEXT: ${it.body}")
        }


        remoteMessage.notification?.let {
            val title = it.title
            val text = it.body
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                val channel = NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
                )

                val builder = Notification.Builder(this, CHANNEL_ID)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setSmallIcon(R.drawable.ic_sports)
                    .setAutoCancel(true)

                val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)

                with(NotificationManagerCompat.from(this)) {
                    notify(NOTIFICATION_ID, builder.build())
                }
            }
        }
        //super.onMessageReceived(remoteMessage)
    }
}