package com.routematch.routematchcodingchallenge.ui.place

import com.routematch.routematchcodingchallenge.data.DataManager
import com.routematch.routematchcodingchallenge.util.SchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class PlaceActivityModule {

    @Provides
    internal fun providePlaceViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider): PlaceViewModel {
        return PlaceViewModel(dataManager, schedulerProvider)
    }

}