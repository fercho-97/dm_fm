package com.example.dispositivosmoviles.ui.utilities

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.ui.activities.CameraActivity

class BroadcasterNotifications: BroadcastReceiver() {

    val CHANNEL: String = "Cotificacion"

    override fun onReceive(context: Context, intent: Intent) {

        val intent = Intent(context, CameraActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val CHANNEL: String = "Cotificacion"
        val noti= NotificationCompat.Builder(context, CHANNEL)
        noti.setContentTitle("Alarma")
        noti.setContentText("MI primera notificaciones en android")
        noti.setSmallIcon(R.drawable.home_48)
        noti.setPriority(NotificationCompat.PRIORITY_DEFAULT)
        noti.setStyle(
            NotificationCompat
                .BigTextStyle()
                .bigText("Esta es una notificacion para recordar que esta es mi primera implementacipn de notificacion"))


        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        noti.setContentIntent(pendingIntent)
        notificationManager.notify(
            1, noti.build()

        )

    }




}