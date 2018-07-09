package com.routematch.routematchcodingchallenge.ui.base

import android.support.v7.widget.RecyclerView
import android.view.View


/**
 * An abstract class for RecyclerView ViewHolders.
 */
abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun onBind(position: Int)
}