package com.example.noodoe.network.consts

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

/**
 * 讀取網路狀態
 */
object NetworkUtil {
    fun isAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> isVPNAvailable(connectivityManager)
            }
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo ?: return false
            return nwInfo.isConnected
        }
    }

    private fun isVPNAvailable(cm: ConnectivityManager): Boolean {
        var isAvailable = false
        val networks = cm.allNetworks
        for (network in networks) {
            val caps = cm.getNetworkCapabilities(network)
            if (caps != null) {
                if (caps.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
                    isAvailable = true
                }
            }
        }
        return isAvailable
    }
}