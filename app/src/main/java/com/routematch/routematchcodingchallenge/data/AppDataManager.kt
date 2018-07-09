package com.routematch.routematchcodingchallenge.data

import android.content.Context
import android.location.Location
import com.routematch.routematchcodingchallenge.data.models.Place
import javax.inject.Inject
import javax.inject.Singleton
import com.routematch.routematchcodingchallenge.util.SchedulerProvider
import com.routematch.routematchcodingchallenge.data.network.PlacesApiHelper
import com.routematch.routematchcodingchallenge.data.network.responses.PlacesResponse
import io.reactivex.Observable
import io.reactivex.Single


/**
 * This is the instantiation of the DataManager and can be used to interact with the API and the Local Database.
 */
@Singleton
class AppDataManager @Inject constructor(private val mContext: Context,
                                         private val schedulerProvider: SchedulerProvider,
                                         private val mPlacesApiHelper: PlacesApiHelper) : DataManager {

    /** Calls the Places API Helper function for finding nearby places. **/
    override fun getNearbyPlaces(location: Location, radius: Int, type: String): Single<List<Place>> {
        return mPlacesApiHelper.getNearbyPlaces(
                location = location,
                radius = radius,
                type = type
        )
    }

    /** Calls the Places API Helper function for getting place details. **/
    override fun getPlaceDetails(place_id: String): Single<Place?> {
        return mPlacesApiHelper.getPlaceDetails(
                place_id = place_id
        )
    }

}