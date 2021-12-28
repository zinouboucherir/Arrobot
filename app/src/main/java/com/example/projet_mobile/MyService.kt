package com.example.projet_mobile

import android.app.*
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.util.*

class MyService : Service() {
    companion object { /* définir les constantes */
        const val CHANNEL_ID = "message urgent"
    }

    override fun onCreate() {

    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val t = Thread {
            val dao = PlanteDB.getDatabase(application).myDao()
            lateinit var plantFreq: List<FullInfo>
            val now = Calendar.getInstance()
            plantFreq = dao.PlantFrequence(now.get(Calendar.MONTH) + 1).toList()
            plantFreq = plantFreq.filter { it.dateDernierArrNutr?.plusDays((it.Par / it.NbrFois).toLong()) == java.time.LocalDate.now() }
            if (plantFreq.isNotEmpty()) {
                val i = Intent(this, PlantArroseActivity::class.java)
                intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                val pendingIntent = PendingIntent.getActivity(this, 0, i, 0)
                val rington=RingtoneManager.getRingtone(applicationContext,RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE))
                rington.play()
                val notification = NotificationCompat.Builder(this, "channelId")
                        .setContentTitle("ArroBot Notification")
                        .setContentText("Vous avez des plante à arroser aujourduit, cliquer pour les consulter!!")
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setAutoCancel(true)
                        .setSmallIcon(R.drawable.logo)
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .build()
                startForeground(1, notification)
                createNotificationChannel()
            }
        }
        t.start()
        t.join()
        return super.onStartCommand(intent, flags, startId)
    }
    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = getString(R.string.channel_name)
            val description = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = description
            channel.setShowBadge(true)
            // récupérer NotificationManager et enregistrer le channel
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}