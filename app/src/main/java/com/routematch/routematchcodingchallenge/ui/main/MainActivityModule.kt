package com.routematch.routematchcodingchallenge.ui.main

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
}