package com.routematch.routematchcodingchallenge.di

import android.app.Application
import com.routematch.routematchcodingchallenge.RoutematchApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * This class tells Dagger how to Inject the Application and its corresponding dependencies.
 */
@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AppModule::class,
    ActivityBuilder::class
])
interface AppComponent {

    fun inject(app: RoutematchApplication)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}