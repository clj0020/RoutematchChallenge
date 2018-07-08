package com.routematch.routematchcodingchallenge.util

import android.Manifest
import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.routematch.routematchcodingchallenge.ui.main.MainActivity


class LocationHelperLiveData(context: Context) : LiveData<Location>(),
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    val TAG = LocationHelperLiveData::class.java.simpleName
    var mContext: Context
    lateinit var mLocationManager: LocationManager
    lateinit var mGoogleApiClient: GoogleApiClient
    lateinit var mLocationRequest: LocationRequest
    var mFusedLocationProviderClient: FusedLocationProviderClient
    var mSettingsClient: SettingsClient
    var mLocationCallback: LocationCallback


    init {
        mContext = context

        // First Build Google Api Client
        buildGoogleApiClient(mContext)

        // Create location request
        createLocationRequest()

        // Initialize FusedLocationProviderClient
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mContext)

        mSettingsClient = LocationServices.getSettingsClient(mContext)

        // Define the location update callback
        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                if (locationResult == null) {
                    value = null
                    return
                }
                for (location in locationResult.locations) {
                    // Set the live data value to the location.
                    value = location
                }
            }
        }
    }

    /** When the class is active, start updating its location. **/
    override fun onActive() {
        super.onActive()

        // Create GoogleAPIClient and start requesting location updates when connected.
        mGoogleApiClient.connect()
    }

    override fun onInactive() {
        super.onInactive()

        if (mGoogleApiClient.isConnected) {
            // Stop requesting location updates and update livedata value.
            mFusedLocationProviderClient.removeLocationUpdates(mLocationCallback)

            // Disconnect Google API client
            mGoogleApiClient.disconnect()
        }
    }

    /** When Google Api Client has connected, check settings and permissions then start listening to location. **/
    override fun onConnected(bundle: Bundle?) {
        Log.d(TAG, "Connected to google api client.")

        // Create a location settings request builder and add our location request.
        val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest)

        // Create settings task
        val settingsTask = mSettingsClient.checkLocationSettings(builder.build()) as Task<LocationSettingsResponse>

        // All location settings are satisfied. We should check permissions here.
        settingsTask.addOnSuccessListener { locationSettingsResponse ->
            Log.i(TAG, "Location Settings satisfied. Checking permission.")

            // Check location permission
            if (checkLocationPermission()) {
                // Set value to the last location that the fused location provider client found.
                mFusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
                    value = location
                }

                // If an activity or fragment is observing and Google API Client is connected.
                if (hasActiveObservers() && mGoogleApiClient.isConnected()) {
                    // Start requesting location updates.
                    startLocationUpdates()
                }
            }
            else {
                // No permission; Ask for permission and subscribe to location helper live data depending on result returned in onPermissionResult.
                requestLocationPermission()
            }
        }

        // Location is off, we may be able to ask the user to turn it on.
        settingsTask.addOnFailureListener {exception ->
            if (exception is ResolvableApiException){
                // Location settings are not satisfied.
                try {
                    // Ask the user to turn on Location.
                    exception.startResolutionForResult(mContext as MainActivity,
                            MainActivity.REQUEST_CHECK_SETTINGS)
                } catch (sendEx: IntentSender.SendIntentException) {
                    Log.e(TAG, sendEx.message)
                }
            }
        }
    }

    /** Builds google api client. **/
    @Synchronized
    fun buildGoogleApiClient(context: Context) {
        mGoogleApiClient = GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()
    }

    /** Creates location request. **/
    fun createLocationRequest() {
        mLocationRequest = LocationRequest().apply {
            interval = 1000 * 60 * 2 // Check every 2 minutes
            fastestInterval = 1000 * 60 * 1 // Fastest update interval is 1 minute.
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    /** We will get permission before instantiating this class. **/
    @SuppressLint("MissingPermission")
    fun startLocationUpdates() {
        mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest,
                mLocationCallback,
                null /* Looper */)
    }

    /** When location is changed, post value to observer. **/
    override fun onLocationChanged(location: Location?) {
        value = location // post location updates to observer.
    }

    /** Returns true if location permission granted. **/
    fun checkLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(mContext as MainActivity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    /** Requests location permission from the User. **/
    fun requestLocationPermission() {
        // Request location permission
        ActivityCompat.requestPermissions(mContext as MainActivity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                MainActivity.REQUEST_LOCATION_PERMISSION)
    }

    override fun onConnectionSuspended(p0: Int) {
        Log.e(TAG, "Connection to Google Api Client was suspended.")
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.e(TAG, "Connection to Google Api Client was suspended.")
    }

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
        Log.i(TAG, "Status of location listener changed.")
    }

    override fun onProviderEnabled(p0: String?) {
        Log.i(TAG, "Location listener provider enabled.")
    }

    override fun onProviderDisabled(p0: String?) {
        Log.e(TAG, "Location listener provider disabled.")
    }
}