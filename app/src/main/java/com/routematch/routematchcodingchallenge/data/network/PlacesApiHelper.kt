package com.routematch.routematchcodingchallenge.data.network

import android.location.Location
import com.routematch.routematchcodingchallenge.data.models.Place
import com.routematch.routematchcodingchallenge.data.network.responses.PlacesResponse
import io.reactivex.Single

interface PlacesApiHelper {

    fun getNearbyPlaces(location: Location, radius: Int, type: String): Single<List<Place>>
}