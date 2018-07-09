package com.routematch.routematchcodingchallenge.ui.place

/** An interface that allows the PlaceActivity and its ViewHolder to communicate. **/
interface PlaceNavigator {

    fun handleError(throwable: Throwable)

}