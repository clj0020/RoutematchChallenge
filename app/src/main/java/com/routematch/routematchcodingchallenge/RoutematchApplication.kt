package com.routematch.routematchcodingchallenge

import android.app.Activity
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject
import dagger.android.HasActivityInjector
import android.app.Application
import com.routematch.routematchcodingchallenge.di.DaggerAppComponent

/**
 * Created by clj00 on 3/2/2018.
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
    }
}