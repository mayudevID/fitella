package com.maulana.fitella.ui.record_run

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.IntentSender
import android.graphics.Color
import android.location.Location
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.maulana.fitella.databinding.ActivityRecordRunBinding
import com.maulana.fitella.utils.RunStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.osmdroid.api.IMapController
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import java.text.DecimalFormat

@SuppressLint("StaticFieldLeak")
class RecordRunViewModel : ViewModel() {

    data class RecordRunState(
        val averageText: String = "0",
        val distanceText: String = "0",
        val timerText: String = "00:00:00",
        val buttonStop: Int = View.VISIBLE,
        val recordPauseLayout: Int = View.GONE,
        val runStatus: RunStatus = RunStatus.INIT,
        val initLayout: Int = View.VISIBLE,
        val recordLayout: Int = View.GONE
    )

    private val _uiState = MutableStateFlow(RecordRunState())
    val uiState: StateFlow<RecordRunState> = _uiState.asStateFlow()
    private lateinit var activity: RecordRunActivity

    private val TAG: String = "RecordRunActivity"
    lateinit var map: MapView

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var lastLocation: Location? = null
    private var locationCallback: LocationCallback
    private var locationRequest: LocationRequest
    var requestingLocationUpdates = false

    lateinit var constraintSet: ConstraintSet
    var startPoint: GeoPoint = GeoPoint(46.55951, 15.63970);
    lateinit var mapController: IMapController
    private var marker: Marker? = null
    private var pathTracker: Polyline? = null
    var runStatus: RunStatus = RunStatus.INIT

    private lateinit var vaOrange: ValueAnimator
    private lateinit var vaWhite: ValueAnimator
    private lateinit var vaMap: ValueAnimator

    private var timerInSeconds: Int = 0
    private lateinit var handler: Handler
    private val df = DecimalFormat("#.##")
    private var listSpeed: ArrayList<Float> = arrayListOf()

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
    }

    fun setActivity(activity: RecordRunActivity) {
        this.activity = activity
    }

    private fun initLocation() { //call in create
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
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

    fun stopLocationUpdates() { //onPause
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    @SuppressLint("MissingPermission")
    private fun readLastKnownLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let { updateLocation(it) }
            }
    }

    fun initCheckLocationSettings() {
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        val client: SettingsClient = LocationServices.getSettingsClient(activity)
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
                        activity,
                        RecordRunActivity.REQUEST_CHECK_SETTINGS
                    )
                } catch (sendEx: IntentSender.SendIntentException) {
                    Log.d(TAG, "Settings Location sendEx??")
                }
            }
        }

    }

    fun updateLocation(newLocation: Location) {
        lastLocation = newLocation
        //var currentPoint: GeoPoint = GeoPoint(newLocation.latitude, newLocation.longitude);
        startPoint.longitude = newLocation.longitude
        startPoint.latitude = newLocation.latitude
        getPositionMarker().position = startPoint
        mapController.animateTo(startPoint)

        if (runStatus == RunStatus.RECORD_START) {
            getPath().addPoint(startPoint.clone())
            listSpeed.add(newLocation.speed)
            _uiState.update { currentState ->
                currentState.copy(
                    averageText = df.format(listSpeed.average()),
                    distanceText = df.format(getPath().distance / 1000)
                )
            }
        }

        map.invalidate()
    }

    fun initMap() {
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
            marker!!.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
            marker!!.icon =
                ContextCompat.getDrawable(
                    activity.applicationContext,
                    com.maulana.fitella.R.drawable.gps
                );
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

    fun startTimer() {
        handler = Handler(Looper.getMainLooper())

        handler.post(
            object : Runnable {
                override fun run() {
                    val hrs: Int = timerInSeconds / 3600
                    val mins: Int = timerInSeconds % 3600 / 60
                    val secs: Int = timerInSeconds % 60

                    //Log.d(TAG, String.format("%02d:%02d:%02d", hrs, mins, secs))

                    _uiState.update { currentState ->
                        currentState.copy(
                            timerText = String.format("%02d:%02d:%02d", hrs, mins, secs)
                        )
                    }

//                    binding.timerText.text =
//                        String.format("%02d:%02d:%02d", hrs, mins, secs);

                    timerInSeconds++

                    handler.postDelayed(this, 1000)
                }
            }
        )
    }

    fun pauseTimer() {
        handler.removeCallbacksAndMessages(null)
        _uiState.update { currentState ->
            currentState.copy(
                buttonStop = View.GONE,
                recordPauseLayout = View.VISIBLE
            )
        }
//        binding.buttonStop.visibility = View.GONE
//        binding.recordPauseLayout.visibility = View.VISIBLE
        runStatus = RunStatus.RECORD_STOP
    }

    fun resumeRecord() {
        _uiState.update { currentState ->
            currentState.copy(
                recordPauseLayout = View.GONE,
                buttonStop = View.VISIBLE
            )
        }
        runStatus = RunStatus.RECORD_START
    }

    fun focusGps() {
        mapController.animateTo(startPoint)
    }

    fun setAnimation(binding: ActivityRecordRunBinding) {
        constraintSet = ConstraintSet()
        constraintSet.clone(binding.root)

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

    fun expandUp() {
        vaOrange.start()
        vaWhite.start()
        vaMap.start()
        mapController.animateTo(startPoint)

        Handler(Looper.myLooper()!!).postDelayed(
            {
                _uiState.update { currentState ->
                    currentState.copy(
                        initLayout = View.GONE,
                        recordLayout = View.VISIBLE
                    )
                }
                startTimer()
                runStatus = RunStatus.RECORD_START
            },
            300
        )
    }

    private fun expandDown() {
        vaWhite.reverse()
        vaOrange.reverse()
    }
}