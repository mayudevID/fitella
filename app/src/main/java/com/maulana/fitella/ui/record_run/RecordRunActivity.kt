package com.maulana.fitella.ui.record_run

import android.Manifest
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.content.IntentSender
import android.graphics.Color
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.maulana.fitella.databinding.ActivityRecordRunBinding
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration.*
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay
import java.util.*


class RecordRunActivity : AppCompatActivity() {
    private val TAG: String = "RecordRunActivity"
    private val REQUEST_PERMISSIONS_REQUEST_CODE = 1
    private lateinit var map: MapView

    private var activityResultLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var lastLocation: Location? = null
    private var locationCallback: LocationCallback
    private var locationRequest: LocationRequest
    private var requestingLocationUpdates = false

    private lateinit var binding: ActivityRecordRunBinding
    private lateinit var constraintSet: ConstraintSet
    private var startPoint: GeoPoint = GeoPoint(46.55951, 15.63970);
    private lateinit var mapController: IMapController
    private var marker: Marker? = null
    private var pathTracker: Polyline? = null

    private lateinit var vaOrange: ValueAnimator
    private lateinit var vaWhite: ValueAnimator
    private lateinit var vaMap: ValueAnimator

    private var timerInSeconds: Int = 0
    private lateinit var handler: Handler

    companion object {
        const val REQUEST_CHECK_SETTINGS = 20202
    }

    init {
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

        this.activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { result ->
            var allAreGranted = true
            for (b in result.values) {
                allAreGranted = allAreGranted && b
            }

            Log.d(TAG, "Permissions granted $allAreGranted")
            if (allAreGranted) {
                initCheckLocationSettings()
                //initMap()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        val br: BroadcastReceiver = LocationProviderChangedReceiver()
        val filter = IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
        registerReceiver(br, filter)

        getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))

        binding = ActivityRecordRunBinding.inflate(layoutInflater)

        constraintSet = ConstraintSet()
        constraintSet.clone(binding.root)
        setAnimation()

        map = binding.map
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setMultiTouchControls(true)
        mapController = map.controller

        setContentView(binding.root)

        val appPerms = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET
        )
        activityResultLauncher.launch(appPerms)

        val rotationGestureOverlay = RotationGestureOverlay(map)
        rotationGestureOverlay.isEnabled
        map.overlays.add(rotationGestureOverlay)

        val mCompassOverlay =
            CompassOverlay(this, InternalCompassOrientationProvider(this), map)
        mCompassOverlay.enableCompass()
        map.overlays.add(mCompassOverlay)

