package com.routematch.routematchcodingchallenge.di

import com.routematch.routematchcodingchallenge.ui.main.MainActivity
import com.routematch.routematchcodingchallenge.ui.main.MainActivityModule
import dagger.android.ContributesAndroidInjector
import dagger.Module


/**
 * Created by clj00 on 3/2/2018.
 */
@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = arrayOf(MainActivityModule::class))
    internal abstract fun bindMainActivity(): MainActivity

}