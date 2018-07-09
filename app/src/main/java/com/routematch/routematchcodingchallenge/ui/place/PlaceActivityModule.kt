package com.routematch.routematchcodingchallenge.ui.place

import com.routematch.routematchcodingchallenge.data.DataManager
import com.routematch.routematchcodingchallenge.util.SchedulerProvider
import dagger.Module
import dagger.Provides

/**
 * This is the PlaceActivityModule that allows the PlaceActivity to inject the ViewModel.
 */
@Module
class PlaceActivityModule {

    @Provides
    internal fun providePlaceViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider): PlaceViewModel {
        return PlaceViewModel(dataManager, schedulerProvider)
    }

}