package com.routematch.routematchcodingchallenge.util

import android.content.Context
import android.content.res.Resources
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import com.routematch.routematchcodingchallenge.R

object ViewUtils {

    fun dpToPx(dp: Float): Int {
        val density = Resources.getSystem().getDisplayMetrics().density
        return Math.round(dp * density)
    }

    fun pxToDp(px: Float): Float {
        val densityDpi = Resources.getSystem().getDisplayMetrics().densityDpi
        return px / (densityDpi / 160f)
    }

    @JvmStatic
    fun formatRating(rating: Number?): String? {
        if (rating == null) {
            return null
        }
        return rating.toString() + " / 5.0 rating"
    }


    @JvmStatic
    fun formatPriceLevel(price_level: Int?): String? {
        if (price_level == null) {
            return null
        }
        var formatted_string: String = ""
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

}// This class is not publicly instantiable