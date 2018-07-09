package com.routematch.routematchcodingchallenge.util

import android.content.Context
import android.net.ConnectivityManager

/** A helper for network related tasks. **/
object NetworkUtils {

    // Return true is phone is connected to a network.
    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
}// This class is not publicly instantiable