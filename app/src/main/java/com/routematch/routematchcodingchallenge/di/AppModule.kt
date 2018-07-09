package com.routematch.routematchcodingchallenge.di

import dagger.Provides
import javax.inject.Singleton
import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.routematch.routematchcodingchallenge.data.AppDataManager
import com.routematch.routematchcodingchallenge.data.DataManager
import com.routematch.routematchcodingchallenge.data.network.AppPlacesApiHelper
import com.routematch.routematchcodingchallenge.data.network.PlacesApiHelper
import com.routematch.routematchcodingchallenge.util.AppSchedulerProvider
import com.routematch.routematchcodingchallenge.util.SchedulerProvider
import dagger.Module

/**
 * This is for providing classes to the application using Dagger.
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

    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        return GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }

    @Provides
    @Singleton
    internal fun providePlacesApiHelper(appPlacesApiHelper: AppPlacesApiHelper): PlacesApiHelper {
        return appPlacesApiHelper
    }

}