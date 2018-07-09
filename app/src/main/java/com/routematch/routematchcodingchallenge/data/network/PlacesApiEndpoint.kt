package com.routematch.routematchcodingchallenge.data.network

import com.routematch.routematchcodingchallenge.BuildConfig

object PlacesApiEndPoint {

    val ENDPOINT_PLACES_AROUND_LOCATION = BuildConfig.BASE_URL + "/maps/api/place/nearbysearch/json"

    val ENDPOINT_PLACE_DETAILS = BuildConfig.BASE_URL + "/maps/api/place/details/json"

}// This class is not publicly instantiable