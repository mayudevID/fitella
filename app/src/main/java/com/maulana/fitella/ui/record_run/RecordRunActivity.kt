package com.maulana.fitella.ui.record_run

import android.Manifest
import android.content.*
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.preference.PreferenceManager
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.maulana.fitella.databinding.ActivityRecordRunBinding
import com.maulana.fitella.services.LocationService
import com.maulana.fitella.services.LocationServiceUtils
import com.maulana.fitella.utils.RunStatus
import com.maulana.fitella.utils.getEnumExtra
import com.maulana.fitella.utils.isServiceRunning
import com.maulana.fitella.utils.parcelable
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.osmdroid.config.Configuration.*
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay

class RecordRunActivity : AppCompatActivity() {
    private val TAG = "RecordRunActivity"
    private var activityResultLauncher: ActivityResultLauncher<Array<String>>

    private lateinit var binding: ActivityRecordRunBinding
    private val recordRunViewModel: RecordRunViewModel by viewModels()

    private val REQUEST_PERMISSIONS_REQUEST_CODE = 1

    private lateinit var mService: LocationService
    private var mBound: Boolean = false

    private var newLoc: Location? = null
    private var newTime: String? = null

    private var isGpsEnabled: Boolean = false
    private var isNetworkEnabled: Boolean = false
    private var intentFilterGpsData: IntentFilter = IntentFilter("GPSLocationUpdates")
    private var intentFilterTimerData: IntentFilter = IntentFilter("TimerUpdates")
    private var intentFilterLocChange: IntentFilter = IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)

    private lateinit var mLocationReceiver: BroadcastReceiver
    private lateinit var mLocationChangeReceiver: BroadcastReceiver
    private lateinit var mTimerReceiver: BroadcastReceiver

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as LocationService.MyBinder
            mService = binder.getService()
            mBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }

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
                initCheckLocationSettings()
            }
        }
    }

    companion object {
        const val REQUEST_CHECK_SETTINGS = 20202
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "ON_START")

        val intentBind = Intent(this, LocationService::class.java)
        bindService(intentBind, connection, Context.BIND_AUTO_CREATE)

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "ON_CREATE")

        supportActionBar?.hide()
        recordRunViewModel.setActivity(this@RecordRunActivity)

        setBroadcastReceiver()

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
            Manifest.permission.INTERNET,
        )

        activityResultLauncher.launch(appPerms)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val appPermsSecond = arrayOf(
                Manifest.permission.FOREGROUND_SERVICE,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION,
            )

            activityResultLauncher.launch(appPermsSecond)
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "ON_RESUME")
        binding.map.onResume()
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "ON_PAUSE")
        binding.map.onPause()
    }

    override fun onStop() {
        Log.d(TAG, "ON_STOP")
        super.onStop()
    }

    fun apa() {
        unbindService(connection)
        mBound = false

        LocationServiceUtils(this@RecordRunActivity).stopLocService()
        unregisterReceiver(mLocationReceiver)
        unregisterReceiver(mLocationChangeReceiver)
        unregisterReceiver(mTimerReceiver)

        EventBus.getDefault().unregister(this);
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "Settings onActivityResult for $requestCode result $resultCode")
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == RESULT_OK) {
//                recordRunViewModel.initMap()
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

    private fun initCheckLocationSettings() {
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(
                LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
                .setMinUpdateIntervalMillis(500)
                .setMinUpdateDistanceMeters(0.5f)
                .setMaxUpdates(750)
                .build()
            )
        val client: SettingsClient = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())
        task.addOnSuccessListener {
            Log.d(TAG, "Settings Location IS OK")
            MyEventLocationSettingsChange.globalState = true

            checkService()
        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                Log.d(TAG, "Settings Location addOnFailureListener call settings")
                try {
                    exception.startResolutionForResult(
                        this,
                        RecordRunActivity.REQUEST_CHECK_SETTINGS
                    )
                } catch (sendEx: IntentSender.SendIntentException) {
                    Log.d(TAG, "Settings Location sendEx??")
                }
            }
        }

    }

    private fun uiState() {
        with (binding) {
           buttonStart.setOnClickListener {
               LocationServiceUtils(this@RecordRunActivity).startRecord()
               recordRunViewModel.expandUp()
           }

           buttonStop.setOnClickListener {
               LocationServiceUtils(this@RecordRunActivity).pauseRecord()
               recordRunViewModel.pauseTimer()
            }

           buttonResume.setOnClickListener {
               LocationServiceUtils(this@RecordRunActivity).resumeRecord()
               recordRunViewModel.resumeTimer()
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

    private fun setBroadcastReceiver() {
        //Location Change Receiver
        mLocationChangeReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                intent?.action?.let { act ->
                    if (act.matches("android.location.PROVIDERS_CHANGED".toRegex())) {
                        val locationManager =
                            context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                        isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                        isNetworkEnabled =
                            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
                        Log.i("RecordRunActivity","Location Providers changed, is GPS Enabled: " + isGpsEnabled)

                        MyEventLocationSettingsChange.setChangeAndPost(isGpsEnabled)
                    }
                }
            }
        }
        registerReceiver(mLocationChangeReceiver, intentFilterLocChange)

        //Location Receiver
        mLocationReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent) {
                newLoc = intent.parcelable("LOCATION")

                if (newLoc != null) {
                    if (mBound) {
                        recordRunViewModel.updateLocation(
                            newLoc!!,
                            mService.listSpeed,
                        )
                        Log.d(TAG, newLoc.toString())
                    } else {
                        Log.d(TAG, "Service not bound")
                    }
                    newLoc = null
                }
            }
        }
        registerReceiver(mLocationReceiver, intentFilterGpsData)

        //Timer Receiver
        mTimerReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent) {
                newTime = intent.getStringExtra("TIMER")

                if (newTime != null) {
                    recordRunViewModel.setTimer(newTime!!)
                    newTime = null
                }
            }
        }
        registerReceiver(mTimerReceiver, intentFilterTimerData)
    }

    private fun checkService() {
        LocationServiceUtils(this).startLocService()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMsg(status: MyEventLocationSettingsChange) {
        if (status.on) {
            Log.i(TAG, "LOC CHANGE SUBSCRIBE")
            checkService()
        } else {
            Log.i(TAG, "LOC CHANGE STOP")
        }
    }
}