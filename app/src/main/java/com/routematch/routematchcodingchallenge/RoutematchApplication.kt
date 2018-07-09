package com.routematch.routematchcodingchallenge

import android.app.Activity
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject
import dagger.android.HasActivityInjector
import android.app.Application
import com.androidnetworking.AndroidNetworking
import com.routematch.routematchcodingchallenge.di.DaggerAppComponent

/**
 * The main application file used for setting up libraries like dagger and fast networking.
 */
class RoutematchApplication : Application(), HasActivityInjector {

    // Injects the DispatchingAndroidInjector for activities.
    @Inject
    internal lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    // Gets the activity injector.
    override fun activityInjector(): DispatchingAndroidInjector<Activity>? {
        return activityDispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()

        // Builds the DaggerAppComponent
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this)

        // Initializes the networking library.
        AndroidNetworking.initialize(applicationContext)
    }
}