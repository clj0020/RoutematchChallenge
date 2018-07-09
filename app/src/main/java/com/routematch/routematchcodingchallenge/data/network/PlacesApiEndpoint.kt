package com.routematch.routematchcodingchallenge.data.network

import com.routematch.routematchcodingchallenge.BuildConfig

/** This contains the endpoints for different use cases involving the Google Places API **/
object PlacesApiEndPoint {

    val ENDPOINT_PLACES_AROUND_LOCATION = BuildConfig.BASE_URL + "/maps/api/place/nearbysearch/json"

    val ENDPOINT_PLACE_DETAILS = BuildConfig.BASE_URL + "/maps/api/place/details/json"

    val ENDPOINT_PLACE_PHOTO = BuildConfig.BASE_URL + "/maps/api/place/photo"

}// This class is not publicly instantiable