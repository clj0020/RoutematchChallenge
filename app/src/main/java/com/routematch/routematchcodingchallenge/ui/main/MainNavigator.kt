package com.routematch.routematchcodingchallenge.ui.main

import com.routematch.routematchcodingchallenge.data.models.Place

/** An interface that allows the MainActivity and its ViewHolder to communicate. **/
interface MainNavigator {

    fun handleError(throwable: Throwable)

    fun updateNearbyPlacesList(nearbyPlaces: List<Place>)

    fun onPlaceClick(place_id: String?)
}