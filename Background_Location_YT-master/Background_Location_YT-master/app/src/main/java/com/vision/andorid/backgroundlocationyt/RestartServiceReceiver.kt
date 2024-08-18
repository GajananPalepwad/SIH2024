package com.vision.andorid.backgroundlocationyt

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class RestartServiceReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action.equals(Intent.ACTION_BOOT_COMPLETED) ||
            intent?.action.equals(Intent.ACTION_MY_PACKAGE_REPLACED)) {
            val restartServiceIntent = Intent(context, LocationService::class.java)
            context.startService(restartServiceIntent)
        }
    }
}
