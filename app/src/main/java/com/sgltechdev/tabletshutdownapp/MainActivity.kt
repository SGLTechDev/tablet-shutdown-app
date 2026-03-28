package com.sgltechdev.tabletshutdownapp

import android.app.ActivityManager
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editTextTime = findViewById<EditText>(R.id.editTextTime)
        val buttonShutdown = findViewById<Button>(R.id.buttonShutdown)

        buttonShutdown.setOnClickListener {
            val timeInput = editTextTime.text.toString()
            if (timeInput.isNotEmpty()) {
                try {
                    val minutes = timeInput.toLong()
                    scheduleShutdown(minutes)
                } catch (e: NumberFormatException) {
                    Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Time cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun scheduleShutdown(minutes: Long) {
        val delayMillis = TimeUnit.MINUTES.toMillis(minutes)
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, ShutdownReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + delayMillis, pendingIntent)
        Toast.makeText(this, "Shutdown scheduled in $minutes minutes", Toast.LENGTH_SHORT).show()
    }
}
