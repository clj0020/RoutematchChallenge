package com.routematch.routematchcodingchallenge.ui.main

import com.routematch.routematchcodingchallenge.data.models.Place

interface MainNavigator {

    fun handleError(throwable: Throwable)

    fun updateNearbyPlacesList(nearbyPlaces: List<Place>)

    fun onPlaceClick(place_id: String?)
}