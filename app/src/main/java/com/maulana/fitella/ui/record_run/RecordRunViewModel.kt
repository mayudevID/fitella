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

    lateinit var constraintSet: ConstraintSet
    var startPoint: GeoPoint = GeoPoint(46.55951, 15.63970);
    lateinit var mapController: IMapController
    private var pathTracker: Polyline? = null
    private var marker: Marker? = null

    private lateinit var vaOrange: ValueAnimator
    private lateinit var vaWhite: ValueAnimator
    private lateinit var vaMap: ValueAnimator

    private val df = DecimalFormat("#.##")

    private var runStatus: RunStatus = RunStatus.RECORD_INIT

    fun setActivity(activity: RecordRunActivity) {
        this.activity = activity
    }

    fun updateLocation(newLocation: Location, newListSpeed: ArrayList<Float>?) {
        if (newLocation.latitude != startPoint.latitude || newLocation.longitude != startPoint.longitude) {
            startPoint.longitude = newLocation.longitude
            startPoint.latitude = newLocation.latitude

            getPositionMarker().position = startPoint
            mapController.setZoom(18.5)
            mapController.animateTo(startPoint)

            if (runStatus == RunStatus.RECORD_START) {
                getPath().addPoint(startPoint.clone())

                _uiState.update { currentState ->
                    currentState.copy(
                        averageText = df.format(newListSpeed?.average()),
                        distanceText = df.format(getPath().distance / 1000)
                    )
                }
            }

            map.invalidate()
        }
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

    fun setTimer(text: String) {
        _uiState.update { currentState ->
            currentState.copy(
                timerText = text
            )
        }
    }

    fun pauseTimer() {
        _uiState.update { currentState ->
            currentState.copy(
                buttonStop = View.GONE,
                recordPauseLayout = View.VISIBLE
            )
        }

        runStatus = RunStatus.RECORD_STOP
    }

    fun resumeTimer() {
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
            },
            300
        )

        runStatus = RunStatus.RECORD_START
    }

    private fun expandDown() {
        vaWhite.reverse()
        vaOrange.reverse()
    }
}