package com.routematch.routematchcodingchallenge.util

import io.reactivex.Scheduler

/** Interface for scheduler provider. For use in choosing threads. **/
interface SchedulerProvider {

    fun computation(): Scheduler

    fun io(): Scheduler

    fun ui(): Scheduler
}