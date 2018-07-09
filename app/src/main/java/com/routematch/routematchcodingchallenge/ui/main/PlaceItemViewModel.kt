package com.routematch.routematchcodingchallenge.ui.main

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.routematch.routematchcodingchallenge.data.models.Place

/** The Place Item ViewModel. For data binding in the item's layout as well as providing an interface for reacting to item click. **/
class PlaceItemViewModel(private val mPlace: Place, val mListener: PlaceItemViewModelListener) {

    val isLoading = ObservableBoolean(false)
    val name: ObservableField<String?>
    val rating: ObservableField<Number?>
    val price_level: ObservableField<Int?>
    val photo_references: ObservableField<List<String>?>

    init {
        name = ObservableField(mPlace.name)
        rating = ObservableField(mPlace.rating)
        price_level = ObservableField(mPlace.price_level)
        photo_references = ObservableField(mPlace.photo_references)
    }

    // When this item is clicked, call the listener's click method.
    fun onItemClick() {
        mListener.onItemClick(mPlace.place_id)
    }

    // Sets the loading observable in which the layout reacts by hiding or showing the loading progress spinner in the item.
    fun setIsLoading(isLoading: Boolean) {
        this.isLoading.set(isLoading)
    }

    interface PlaceItemViewModelListener {
        fun onItemClick(place_id: String?)
    }
}