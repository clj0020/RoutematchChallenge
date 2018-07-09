package com.routematch.routematchcodingchallenge.ui.main

import android.annotation.SuppressLint
import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.routematch.routematchcodingchallenge.R
import android.content.pm.PackageManager
import android.graphics.Point
import android.location.Location
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.google.android.gms.maps.model.LatLng
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.MarkerOptions

import com.routematch.routematchcodingchallenge.BR
import com.routematch.routematchcodingchallenge.data.models.Place
import com.routematch.routematchcodingchallenge.databinding.ActivityMainBinding
import com.routematch.routematchcodingchallenge.ui.base.BaseActivity
import com.routematch.routematchcodingchallenge.ui.place.PlaceActivity
import javax.inject.Inject
import com.routematch.routematchcodingchallenge.util.LocationHelperLiveData
import com.routematch.routematchcodingchallenge.util.ViewUtils



/**
 * An activity that displays a map showing the device's current location along with a list of nearby places.
 * For this Challenge, I chose to show the user nearby restaurants, but the type of place can be easily changed programmatically according to what type of places the user wants.
 */
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(),
        MainNavigator,
        OnMapReadyCallback,
        NearbyPlacesListAdapter.NearbyPlacesAdapterListener {

    // Injects the Nearby Places RecyclerView Adapter.
    @Inject
    lateinit var mNearbyPlacesAdapter: NearbyPlacesListAdapter

    // Injects a horizontal layout manager for the Nearby Places RecyclerView.
    @Inject
    lateinit var mLayoutManager: LinearLayoutManager

    // Injects the ViewModel for this class.
    @Inject
    override lateinit var viewModel: MainViewModel
        internal set

    private var mActivityMainBinding: ActivityMainBinding? = null

    /** Sets the binding variable for the viewmodel. **/
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

        // Execute other pending bindings.
        mActivityMainBinding!!.executePendingBindings()

        // Set the navigator
        viewModel.navigator = this

        // Sets up the UI and subscribes to Location Helper and Nearby Places
        setup()
    }

    // Sets up the UI and then subscribes to Location Helper and Nearby Places.
    private fun setup() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mMapFragment = supportFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment
        mMapFragment.getMapAsync(this)

        // Set up recyclerview
        mNearbyPlacesAdapter.setListener(this) // Sets listener for Nearby Places adapter.
        mActivityMainBinding!!.nearbyPlacesListRecyclerView.setLayoutManager(mLayoutManager) // Sets layout manager for Nearby Places RecyclerView
        mActivityMainBinding!!.nearbyPlacesListRecyclerView.setItemAnimator(DefaultItemAnimator()) // Sets item animator for Nearby Places RecyclerView
        mActivityMainBinding!!.nearbyPlacesListRecyclerView.setAdapter(mNearbyPlacesAdapter) // Sets adapter for Nearby Places RecyclerView

        // Subscribe to Location Helper, this will perform most location functions such as getting current location, checking location settings, and checking permissions.
        subscribeToLocationHelper()
        // Subscribe to Nearby Places List in the ViewModel. This will populate the RecyclerView with NearbyPlaces when a new current location is found.
        subscribeToNearbyPlacesListLiveData()
    }

    /** Subscribes to Nearby Places List in the ViewModel. This will populate the RecyclerView with NearbyPlaces when a new current location is found. **/
    private fun subscribeToNearbyPlacesListLiveData() {
        viewModel.nearbyPlacesListLiveData.observe(this, Observer<List<Place>> { nearbyPlaces ->
            // Add nearby place items to RecyclerView.
            viewModel.addPlaceItemsToList(nearbyPlaces!!)

            // Add markers to the map.
            for (place in nearbyPlaces) {
                if (place.lat != null && place.lng != null) {
                    mGoogleMap.addMarker(MarkerOptions().position(LatLng(place.lat, place.lng)).title(place.name))
                }
            }
        })
    }

    /** Adds new nearby places to the nearby places list. **/
    override fun updateNearbyPlacesList(nearbyPlaces: List<Place>) {
        mNearbyPlacesAdapter.addItems(nearbyPlaces)
    }

    /** Called when the map fragment is created. **/
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

                // Offset center of map to be above Nearby Places list.
                mGoogleMap.setPadding(0, 0, 0, ViewUtils.dpToPx(400.0f) /* Sets bottom padding to the same value as height of RecyclerView. */)

                // Animate the camera to the new location.
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude, location.longitude), 14.0f))

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

    /** When a place is clicked in the Nearby Places list, this takes the user to the PlaceActivity to view Place Details. **/
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
