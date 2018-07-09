package com.routematch.routematchcodingchallenge.util

import android.databinding.BindingAdapter
import android.databinding.InverseBindingAdapter
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.routematch.routematchcodingchallenge.data.models.Place
import com.routematch.routematchcodingchallenge.ui.main.NearbyPlacesListAdapter

/** A very important class that defines different BindingAdapters for views. **/
object BindingUtils {

    // Binds the recyclerview adapter to the MainActivity view
    @JvmStatic
    @BindingAdapter("adapter")
    fun addNearbyPlacesItems(recyclerView: RecyclerView, nearbyPlaces: List<Place>) {
        val adapter = recyclerView.adapter as NearbyPlacesListAdapter?
        if (adapter != null) {
            adapter.clearItems()
            adapter.addItems(nearbyPlaces)
        }
    }

    // Sets the image of the place using Glide into an imageview. Requires the ImageLoaderCallback to be implemented in the view's parent activity, fragment, or viewholder.
    @JvmStatic
    @BindingAdapter(value = ["imageUrl", "imageCallback"], requireAll = false)
    fun setImageUrl(imageView: ImageView, imageUrl: String, imageCallback: ImageLoaderCallback) {
        // Show loading spinner at the beginning.
        imageCallback.onImageLoading()
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .listener(object : RequestListener<Drawable?> {

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: com.bumptech.glide.request.target.Target<Drawable?>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        // call to the image load ready event.
                        imageCallback.onImageReady()
                        return false
                    }

                    override fun onLoadFailed(e: GlideException?, model: Any?, target: com.bumptech.glide.request.target.Target<Drawable?>?, isFirstResource: Boolean): Boolean {
                        // call to the image load error event
                        imageCallback.onImageLoadError()
                        return false
                    }
                })
                .into(imageView)
    }

    // Sets a textview's text if the variable is a number.
    @JvmStatic
    @BindingAdapter("android:text")
    fun setNumber(view: TextView, value: Number?) {
        if (value == null) view.setText("")
        else view.setText(value.toString())
    }

    // Gets a number from the textview's text.
    @JvmStatic
    @InverseBindingAdapter(attribute = "android:text")
    fun getNumber(view: TextView): Number {
        val num = view.getText().toString()
        if(num.isEmpty()) return 0
        try {
            return Integer.parseInt(num)
        }
        catch (e: NumberFormatException) {
            return 0
        }
    }

}// This class is not publicly instantiable