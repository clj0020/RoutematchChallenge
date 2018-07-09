package com.routematch.routematchcodingchallenge.ui.main

import android.support.v7.widget.LinearLayoutManager
import com.routematch.routematchcodingchallenge.data.DataManager
import com.routematch.routematchcodingchallenge.util.SchedulerProvider
import dagger.Provides
import dagger.Module


/**
 * This is the MainActivityModule that allows the MainActivity to inject the ViewModel, RecyclerView Adapter, and the RecyclerView LinearLayout Manager.
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