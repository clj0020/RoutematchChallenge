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

    @Inject
    internal lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): DispatchingAndroidInjector<Activity>? {
        return activityDispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this)

        AndroidNetworking.initialize(applicationContext)
    }
}