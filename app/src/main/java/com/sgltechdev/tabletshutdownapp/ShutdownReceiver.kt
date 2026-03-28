package com.sgltechdev.tabletshutdownapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import android.widget.Toast

class ShutdownReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        try {
            val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
            powerManager.goToSleep(System.currentTimeMillis())
        } catch (e: SecurityException) {
            Toast.makeText(context, "Permission denied to shutdown device", Toast.LENGTH_SHORT).show()
        }
    }
}
