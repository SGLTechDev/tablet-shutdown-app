package com.sgltechdev.tabletshutdownapp

import android.app.ActivityManager
import android.content.Context
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
        Thread.sleep(delayMillis)
        shutdownDevice()
    }

    private fun shutdownDevice() {
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        if (activityManager.isSystemReady) {
            try {
                val powerManager = getSystemService(Context.POWER_SERVICE) as android.os.PowerManager
                powerManager.goToSleep(System.currentTimeMillis())
            } catch (e: SecurityException) {
                Toast.makeText(this, "Permission denied to shutdown device", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
