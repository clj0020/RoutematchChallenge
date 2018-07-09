package com.routematch.routematchcodingchallenge.util

/** This is an interface that we can use to listen to the lifecycle of Glide in a class and display loading progress bar. **/
interface ImageLoaderCallback {

    // called initially
    fun onImageLoading()

    // called when image has successfully loaded
    fun onImageReady()

    // called when image loading fails
    fun onImageLoadError()

}