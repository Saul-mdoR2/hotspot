package com.example.hotspot

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    // private val permissionCode: Int = 100
    // var enabled = false

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val activar = findViewById<Button>(R.id.btnActivar)

        /*  activar.setOnClickListener {
              enabled = false
              checkPermissions()
          }*/

        checkPermissions()
        // ANDROID OREO AND ABOVE
        activar.setOnClickListener {
            HotspotManager.turnOnHotspot(this)
        }
    }
    /*
    private fun checkPermissions() {
        val permissionGrantedNetworkState = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.CHANGE_WIFI_STATE
        ) == PackageManager.PERMISSION_GRANTED
        if (permissionGrantedNetworkState) {
            WifiAccessManager.setWifiApState(this, enabled)
        } else {
            requestPermission()
        }
    }

    private fun requestPermission() {
        val showRequestRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CHANGE_WIFI_STATE)
        if (showRequestRationale) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CHANGE_WIFI_STATE), permissionCode)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            permissionCode ->{
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    WifiAccessManager.setWifiApState(this, enabled)
                }else{
                   Toast.makeText(this, "No es posible activar Hotspot, se requieren permisos.", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }*/

    private fun checkPermissions() {
        val permissionGrantedNetworkState = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        if (!permissionGrantedNetworkState)
            requestPermission()
    }

    private fun requestPermission() {
        val showRequestRationale = ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (showRequestRationale) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                HotspotManager.requestCode
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            100 -> {
                if (grantResults.isNotEmpty() && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                        this,
                        "No es posible activar Hotspot, se requieren permisos.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                return
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}