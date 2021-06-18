@file:Suppress("DEPRECATION")

package com.example.hotspot

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Handler
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

private var wifiManager: WifiManager? = null
var currentConfig: WifiConfiguration? = null
var hotspotReservation: WifiManager.LocalOnlyHotspotReservation? = null

object HotspotManager {
    const val requestCode = 100

    @RequiresApi(api = Build.VERSION_CODES.O)
    fun turnOnHotspot(context: AppCompatActivity) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermission(context)
            return
        }
        wifiManager =
            context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        wifiManager!!.startLocalOnlyHotspot(object : WifiManager.LocalOnlyHotspotCallback() {
            override fun onStarted(reservation: WifiManager.LocalOnlyHotspotReservation) {
                super.onStarted(reservation)
                hotspotReservation = reservation
                currentConfig = hotspotReservation!!.wifiConfiguration
                Log.v("HotspotInfo", "Local Hotspot started")
                Log.v(
                    "HotspotInfo", """THE PASSWORD IS: ${currentConfig!!.preSharedKey} 
                    SSID is : ${currentConfig!!.SSID}"""
                )
                hotspotDetailsDialog()
            }

            override fun onStopped() {
                super.onStopped()
                Log.v("HotspotInfo", "Local Hotspot Stopped")
            }

            override fun onFailed(reason: Int) {
                super.onFailed(reason)
                Log.v("HotspotInfo", "Local Hotspot failed to start")
            }
        }, Handler())
    }

    private fun hotspotDetailsDialog() {
        Log.v(
            "HotspotInfo",
            " " + currentConfig!!.SSID + "\n" + " " + currentConfig!!.preSharedKey
        )
    }


    private fun requestPermission(context: AppCompatActivity) {
        ActivityCompat.requestPermissions(
            context,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            requestCode
        )
    }
}
