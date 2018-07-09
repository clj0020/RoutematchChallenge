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
import com.routematch.routematchcodingchallenge.util.ImageLoaderCallback
import javax.inject.Inject

/**
 * An activity that displays the details of a selected Place.
 */
class PlaceActivity : BaseActivity<ActivityPlaceBinding, PlaceViewModel>(),
        PlaceNavigator,
        ImageLoaderCallback {

    /** Injects the viewmodel. **/
    @Inject
    override lateinit var viewModel: PlaceViewModel
        internal set

    private var mActivityPlaceBinding: ActivityPlaceBinding? = null

    /** Sets the binding variable **/
    override val bindingVariable: Int
        get() = BR.viewModel

    /** Sets Layout File **/
    override val layoutId: Int
        get() = R.layout.activity_place

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityPlaceBinding = viewDataBinding

        // Set callback for image loading before executing pending bindings.
        mActivityPlaceBinding!!.setImageLoaderCallback(this)

        // Execute other pending bindings.
        mActivityPlaceBinding!!.executePendingBindings()

        // Set Navigator
        viewModel.navigator = this

        // Sets up the UI and subscribes to the place live data in the viewmodel.
        setup()
    }

    /** Sets up the UI and subscribes to the place live data in the viewmodel. **/
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
        // Fetch the Place from the Google Places API.
        viewModel.fetchPlace(place_id = place_id)

        // Observes the ViewModel's Place Live Data object and bind the result to the layout.
        viewModel.placeLiveData.observe(this, Observer<Place> { place: Place? ->
            if (place != null) {
                // Binds the place to the layout.
                mActivityPlaceBinding!!.setPlace(place)
            }
        })
    }

    /** Called when the image loading process begins. **/
    override fun onImageLoading() {
        // Shows the loading progress spinner
        viewModel.setIsLoading(true)
    }

    /** Called when the image has successfully loaded from the Places Photo API **/
    override fun onImageReady() {
        // Hides the loading progress spinner.
        viewModel.setIsLoading(false)
    }

    /** Called when the image loading process encounters an error **/
    override fun onImageLoadError() {
        // Hides the loading progress spinner
        viewModel.setIsLoading(false)

        // Sends an error message.
        handleError(Throwable(message = "Error loading place image.", cause = Exception()))
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