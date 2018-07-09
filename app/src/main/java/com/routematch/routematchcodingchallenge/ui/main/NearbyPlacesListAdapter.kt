package com.routematch.routematchcodingchallenge.ui.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.routematch.routematchcodingchallenge.data.models.Place
import com.routematch.routematchcodingchallenge.ui.base.BaseViewHolder
import com.routematch.routematchcodingchallenge.databinding.ItemPlaceViewBinding
import com.routematch.routematchcodingchallenge.databinding.ItemPlaceEmptyViewBinding

class NearbyPlacesListAdapter(val mPlacesList: MutableList<Place>?) : RecyclerView.Adapter<BaseViewHolder>() {

    private var mListener: NearbyPlacesAdapterListener? = null

    override fun getItemCount(): Int {
        return if (mPlacesList != null && mPlacesList.size > 0) {
            mPlacesList.size
        } else {
            1
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (mPlacesList != null && !mPlacesList.isEmpty()) {
            VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_EMPTY
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        when (viewType) {
            VIEW_TYPE_NORMAL -> {
                val placeViewBinding = ItemPlaceViewBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                return PlaceViewHolder(placeViewBinding)
            }
            VIEW_TYPE_EMPTY -> {
                val emptyViewBinding = ItemPlaceEmptyViewBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                return EmptyViewHolder(emptyViewBinding)
            }
            else -> {
                val emptyViewBinding = ItemPlaceEmptyViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return EmptyViewHolder(emptyViewBinding)
            }
        }
    }

    fun addItems(nearbyPlacesList: List<Place>) {
        mPlacesList!!.addAll(nearbyPlacesList)
        notifyDataSetChanged()
    }

    fun addItem(place: Place) {
        mPlacesList!!.add(place)
        notifyDataSetChanged()
    }

    fun clearItems() {
        mPlacesList!!.clear()
    }

    fun setListener(listener: NearbyPlacesAdapterListener) {
        this.mListener = listener
    }


    interface NearbyPlacesAdapterListener {

    }

    inner class PlaceViewHolder(private val mBinding: ItemPlaceViewBinding) : BaseViewHolder(mBinding.getRoot()), PlaceItemViewModel.PlaceItemViewModelListener {

        private var mPlaceItemViewModel: PlaceItemViewModel? = null

        override fun onBind(position: Int) {
            val place = mPlacesList!![position]
            mPlaceItemViewModel = PlaceItemViewModel(place, this)
            mBinding.setViewModel(mPlaceItemViewModel)

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mBinding.executePendingBindings()
        }

        override fun onItemClick(place_id: String?) {
            (itemView.context as MainActivity).onPlaceClick(place_id)
        }
    }

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