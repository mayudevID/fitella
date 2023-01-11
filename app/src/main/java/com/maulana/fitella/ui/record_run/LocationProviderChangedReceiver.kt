package com.maulana.fitella.ui.record_run

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.util.Log

class LocationProviderChangedReceiver : BroadcastReceiver() {

    private var isGpsEnabled: Boolean = false
    private var isNetworkEnabled: Boolean = false

    override fun onReceive(context: Context, intent: Intent) {
        intent.action?.let { act ->
            if (act.matches("android.location.PROVIDERS_CHANGED".toRegex())) {
                val locationManager =
                    context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                isNetworkEnabled =
                    locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
                Log.i("RecordRunActivity","Location Providers changed, is GPS Enabled: " + isGpsEnabled)
                //Start your Activity if location was enabled:
                MyEventLocationSettingsChange.setChangeAndPost(isGpsEnabled) //notify
                //if (isGpsEnabled) {
                //Some action
                //}
            }
        }
    }
}