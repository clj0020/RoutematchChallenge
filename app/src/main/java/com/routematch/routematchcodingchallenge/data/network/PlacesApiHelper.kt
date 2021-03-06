package com.routematch.routematchcodingchallenge.data.network

import android.location.Location
import com.routematch.routematchcodingchallenge.data.models.Place
import com.routematch.routematchcodingchallenge.data.network.responses.PlacesResponse
import io.reactivex.Single

/**
 * An interface for the Places Api Helper
 */
interface PlacesApiHelper {

    fun getNearbyPlaces(location: Location, radius: Int, type: String): Single<List<Place>>

    fun getPlaceDetails(place_id: String): Single<Place?>
}