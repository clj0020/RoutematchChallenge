package com.routematch.routematchcodingchallenge.ui.main

import android.annotation.SuppressLint
import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.routematch.routematchcodingchallenge.R
import android.content.pm.PackageManager
import android.location.Location
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.google.android.gms.maps.model.LatLng
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

import com.routematch.routematchcodingchallenge.BR
import com.routematch.routematchcodingchallenge.data.models.Place
import com.routematch.routematchcodingchallenge.databinding.ActivityMainBinding
import com.routematch.routematchcodingchallenge.ui.base.BaseActivity
import com.routematch.routematchcodingchallenge.ui.place.PlaceActivity
import javax.inject.Inject
import com.routematch.routematchcodingchallenge.util.LocationHelperLiveData



/**
 * An activity that displays a map showing the device's current location.
 */
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(),
        MainNavigator,
        OnMapReadyCallback,
        NearbyPlacesListAdapter.NearbyPlacesAdapterListener {

    @Inject
    lateinit var mNearbyPlacesAdapter: NearbyPlacesListAdapter

    @Inject
    lateinit var mLayoutManager: LinearLayoutManager

    private var mNearbyPlaces: List<Place> = arrayListOf()

    @Inject
    override lateinit var viewModel: MainViewModel
        internal set

    private var mActivityMainBinding: ActivityMainBinding? = null

    override val bindingVariable: Int
        get() = BR.viewModel

    /** Sets Layout File **/
    override val layoutId: Int
        get() = R.layout.activity_main

    lateinit var mMapFragment: SupportMapFragment
    lateinit var mGoogleMap: GoogleMap
    lateinit var mLocation: Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityMainBinding = viewDataBinding
        viewModel.navigator = this

        setup()
    }

    private fun setup() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mMapFragment = supportFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment
        mMapFragment.getMapAsync(this)

        mNearbyPlacesAdapter.setListener(this)

        mActivityMainBinding!!.nearbyPlacesListRecyclerView.setLayoutManager(mLayoutManager)
        mActivityMainBinding!!.nearbyPlacesListRecyclerView.setItemAnimator(DefaultItemAnimator())
        mActivityMainBinding!!.nearbyPlacesListRecyclerView.setAdapter(mNearbyPlacesAdapter)

        subscribeToLocationHelper()
        subscribeToNearbyPlacesListLiveData()
    }

    private fun subscribeToNearbyPlacesListLiveData() {
        viewModel.nearbyPlacesListLiveData.observe(this, Observer<List<Place>> { nearbyPlaces ->
            viewModel.addPlaceItemsToList(nearbyPlaces!!)
            mNearbyPlaces = nearbyPlaces
        })
    }

    override fun updateNearbyPlacesList(nearbyPlaces: List<Place>) {
        mNearbyPlacesAdapter.addItems(nearbyPlaces)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap
    }

    /** Subscribe to Location Helper Live Data and Reacts in Map to changes. Location Helper checks permissions. **/
    @SuppressLint("MissingPermission")
    fun subscribeToLocationHelper() {
        // observe location live data.
        LocationHelperLiveData(this@MainActivity).observe(this, Observer<Location> {location: Location? ->
            if (location != null) {
                // If location is not null, Move Google Map camera to location.
                mLocation = location
                mGoogleMap.setMyLocationEnabled(true)
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude, location.longitude), 18.0f))

                // Next we'll connect to the Google Places API and Populate a RecyclerView with the results.
                viewModel.fetchNearbyPlaces(
                        location = mLocation,
                        radius = DEFAULT_NEARBY_PLACES_RADIUS,
                        type = DEFAULT_NEARBY_PLACES_TYPE
                )
            }
            else {
                // if the location is null, then we don't do anything.
                Log.i(TAG, "No location found.")
            }
        })
    }

    /** This is called after the user is asked to turn on location setting. **/
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            REQUEST_CHECK_SETTINGS -> when (resultCode) {
                Activity.RESULT_OK -> {
                    // User agreed to make required location settings changes.
                    Log.i(TAG, "User agreed to make required location settings changes.")

                    // We can now subscribe to location helper live data because it will check for permissions.
                    subscribeToLocationHelper()
                }
                Activity.RESULT_CANCELED -> {
                    Log.i(TAG, "User chose not to make required location settings changes.")
                }
            }
        }
    }

    /** React to location permission dialog. **/
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_LOCATION_PERMISSION -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted.
                    subscribeToLocationHelper()
                }
                else {
                    // permission denied.
                    Log.i(TAG, "User chose not to give required location permission.")
                }
                return
            }
            else -> {
                // Ignore all other requests.
            }
        }
    }

    override fun onPlaceClick(place_id: String?) {
        startActivity(PlaceActivity.newIntent(this, place_id))
    }

    /** Handles errors. **/
    override fun handleError(throwable: Throwable) {
        // handle error
        if (throwable.message != null) {
            showError("Oh no!", throwable.message.toString())
        }
        else {
            showError("Oh no!", "An unknown error occurred!")
        }
    }

    /** Contains Constants and Initialization **/
    companion object {
        val TAG = MainActivity::class.java.simpleName
        val REQUEST_CHECK_SETTINGS: Int = 0x1
        val REQUEST_LOCATION_PERMISSION: Int = 200

        val DEFAULT_NEARBY_PLACES_RADIUS = 1 * 5000 // 5 km
        val DEFAULT_NEARBY_PLACES_TYPE = "restaurant"

        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}
