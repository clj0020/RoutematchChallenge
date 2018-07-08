package com.routematch.routematchcodingchallenge.ui.main

import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableField
import android.location.Location
import com.google.android.gms.maps.model.LatLng
import com.routematch.routematchcodingchallenge.data.DataManager
import com.routematch.routematchcodingchallenge.ui.base.BaseViewModel
import com.routematch.routematchcodingchallenge.util.LocationHelperLiveData
import com.routematch.routematchcodingchallenge.util.SchedulerProvider


class MainViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider): BaseViewModel<MainNavigator>(dataManager, schedulerProvider) {

    val appVersion = ObservableField<String>()

    fun updateAppVersion(version: String) {
        appVersion.set(version)
    }
}