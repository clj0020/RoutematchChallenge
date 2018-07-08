package com.routematch.routematchcodingchallenge.di

import dagger.Provides
import javax.inject.Singleton
import android.app.Application
import android.content.Context
import com.routematch.routematchcodingchallenge.data.AppDataManager
import com.routematch.routematchcodingchallenge.data.DataManager
import com.routematch.routematchcodingchallenge.util.AppSchedulerProvider
import com.routematch.routematchcodingchallenge.util.SchedulerProvider
import dagger.Module

/**
 * Created by clj00 on 3/2/2018.
 */
@Module
class AppModule {


    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    internal fun provideDataManager(appDataManager: AppDataManager): DataManager {
        return appDataManager
    }

    @Provides
    internal fun provideSchedulerProvider(): SchedulerProvider {
        return AppSchedulerProvider()
    }

}