        initListener()
    }

    override fun onResume() {
        super.onResume()
        binding.map.onResume()
    }

    override fun onPause() {
        super.onPause()
        if (requestingLocationUpdates) {
            requestingLocationUpdates = false
            stopLocationUpdates()
        }
        binding.map.onPause()
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "Settings onActivityResult for $requestCode result $resultCode")
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == RESULT_OK) {
                initMap()
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMsg(status: MyEventLocationSettingsChange) {
        if (status.on) {
            initMap()
        } else {
            Log.i(TAG, "Stop something")
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val permissionsToRequest = ArrayList<String>()
        var i = 0
        while (i < grantResults.size) {
            permissionsToRequest.add(permissions[i])
            i++
        }
        if (permissionsToRequest.size > 0) {
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toTypedArray(),
                REQUEST_PERMISSIONS_REQUEST_CODE
            )
        }
    }

    // =============================================================================================

    private fun initListener() {
        binding.buttonStart.setOnClickListener {
            expandUp()
        }

        binding.buttonStop.setOnClickListener {
            pauseTimer()
        }

        binding.buttonResume.setOnClickListener {
            startTimer()
            binding.recordPauseLayout.visibility = View.GONE
            binding.buttonStop.visibility = View.VISIBLE
        }

        binding.fabGps.setOnClickListener {
            mapController.animateTo(startPoint)
        }

        binding.buttonClose.setOnClickListener {
            finish()
        }
    }

    private fun setAnimation() {
        vaOrange = ValueAnimator.ofFloat(0.165f, 0.41133004f)
        vaOrange.duration = 300
        vaOrange.addUpdateListener { animation ->
            constraintSet.constrainPercentHeight(
                binding.orangeCardView.id,
                animation.animatedValue as Float
            )
            constraintSet.applyTo(binding.root)
        }

        vaWhite = ValueAnimator.ofFloat(0.145f, 0.39901477f)
        vaWhite.duration = 300
        vaWhite.addUpdateListener { animation ->
            constraintSet.constrainPercentHeight(
                binding.whiteCardView.id,
                animation.animatedValue as Float
            )
            constraintSet.applyTo(binding.root)
        }

        vaMap = ValueAnimator.ofFloat(0.95f, 0.7f)
        vaMap.duration = 300
        vaMap.addUpdateListener { animation ->
            constraintSet.constrainPercentHeight(binding.map.id, animation.animatedValue as Float)
            constraintSet.applyTo(binding.root)
        }
    }

    private fun expandUp() {
        vaOrange.start()
        vaWhite.start()
        vaMap.start()
        mapController.animateTo(startPoint)

        Handler(Looper.myLooper()!!).postDelayed(
            {
                binding.initLayout.visibility = View.GONE
                binding.recordLayout.visibility = View.VISIBLE
                startTimer()
            },
            300
        )
    }

    private fun expandDown() {
        vaWhite.reverse()
        vaOrange.reverse()
    }

    private fun startTimer() {
        handler = Handler(Looper.getMainLooper())

        handler.post(
            object : Runnable {
                override fun run() {
                    val hrs: Int = timerInSeconds / 3600
                    val mins: Int = timerInSeconds % 3600 / 60
                    val secs: Int = timerInSeconds % 60

                    Log.d(TAG, String.format("%02d:%02d:%02d", hrs, mins, secs))

                    binding.timerText.text =
                        String.format("%02d:%02d:%02d", hrs, mins, secs);

                    timerInSeconds++

                    handler.postDelayed(this, 1000)
                }
            }
        )
    }

    private fun pauseTimer() {
        handler.removeCallbacksAndMessages(null)
        binding.buttonStop.visibility = View.GONE
        binding.recordPauseLayout.visibility = View.VISIBLE
    }


    private fun initLocation() { //call in create
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        readLastKnownLocation()
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() { //onResume
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun stopLocationUpdates() { //onPause
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    @SuppressLint("MissingPermission")
    private fun readLastKnownLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let { updateLocation(it) }
            }
    }

    private fun initCheckLocationSettings() {
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        val client: SettingsClient = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())
        task.addOnSuccessListener {
            Log.d(TAG, "Settings Location IS OK")
            MyEventLocationSettingsChange.globalState = true
            initMap()
        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                Log.d(TAG, "Settings Location addOnFailureListener call settings")
                try {
                    exception.startResolutionForResult(
                        this@RecordRunActivity,
                        REQUEST_CHECK_SETTINGS
                    )
                } catch (sendEx: IntentSender.SendIntentException) {
                    Log.d(TAG, "Settings Location sendEx??")
                }
            }
        }

    }

    fun updateLocation(newLocation: Location) {
        lastLocation = newLocation
        var currentPoint: GeoPoint = GeoPoint(newLocation.latitude, newLocation.longitude);
        startPoint.longitude = newLocation.longitude
        startPoint.latitude = newLocation.latitude
        getPositionMarker().position = startPoint
        mapController.animateTo(startPoint)
        map.invalidate()
    }

    private fun initMap() {
        initLocation()
        if (!requestingLocationUpdates) {
            requestingLocationUpdates = true
            startLocationUpdates()
        }
        mapController.setZoom(18.5)
        mapController.setCenter(startPoint)
        map.invalidate()
    }

    private fun getPositionMarker(): Marker {
        if (marker == null) {
            marker = Marker(map)
            marker!!.title = "Here I am"
            marker!!.icon =
                ContextCompat.getDrawable(this, com.maulana.fitella.R.drawable.location_point);
            map.overlays.add(marker)
        }
        return marker!!
    }

    private fun getPath(): Polyline {
        if (pathTracker == null) {
            pathTracker = Polyline()
            pathTracker!!.outlinePaint.color = Color.RED
            pathTracker!!.outlinePaint.strokeWidth = 10f
            pathTracker!!.addPoint(startPoint.clone())
            map.overlayManager.add(pathTracker)
        }
        return pathTracker!!
    }
}