package com.routematch.routematchcodingchallenge.ui.main

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.routematch.routematchcodingchallenge.data.models.Place
import com.routematch.routematchcodingchallenge.ui.base.BaseViewHolder
import com.routematch.routematchcodingchallenge.databinding.ItemPlaceViewBinding
import com.routematch.routematchcodingchallenge.databinding.ItemPlaceEmptyViewBinding
import com.routematch.routematchcodingchallenge.ui.main.MainActivity.Companion.TAG
import com.routematch.routematchcodingchallenge.util.ImageLoaderCallback

/**
 * This RecyclerView Adapter handles a list of Nearby Place items and displays them. Also includes interfaces for interacting with the items.
 */
class NearbyPlacesListAdapter(val mPlacesList: MutableList<Place>?) : RecyclerView.Adapter<BaseViewHolder>() {

    private var mListener: NearbyPlacesAdapterListener? = null

    // Gets the number of places in the list.
    override fun getItemCount(): Int {
        return if (mPlacesList != null && mPlacesList.size > 0) {
            mPlacesList.size
        } else {
            1
        }
    }

    // Changes View Types based on if there are any places to display or not.
    override fun getItemViewType(position: Int): Int {
        return if (mPlacesList != null && !mPlacesList.isEmpty()) {
            // if there are places display them.
            VIEW_TYPE_NORMAL
        } else {
            // if there are no places display the empty view.
            VIEW_TYPE_EMPTY
        }
    }

    // Called when the ViewHolder is binded.
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    // Chooses the correct view type when the ViewHolder is created.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        when (viewType) {
            // If the View Type is normal, inflate the place item view and return its viewholder.
            VIEW_TYPE_NORMAL -> {
                val placeViewBinding = ItemPlaceViewBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                return PlaceViewHolder(placeViewBinding)
            }
            // If the View Type is empty, inflate the empty place item view and return its viewholder.
            VIEW_TYPE_EMPTY -> {
                val emptyViewBinding = ItemPlaceEmptyViewBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                return EmptyViewHolder(emptyViewBinding)
            }
            // Default to the empty view type otherwise.
            else -> {
                val emptyViewBinding = ItemPlaceEmptyViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return EmptyViewHolder(emptyViewBinding)
            }
        }
    }

    // Adds items to the list
    fun addItems(nearbyPlacesList: List<Place>) {
        mPlacesList!!.addAll(nearbyPlacesList) // add the list to the Places list.
        notifyDataSetChanged() // Let the view know that the dataset has been updated.
    }

    // Add a single item to the list.
    fun addItem(place: Place) {
        mPlacesList!!.add(place)
        notifyDataSetChanged()
    }

    // Clears items from the list.
    fun clearItems() {
        mPlacesList!!.clear()
    }

    // Sets the listener for the adapter.
    fun setListener(listener: NearbyPlacesAdapterListener) {
        this.mListener = listener
    }


    // Provides an interface for the NearbyPlacesAdapter.
    interface NearbyPlacesAdapterListener {

    }

    // The Place Item ViewHolder class. Implements ImageLoaderCallback.
    inner class PlaceViewHolder(private val mBinding: ItemPlaceViewBinding) : BaseViewHolder(mBinding.getRoot()), PlaceItemViewModel.PlaceItemViewModelListener, ImageLoaderCallback {

        private var mPlaceItemViewModel: PlaceItemViewModel? = null

        // When the item is bound, set its viewmodel, imageloadercallback, and execute all other bindings.
        override fun onBind(position: Int) {
            val place = mPlacesList!![position]
            mPlaceItemViewModel = PlaceItemViewModel(place, this)
            mBinding.setViewModel(mPlaceItemViewModel)
            mBinding.setImageLoaderCallback(this)
            mBinding.executePendingBindings()
        }

        // when the item is clicked, call the MainActivity method for displaying place details.
        override fun onItemClick(place_id: String?) {
            (itemView.context as MainActivity).onPlaceClick(place_id)
        }

        // When the image loading process begins.
        override fun onImageLoading() {
            // Make loading progress bar visible
            mBinding.viewModel?.setIsLoading(true)
        }

        // When the image has been successfully loaded.
        override fun onImageReady() {
            // Hide loading progress bar
            mBinding.viewModel?.setIsLoading(false)
        }

        // When the image loading process encounters an error.
        override fun onImageLoadError() {
            // Hide loading progress bar
            mBinding.viewModel?.setIsLoading(false)

            // Display error
            Log.e(TAG, "Error loading image.")
            (itemView.context as MainActivity).handleError(Throwable(message = "Problem encountered with loading place image.", cause = Exception()))
        }
    }

    // The Empty Place Item ViewHolder class.
    inner class EmptyViewHolder(private val mBinding: ItemPlaceEmptyViewBinding) : BaseViewHolder(mBinding.getRoot()), PlaceEmptyItemViewModel.PlaceEmptyItemViewModelListener {

        override fun onBind(position: Int) {
            val emptyItemViewModel = PlaceEmptyItemViewModel(this)
            mBinding.setViewModel(emptyItemViewModel)
        }

    }

    companion object {
        val VIEW_TYPE_EMPTY = 0
        val VIEW_TYPE_NORMAL = 1
    }
}