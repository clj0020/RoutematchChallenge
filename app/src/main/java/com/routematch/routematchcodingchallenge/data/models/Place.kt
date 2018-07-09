package com.routematch.routematchcodingchallenge.data.models

import android.location.Location

/** The Custom Data Class for a Place. **/
data class Place(
        val id: String?,
        val place_id: String?,
        val name: String?,
        val rating: Number?,
        val price_level: Int?,
        val lat: Number?,
        val lng: Number?
) {
    // For fields that need to be set later.
    var photo_references: List<String>? = null
    var website: String? = null
    var formatted_address: String? = null
    var formatted_phone_number: String? = null
    var hours_of_operation: List<String>? = null
    var is_open: Boolean? = null
}