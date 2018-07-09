package com.routematch.routematchcodingchallenge.util

import android.content.Context
import android.content.res.Resources
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import com.routematch.routematchcodingchallenge.BuildConfig
import com.routematch.routematchcodingchallenge.R
import com.routematch.routematchcodingchallenge.data.network.PlacesApiEndPoint

/** Helper for various view related tasks. **/
object ViewUtils {

    // Converts DP to pixels.
    fun dpToPx(dp: Float): Int {
        val density = Resources.getSystem().getDisplayMetrics().density
        return Math.round(dp * density)
    }

    // Converts pixels to dp.
    fun pxToDp(px: Float): Float {
        val densityDpi = Resources.getSystem().getDisplayMetrics().densityDpi
        return px / (densityDpi / 160f)
    }

    // Formats the rating of a place.
    @JvmStatic
    fun formatRating(rating: Number?): String? {
        if (rating == null) {
            return null
        }
        return rating.toString() + " / 5.0 rating"
    }


    // Formats the price level of a place.
    @JvmStatic
    fun formatPriceLevel(price_level: Int?): String? {
        if (price_level == null) {
            return null
        }
        var formatted_string = ""
        when (price_level) {
            0 -> {
                formatted_string = "Free"
            }
            1 -> {
                formatted_string = "$"
            }
            2 -> {
                formatted_string = "$$"
            }
            3 -> {
                formatted_string = "$$$"
            }
            4 -> {
                formatted_string = "$$$$"
            }
        }
        return formatted_string
    }


    // Creates the url for a Places Photo API request. Glide calls the API this time.
    @JvmStatic
    fun createPlacesApiUrl(photo_references: List<String>?, maxwidth: Int): String? {
        if (photo_references == null || photo_references.size <= 0) {
            return null
        }
        return PlacesApiEndPoint.ENDPOINT_PLACE_PHOTO +
                "?maxwidth=" + maxwidth +
                "&photoreference=" + photo_references.get(0) +
                "&key=" + BuildConfig.GOOGLE_MAPS_API_KEY
    }

}// This class is not publicly instantiable