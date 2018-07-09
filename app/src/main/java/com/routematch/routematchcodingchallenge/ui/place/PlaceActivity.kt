package com.routematch.routematchcodingchallenge.ui.place

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.routematch.routematchcodingchallenge.BR
import com.routematch.routematchcodingchallenge.R
import com.routematch.routematchcodingchallenge.data.models.Place
import com.routematch.routematchcodingchallenge.databinding.ActivityPlaceBinding
import com.routematch.routematchcodingchallenge.ui.base.BaseActivity
import com.routematch.routematchcodingchallenge.ui.main.MainActivity
import javax.inject.Inject

class PlaceActivity : BaseActivity<ActivityPlaceBinding, PlaceViewModel>(),
        PlaceNavigator {

    @Inject
    override lateinit var viewModel: PlaceViewModel
        internal set

    private var mActivityPlaceBinding: ActivityPlaceBinding? = null

    override val bindingVariable: Int
        get() = BR.viewModel

    /** Sets Layout File **/
    override val layoutId: Int
        get() = R.layout.activity_place

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityPlaceBinding = viewDataBinding
        viewModel.navigator = this

        setup()
    }

    fun setup() {
        // Check if there are intent extras.
        if (intent.extras != null) {
            // Get the place id of the place from the intent extras.
            val place_id: String? = intent.extras.getString(KEY_PLACE_ID)
            if (place_id != null) {
                // Fetch the place details and subscribe to the viewmodel's place live data
                subscribeToPlaceLiveData(place_id)
            }
            else {
                // No place id sent to activity. Return to MainActivity
                Toast.makeText(this, "Can't find the place id of this place! Returning to map.", Toast.LENGTH_SHORT).show()
                val intent = MainActivity.newIntent(this)
                startActivity(intent)
            }
        }
        else {
            // No place id sent to activity. Return to MainActivity
            Toast.makeText(this, "Can't find the place id of this place! Returning to map.", Toast.LENGTH_SHORT).show()
            val intent = MainActivity.newIntent(this)
            startActivity(intent)
        }
    }

    /** Fetchs the Place Details and subscribes to the Viewmodel's Place Live Data. **/
    fun subscribeToPlaceLiveData(place_id: String) {
        viewModel.fetchPlace(place_id = place_id)
        viewModel.placeLiveData.observe(this, Observer<Place> { place: Place? ->
            if (place != null) {
                mActivityPlaceBinding!!.setPlace(place)
            }
        })
    }

    /** Handles errors. **/
    override fun handleError(throwable: Throwable) {
        if (throwable.message != null) {
            // show specific error message if throwable has a message.
            showError("Oh no!", throwable.message.toString())
        }
        else {
            // show default error.
            showError("Oh no!", "An unknown error occurred!")
        }
    }

    /** Contains Constants and Initialization **/
    companion object {
        val TAG = PlaceActivity::class.java.simpleName
        val KEY_PLACE_ID = "place_id"

        fun newIntent(context: Context, id: String?): Intent {
            val intent = Intent(context, PlaceActivity::class.java)
            intent.putExtra(KEY_PLACE_ID, id)
            return intent
        }
    }
}