package com.example.projet_mobile

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.example.projet_mobile.databinding.ActivityAlarmBinding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.coroutines.flow.SharingCommand
import java.util.*
import java.util.jar.Manifest

class AlarmActivity : AppCompatActivity() {
    var calendar=Calendar.getInstance() //la date et l'heure d'aujourd'hui
    private lateinit var alarmManager:AlarmManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.FOREGROUND_SERVICE)  ,PackageManager.PERMISSION_GRANTED)

        binding.timePicker.setOnTimeChangedListener { view, hourOfDay, minute ->
            calendar[Calendar.HOUR_OF_DAY]=hourOfDay
            calendar[Calendar.MINUTE]=minute
            calendar[Calendar.SECOND]=0
            calendar[Calendar.MILLISECOND]=0
        }

        binding.setAlarm.setOnClickListener {
            setAlarm()
        }
        binding.cancelAlarm.setOnClickListener {
            cancelAlarm()
        }
    }

    private fun cancelAlarm() {
        val intent=Intent(this,MyService::class.java)
        val pendingIntent = PendingIntent.getService(this,0, intent,PendingIntent.FLAG_IMMUTABLE)
        alarmManager=getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
        Toast.makeText(this, "alarm annulé avec succées ", Toast.LENGTH_SHORT).show()
    }

    private fun setAlarm() {
        //lancer le service MyService
        val intent=Intent(this,MyService::class.java)
        val pendingIntent = PendingIntent.getService(this,0, intent,PendingIntent.FLAG_IMMUTABLE)
        //programer une alarm qui se déclencehe chauqe jour à l'heure choisi par l'utilisateur
        alarmManager=getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.setInexactRepeating(
             AlarmManager.RTC_WAKEUP,calendar.timeInMillis,
             AlarmManager.INTERVAL_DAY,pendingIntent,
            )
        Toast.makeText(this, "alarm instalé avec succées ", Toast.LENGTH_SHORT).show()
    }
}