package com.routematch.routematchcodingchallenge.util

import android.databinding.BindingAdapter
import android.databinding.InverseBindingAdapter
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.routematch.routematchcodingchallenge.data.models.Place
import com.routematch.routematchcodingchallenge.ui.main.NearbyPlacesListAdapter

object BindingUtils {

    @JvmStatic
    @BindingAdapter("adapter")
    fun addNearbyPlacesItems(recyclerView: RecyclerView, nearbyPlaces: List<Place>) {
        val adapter = recyclerView.adapter as NearbyPlacesListAdapter?
        if (adapter != null) {
            adapter.clearItems()
            adapter.addItems(nearbyPlaces)
        }
    }

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun setImageUrl(imageView: ImageView, imageUrl: String) {
        val context = imageView.getContext()
        Glide.with(context).load(imageUrl).into(imageView)
    }

    @JvmStatic
    @BindingAdapter("android:text")
    fun setNumber(view: TextView, value: Number?) {
        if (value == null) view.setText("");
        else view.setText(value.toString());
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "android:text")
    fun getNumber(view: TextView): Number {
        val num = view.getText().toString()
        if(num.isEmpty()) return 0;
        try {
            return Integer.parseInt(num) as Number
        }
        catch (e: NumberFormatException) {
            return 0 as Number
        }
    }

}// This class is not publicly instantiable