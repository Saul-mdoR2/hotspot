@file:Suppress("DEPRECATION")

package com.example.hotspot

import android.content.Context
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import java.lang.reflect.Method


object WifiAccessManager {
    private const val SSID = "1234567890abcdef"
    fun setWifiApState(context: Context, enabled: Boolean): Boolean {
        //config = Preconditions.checkNotNull(config);
        return try {
            val mWifiManager =
                context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            if (enabled) {
                mWifiManager.isWifiEnabled = false
            }
            val conf = wifiApConfiguration
            mWifiManager.addNetwork(conf)
            mWifiManager.javaClass.getMethod(
                "setWifiApEnabled",
                WifiConfiguration::class.java,
                Boolean::class.java
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


    /* fun isApOn(context: Context): Boolean {
         val wifimanager =
             context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
         try {
             val method: Method = wifimanager.javaClass.getDeclaredMethod("isWifiApEnabled")
             method.isAccessible = true
             return method.invoke(wifimanager) as Boolean
         } catch (ignored: Throwable) {
         }
         return false
     }

     // toggle wifi hotspot on or off
     fun configApState(context: Context): Boolean {
         val wifimanager =
             context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
         val wificonfiguration: WifiConfiguration? = null
         try {
             // if WiFi is on, turn it off
             if (isApOn(context)) {
                 wifimanager.isWifiEnabled = false
             }
             val method: Method = wifimanager.javaClass.getMethod(
                 "setWifiApEnabled",
                 WifiConfiguration::class.java,
                 Boolean::class.java
             )
             method.invoke(wifimanager, wificonfiguration, !isApOn(context))
             return true
         } catch (e: java.lang.Exception) {
             e.printStackTrace()
         }
         return false
     }*/


}