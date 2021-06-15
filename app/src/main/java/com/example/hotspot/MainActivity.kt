package com.example.hotspot

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val activar = findViewById<Button>(R.id.btnActivar)
        val desactivar = findViewById<Button>(R.id.btnDesactivar)


        activar.setOnClickListener {
            WifiAccessManager.setWifiApState(this, false)
        }

        desactivar.setOnClickListener {
            WifiAccessManager.setWifiApState(this, true)
        }
    }
}