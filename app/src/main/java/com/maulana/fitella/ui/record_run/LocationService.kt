package com.maulana.fitella.ui.record_run

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.location.Location
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.*


class LocationService : Service() {
    private val TAG = "LocationService"

   companion object {
       val ACTION_START_FOREGROUND_SERVICE = "ACTION_START_FOREGROUND_SERVICE"
       val ACTION_STOP_FOREGROUND_SERVICE = "ACTION_STOP_FOREGROUND_SERVICE"
   }

    private val CHANNEL_ID = "my_channel_id"
    private val CHANNEL_NAME: CharSequence = "MY Channel"

    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest
    private lateinit var lastLocation: Location

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "LocationTracker service created")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            val action = intent.action
            if (action != null) {
                when (action) {
                    ACTION_START_FOREGROUND_SERVICE -> startForegroundService()
                    ACTION_STOP_FOREGROUND_SERVICE -> stopForegroundService()
                }
            }
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    private fun startForegroundService() {
        Log.i(TAG, "Start foreground service")

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
            .setMinUpdateIntervalMillis(500)
            .setMinUpdateDistanceMeters(0.5f)
            .setMaxUpdates(750)
            .build()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    updateLocation(location)
                }
            }
        }

        readLastKnownLocation()

        /// initMAP

        try {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        } catch (unlikely: SecurityException) {
            Log.e(TAG, "Lost location permission. Could not request updates. $unlikely")
        }

        createNotificationChannel()

        val showTaskIntent = Intent(applicationContext, RecordRunActivity::class.java)
        showTaskIntent.action = Intent.ACTION_MAIN
        showTaskIntent.addCategory(Intent.CATEGORY_APP_MAPS)
        showTaskIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val contentIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            showTaskIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder: Notification.Builder = Notification.Builder(applicationContext)
            .setContentTitle("Fitella")
            .setContentText("Location Service is running")
            .setSmallIcon(com.maulana.fitella.R.drawable.baseline_gps_fixed_24)
            .setOnlyAlertOnce(true)
            .setWhen(System.currentTimeMillis())
            .setContentIntent(contentIntent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID)
        }

        startForeground(1, builder.build())
        //ServiceUtils.setRequestingLocationUpdates(applicationContext, true)
    }

    private fun stopForegroundService() {
        Log.i(TAG, "Stop Foreground Service")

        fusedLocationClient.removeLocationUpdates(locationCallback)
        stopForeground(true)
        stopSelf()
    }

    @SuppressLint("MissingPermission")
    private fun readLastKnownLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let { updateLocation(it) }
            }
    }

    private fun createNotificationChannel() {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (notificationManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val notificationChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
                notificationChannel.enableLights(true)
                notificationChannel.lightColor = Color.BLUE
                notificationChannel.setSound(null, null)
                notificationChannel.setShowBadge(false)
                notificationManager.createNotificationChannel(notificationChannel)
            }
        }
    }

    fun updateLocation(newLocation: Location) {
        lastLocation = newLocation
        Log.d(TAG, lastLocation.longitude.toString())
        //var currentPoint: GeoPoint = GeoPoint(newLocation.latitude, newLocation.longitude);
//        startPoint.longitude = newLocation.longitude
//        startPoint.latitude = newLocation.latitude
//        getPositionMarker().position = startPoint
//        mapController.animateTo(startPoint)
//
//        if (runStatus == RunStatus.RECORD_START) {
//            getPath().addPoint(startPoint.clone())
//            listSpeed.add(newLocation.speed)
//            _uiState.update { currentState ->
//                currentState.copy(
//                    averageText = df.format(listSpeed.average()),
//                    distanceText = df.format(getPath().distance / 1000)
//                )
//            }
//        }
//
//        map.invalidate()
    }
}