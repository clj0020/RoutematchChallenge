package com.routematch.routematchcodingchallenge.data

import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton
import com.routematch.routematchcodingchallenge.util.SchedulerProvider


/**
 * Created by clj00 on 3/2/2018.
 */
@Singleton
class AppDataManager @Inject constructor(private val mContext: Context,
                                         private val schedulerProvider: SchedulerProvider) : DataManager {

}