package com.routematch.routematchcodingchallenge.ui.main

import android.databinding.ObservableField
import com.routematch.routematchcodingchallenge.data.models.Place

class PlaceItemViewModel(private val mPlace: Place, val mListener: PlaceItemViewModelListener) {

    val name: ObservableField<String?>
    val rating: ObservableField<Number?>
    val price_level: ObservableField<Int?>

    init {
        name = ObservableField(mPlace.name)
        rating = ObservableField(mPlace.rating)
        price_level = ObservableField(mPlace.price_level)
    }

    fun onItemClick() {
        mListener.onItemClick(mPlace.place_id)
    }

    interface PlaceItemViewModelListener {
        fun onItemClick(place_id: String?)
    }
}