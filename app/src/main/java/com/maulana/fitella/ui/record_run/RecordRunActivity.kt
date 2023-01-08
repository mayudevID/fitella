package com.maulana.fitella.ui.record_run

import android.Manifest
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.IntentSender
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.os.Looper
import androidx.preference.PreferenceManager
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.maulana.fitella.databinding.ActivityRecordRunBinding
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration.*
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
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
    var startPoint: GeoPoint = GeoPoint(46.55951, 15.63970);
    lateinit var mapController: IMapController
    var marker: Marker? = null
    var pathTracker: Polyline? = null

    private lateinit var vaOrange: ValueAnimator
    private lateinit var vaWhite: ValueAnimator

    companion object {
        const val REQUEST_CHECK_SETTINGS = 20202
    }

    init {
        locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
            .setMinUpdateIntervalMillis(500)
            .setMinUpdateDistanceMeters(10f)
            .setMaxUpdates(1000)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

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
        binding.whiteCardView.setOnClickListener {
            expandUp()
        }
    }

    private fun setAnimation() {
        vaOrange = ValueAnimator.ofFloat(0.165f, 0.41133004f)
        vaOrange.duration = 300
        vaOrange.addUpdateListener { animation ->
            constraintSet.constrainPercentHeight(binding.orangeCardView.id, animation.animatedValue as Float)
            constraintSet.applyTo(binding.root)
        }

        vaWhite = ValueAnimator.ofFloat(0.145f, 0.39901477f)
        vaWhite.duration = 300
        vaWhite.addUpdateListener { animation ->
            constraintSet.constrainPercentHeight(binding.whiteCardView.id, animation.animatedValue as Float)
            constraintSet.applyTo(binding.root)
        }
    }

    private fun expandUp() {
        vaOrange.start()
        vaWhite.start()
    }

    private fun expandDown() {
        vaWhite.reverse()
        vaOrange.reverse()
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
        mapController.setCenter(startPoint)
        getPositionMarker().position = startPoint
        map.invalidate()
    }

    private fun initMap() {
        initLocation()
        if (!requestingLocationUpdates) {
            requestingLocationUpdates = true
            startLocationUpdates()
        }
        mapController.setZoom(18.5)
        mapController.setCenter(startPoint);
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