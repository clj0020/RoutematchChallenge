package com.routematch.routematchcodingchallenge.ui.base

import io.reactivex.disposables.CompositeDisposable
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import com.routematch.routematchcodingchallenge.data.DataManager
import com.routematch.routematchcodingchallenge.util.SchedulerProvider

/**
 * Created by clj00 on 3/2/2018.
 */
abstract class BaseViewModel<N>(val dataManager: DataManager,
                                val schedulerProvider: SchedulerProvider) : ViewModel() {

    val isLoading = ObservableBoolean(false)

    val compositeDisposable: CompositeDisposable

    var navigator: N ?= null

    init {
        this.compositeDisposable = CompositeDisposable()
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun setIsLoading(isLoading: Boolean) {
        this.isLoading.set(isLoading)
    }
}