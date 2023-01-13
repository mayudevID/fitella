package com.maulana.fitella.ui.record_run

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.preference.PreferenceManager
import com.maulana.fitella.databinding.ActivityRecordRunBinding
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.osmdroid.config.Configuration.*
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay
import kotlin.collections.ArrayList

class RecordRunActivity : AppCompatActivity() {
    private val TAG: String = "RecordRunActivity"
    private var activityResultLauncher: ActivityResultLauncher<Array<String>>

    private lateinit var binding: ActivityRecordRunBinding
    private val recordRunViewModel: RecordRunViewModel by viewModels()

    private val REQUEST_PERMISSIONS_REQUEST_CODE = 1

    init {
        this.activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { result ->
            var allAreGranted = true
            for (b in result.values) {
                allAreGranted = allAreGranted && b
            }

            Log.d(TAG, "Permissions granted $allAreGranted")
            if (allAreGranted) {
                recordRunViewModel.initCheckLocationSettings()
            }
        }
    }

    companion object {
        const val REQUEST_CHECK_SETTINGS = 20202
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        recordRunViewModel.setActivity(this@RecordRunActivity)

        val br: BroadcastReceiver = LocationProviderChangedReceiver()
        val filter = IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
        registerReceiver(br, filter)

        getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))

        binding = ActivityRecordRunBinding.inflate(layoutInflater)
        uiState()

        with (recordRunViewModel) {
            setAnimation(binding)

            map = binding.map
            map.setTileSource(TileSourceFactory.MAPNIK)
            map.setMultiTouchControls(true)
            mapController = map.controller

            val rotationGestureOverlay = RotationGestureOverlay(map)
            rotationGestureOverlay.isEnabled
            map.overlays.add(rotationGestureOverlay)

            val mCompassOverlay =
                CompassOverlay(
                    this@RecordRunActivity,
                    InternalCompassOrientationProvider(this@RecordRunActivity),
                    map
                )
            mCompassOverlay.enableCompass()
            map.overlays.add(mCompassOverlay)

            setAnimation(binding)
        }

        setContentView(binding.root)

        val appPerms = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET
        )
        activityResultLauncher.launch(appPerms)
    }

    override fun onResume() {
        super.onResume()
        binding.map.onResume()
    }

    override fun onPause() {
        super.onPause()
        if (recordRunViewModel.requestingLocationUpdates) {
            recordRunViewModel.requestingLocationUpdates = false
            recordRunViewModel.stopLocationUpdates()
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
                recordRunViewModel.initMap()
            }
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

    private fun uiState() {
        with (binding) {
           buttonStart.setOnClickListener {
                recordRunViewModel.expandUp()
            }

           buttonStop.setOnClickListener {
                recordRunViewModel.pauseTimer()
            }

           buttonResume.setOnClickListener {
                recordRunViewModel.startTimer()
                recordRunViewModel.resumeRecord()
            }

           fabGps.setOnClickListener {
                recordRunViewModel.focusGps()
            }

           buttonClose.setOnClickListener {
                finish()
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                recordRunViewModel.uiState.collect { value ->
                    with(binding) {
                        averageText.text = value.averageText
                        distanceText.text = value.distanceText
                        timerText.text = value.timerText
                        buttonStop.visibility = value.buttonStop
                        recordPauseLayout.visibility = value.recordPauseLayout
                        initLayout.visibility = value.initLayout
                        recordLayout.visibility = value.recordLayout
                    }
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMsg(status: MyEventLocationSettingsChange) {
        if (status.on) {
            recordRunViewModel.initMap()
        } else {
            Log.i(TAG, "Stop something")
        }
    }
}