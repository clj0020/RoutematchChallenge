package com.routematch.routematchcodingchallenge.ui.main

import android.support.v7.widget.LinearLayoutManager
import com.routematch.routematchcodingchallenge.data.DataManager
import com.routematch.routematchcodingchallenge.util.SchedulerProvider
import dagger.Provides
import dagger.Module


/**
 * Created by clj00 on 3/2/2018.
 */
@Module
class MainActivityModule {

    @Provides
    internal fun provideMainViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider): MainViewModel {
        return MainViewModel(dataManager, schedulerProvider)
    }

    @Provides
    internal fun provideNearbyPlacesListAdapter(): NearbyPlacesListAdapter {
        return NearbyPlacesListAdapter(ArrayList())
    }

    @Provides
    internal fun provideLinearLayoutManager(activity: MainActivity): LinearLayoutManager {
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        return layoutManager
    }
}