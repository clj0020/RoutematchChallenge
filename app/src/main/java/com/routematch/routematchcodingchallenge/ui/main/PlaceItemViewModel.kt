package com.routematch.routematchcodingchallenge.ui.main

import android.databinding.ObservableField
import com.routematch.routematchcodingchallenge.data.models.Place

class PlaceItemViewModel(private val mPlace: Place, val mListener: PlaceItemViewModelListener) {

    val name: ObservableField<String?>
    val rating: ObservableField<Number?>
    val price_level: ObservableField<Number?>
    val image_url: ObservableField<String?>

    init {
        name = ObservableField(mPlace.name)
        rating = ObservableField(mPlace.rating)
        price_level = ObservableField(mPlace.price_level)
        image_url = ObservableField(mPlace.image_url)
    }

    fun onItemClick() {
        mListener.onItemClick(mPlace.id)
    }

    interface PlaceItemViewModelListener {

        fun onItemClick(id: String?)
    }
}