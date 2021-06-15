@file:Suppress("DEPRECATION")

package com.example.hotspot

import android.content.Context
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager


object WifiAccessManager {
    private const val SSID = "1234567890abcdef"
    fun setWifiApState(context: Context, enabled: Boolean): Boolean {
        //config = Preconditions.checkNotNull(config);
        return try {
            val mWifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            if (enabled) {
                mWifiManager.isWifiEnabled = false
            }
            val conf = wifiApConfiguration
            mWifiManager.addNetwork(conf)
            mWifiManager.javaClass.getMethod(
                "setWifiApEnabled",
                WifiConfiguration::class.java,
                Boolean::class.javaPrimitiveType
            ).invoke(mWifiManager, conf, enabled) as Boolean
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    private val wifiApConfiguration: WifiConfiguration
        get() {
            val conf = WifiConfiguration()
            conf.SSID = SSID
            conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE)
            return conf
        }
}