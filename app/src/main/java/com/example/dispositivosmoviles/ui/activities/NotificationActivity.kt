package com.example.dispositivosmoviles.ui.activities

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.ActivityNotificationBinding
import com.example.dispositivosmoviles.ui.utilities.BroadcasterNotifications
import java.util.Calendar
import java.util.Date

class NotificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNotificationBinding.inflate(layoutInflater)

        setContentView(binding.root)
        binding.btnNotification.setOnClickListener() {

            createNotification()
            sendNotificaction()
        }

        binding.btnNotificationProgramada.setOnClickListener {

            val calendar = Calendar.getInstance()
            val hora = binding.timePicker.hour
            val minutes = binding.timePicker.minute

            Toast.makeText(this,
                "La notificación se activará a las: $hora:$minutes",
            Toast.LENGTH_SHORT).show()

            calendar.set(Calendar.HOUR, hora)
            calendar.set(Calendar.MINUTE, minutes)
            calendar.set(Calendar.SECOND, 0)

            sendNotificationTimePicker(calendar.timeInMillis)


        }
    }

    private fun sendNotificationTimePicker(time: Long) {

        val myIntent = Intent(applicationContext, BroadcasterNotifications::class.java)
        val myPendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            0,
            myIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            time,
            myPendingIntent
        )




    }

    val CHANNEL: String = "Cotificacion"

    private fun createNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Variedades"
            val descriptionText = "Notificacion simples de variables"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    @SuppressLint("MissingPermission")
    fun sendNotificaction() {

        val intent = Intent(this, CameraActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )


        val noti = NotificationCompat.Builder(this, CHANNEL)
        noti.setContentTitle("Primera notificacion")
        noti.setContentText("MI primera notificaciones en android")
        noti.setSmallIcon(R.drawable.home_48)
        noti.setPriority(NotificationCompat.PRIORITY_DEFAULT)
        noti.setStyle(
            NotificationCompat
                .BigTextStyle()
                .bigText("Esta es una notificacion para recordar que esta es mi primera implementacipn de notificacion")
        )

        noti.setContentIntent(pendingIntent)

        with(NotificationManagerCompat.from(this)) {
            notify(1, noti.build())
        }

    }
}