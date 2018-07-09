package com.routematch.routematchcodingchallenge.ui.place

import android.arch.lifecycle.MutableLiveData
import com.routematch.routematchcodingchallenge.ui.base.BaseViewModel
import com.routematch.routematchcodingchallenge.data.DataManager
import com.routematch.routematchcodingchallenge.data.models.Place
import com.routematch.routematchcodingchallenge.util.SchedulerProvider

class PlaceViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider): BaseViewModel<PlaceNavigator>(dataManager, schedulerProvider) {

    val placeLiveData: MutableLiveData<Place> = MutableLiveData<Place>()

    // Fetches place details using the data manager and adds to composite disposable.
    fun fetchPlace(place_id: String) {
        setIsLoading(true)
        compositeDisposable.add(
                dataManager
                        .getPlaceDetails(place_id = place_id)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe({place ->
                            if (place != null) {
                                // Set live data.
                                placeLiveData.setValue(place)
                            }
                            setIsLoading(false)
                        }, {throwable: Throwable ->
                            setIsLoading(false)
                            navigator?.handleError(throwable)
                        })
        )
    }

}