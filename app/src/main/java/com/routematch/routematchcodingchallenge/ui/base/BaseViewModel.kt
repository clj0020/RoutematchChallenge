package com.routematch.routematchcodingchallenge.ui.base

import io.reactivex.disposables.CompositeDisposable
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import com.routematch.routematchcodingchallenge.data.DataManager
import com.routematch.routematchcodingchallenge.util.SchedulerProvider

/**
 * This is the BaseViewModel class. Sets up Composite Disposable and contains a loading observable that can be reacted to by host activities or fragments.
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