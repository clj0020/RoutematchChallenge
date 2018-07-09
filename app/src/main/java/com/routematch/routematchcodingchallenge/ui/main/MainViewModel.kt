package com.routematch.routematchcodingchallenge.ui.main

import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableArrayList
import android.databinding.ObservableField
import android.databinding.ObservableList
import android.location.Location
import android.util.Log
import com.routematch.routematchcodingchallenge.data.DataManager
import com.routematch.routematchcodingchallenge.data.models.Place
import com.routematch.routematchcodingchallenge.ui.base.BaseViewModel
import com.routematch.routematchcodingchallenge.ui.main.MainActivity.Companion.TAG
import com.routematch.routematchcodingchallenge.util.SchedulerProvider

/** The ViewModel for the MainActivity. Fetches nearby places and posts the results to the observing MainActivity. **/
class MainViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider): BaseViewModel<MainNavigator>(dataManager, schedulerProvider) {

    val nearbyPlacesObservableList: ObservableList<Place>?= ObservableArrayList<Place>()
    val nearbyPlacesListLiveData: MutableLiveData<List<Place>> = MutableLiveData<List<Place>>()

    val appVersion = ObservableField<String>()


    // Updates version of app.
    fun updateAppVersion(version: String) {
        appVersion.set(version)
    }

    // Fetches nearby places using the data manager and adds to composite disposable.
    fun fetchNearbyPlaces(location: Location, radius: Int, type: String) {
        setIsLoading(true)
        compositeDisposable.add(
                dataManager
                        .getNearbyPlaces(
                                location = location,
                                radius = radius,
                                type = type
                        )
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe({nearbyPlaces ->
                            if (nearbyPlaces != null) {
                                // Set live data.
                                nearbyPlacesListLiveData.setValue(nearbyPlaces)
                            }
                            setIsLoading(false)
                        }, {throwable: Throwable ->
                            setIsLoading(false)
                            navigator?.handleError(throwable)
                        })
        )
    }

    // Adds Place Items to the Observable List.
    fun addPlaceItemsToList(nearbyPlaces: List<Place>) {
        nearbyPlacesObservableList?.clear()
        nearbyPlacesObservableList?.addAll(nearbyPlaces)
    }

}