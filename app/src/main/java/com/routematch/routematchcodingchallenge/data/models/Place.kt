package com.routematch.routematchcodingchallenge.data.models

import android.location.Location

data class Place(
        val id: String?,
        val place_id: String?,
        val name: String?,
        val rating: Number?,
        val price_level: Number?,
        val lat: Number?,
        val lng: Number?,
        val image_url: String?
) {}