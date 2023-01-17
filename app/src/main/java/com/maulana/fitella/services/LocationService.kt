package com.maulana.fitella.services

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.location.Location
import android.os.*
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.location.*
import com.maulana.fitella.ui.record_run.RecordRunActivity
import com.maulana.fitella.utils.RunStatus
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import java.text.DecimalFormat


class LocationService : Service() {
    inner class MyBinder : Binder() {
        fun getService(): LocationService = this@LocationService
    }

    private val binder = MyBinder()

    private val TAG = "LocationService"

    private var notificationManager: NotificationManager? = null

    companion object {
        val ACTION_START_FOREGROUND_SERVICE = "ACTION_START_FOREGROUND_SERVICE"
        val ACTION_STOP_FOREGROUND_SERVICE = "ACTION_STOP_FOREGROUND_SERVICE"
        val ACTION_START_RECORD = "ACTION_START_RECORD"
        val ACTION_STOP_RECORD = "ACTION_STOP_RECORD"
        val ACTION_RESUME_RECORD = "ACTION_RESUME_RECORD"
    }

    private val CHANNEL_ID = "my_channel_id"
    private val CHANNEL_NAME: CharSequence = "MY Channel"

    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest
    private lateinit var lastLocation: Location

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var marker: Marker? = null
    var pathTracker: Polyline? = null
    var runStatus: RunStatus? = RunStatus.INIT

    private var timerInSeconds: Int = 0
    private lateinit var handler: Handler
    private val df = DecimalFormat("#.##")
    var listSpeed: ArrayList<Float> = arrayListOf()
    private var intentGpsData: Intent = Intent("GPSLocationUpdates")
    private var intentTimerData: Intent = Intent("TimerUpdates")

    var startPoint: GeoPoint = GeoPoint(46.55951, 15.63970);

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
                    ACTION_START_RECORD -> startTimer()
                    ACTION_STOP_RECORD -> pauseTimer()
                    ACTION_RESUME_RECORD -> resumeTimer()
                }
            }
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
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
    }


    private fun updateLocation(newLocation: Location) {
        if (runStatus == RunStatus.RECORD_START || runStatus == RunStatus.RECORD_INIT) {
            if (newLocation.latitude != startPoint.latitude || newLocation.longitude != startPoint.longitude) {
                startPoint.latitude = newLocation.latitude
                startPoint.longitude = newLocation.longitude

                getPath().addPoint(startPoint)
                listSpeed.add(newLocation.speed)

            }
        }

        intentGpsData.putExtra("LOCATION", newLocation)
        sendBroadcast(intentGpsData);
    }

    private fun startTimer() {
        handler = Handler(Looper.getMainLooper())

        handler.post(
            object : Runnable {
                override fun run() {
                    val hrs: Int = timerInSeconds / 3600
                    val mins: Int = timerInSeconds % 3600 / 60
                    val secs: Int = timerInSeconds % 60

                    intentTimerData.putExtra("TIMER", String.format("%02d:%02d:%02d", hrs, mins, secs))
                    sendBroadcast(intentTimerData);

                    timerInSeconds++

                    handler.postDelayed(this, 1000)
                }
            }
        )

        runStatus = if (runStatus == RunStatus.INIT) {
            RunStatus.RECORD_INIT
        } else {
            RunStatus.RECORD_START
        }
    }

    private fun pauseTimer() {
        handler.removeCallbacksAndMessages(null)
        runStatus = RunStatus.RECORD_STOP
    }

    private fun resumeTimer() {
        startTimer()
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
        notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (notificationManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val notificationChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
                notificationChannel.enableLights(true)
                notificationChannel.lightColor = Color.BLUE
                notificationChannel.setSound(null, null)
                notificationChannel.setShowBadge(false)
                notificationManager?.createNotificationChannel(notificationChannel)
            }
        }
    }

    fun getPath(): Polyline {
        if (pathTracker == null) {
            pathTracker = Polyline()
            pathTracker!!.outlinePaint.color = Color.RED
            pathTracker!!.outlinePaint.strokeWidth = 10f
            pathTracker!!.addPoint(startPoint.clone())
        }
        return pathTracker!!
    }

//    private fun getPositionMarker(): Marker {
//        if (marker == null) {
//            marker = Marker(map)
//            marker!!.title = "Here I am"
//            marker!!.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
//            marker!!.icon =
//                ContextCompat.getDrawable(
//                    applicationContext,
//                    com.maulana.fitella.R.drawable.gps
//                );
//            map.overlays.add(marker)
//        }
//        return marker!!
//    }
}