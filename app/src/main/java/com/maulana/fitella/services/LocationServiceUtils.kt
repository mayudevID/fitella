package com.maulana.fitella.services

import android.content.Context
import android.content.Intent
import android.os.Build

class LocationServiceUtils(private val context: Context) {
    private lateinit var action: String

    fun startLocService() {
        val intentLoc = Intent(context, LocationService::class.java).apply {
            action = LocationService.ACTION_START_FOREGROUND_SERVICE
        }
        triggerForeground(intentLoc)
    }

    fun stopLocService() {
        val intentLoc = Intent(context, LocationService::class.java).apply {
            action = LocationService.ACTION_STOP_FOREGROUND_SERVICE
        }
        triggerForeground(intentLoc)
    }

    fun startRecord() {
        val intentLoc = Intent(context, LocationService::class.java).apply {
            action = LocationService.ACTION_START_RECORD
        }
        triggerForeground(intentLoc)
    }

    fun pauseRecord() {
        val intentLoc = Intent(context, LocationService::class.java).apply {
            action = LocationService.ACTION_STOP_RECORD
        }
        triggerForeground(intentLoc)
    }

    fun resumeRecord() {
        val intentLoc = Intent(context, LocationService::class.java).apply {
            action = LocationService.ACTION_RESUME_RECORD
        }
        triggerForeground(intentLoc)
    }

    private fun triggerForeground(intentLoc: Intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentLoc)
        } else {
            context.startService(intentLoc)
        }
    }
}