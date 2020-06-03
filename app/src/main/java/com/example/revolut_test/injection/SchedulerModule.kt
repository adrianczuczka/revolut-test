package com.example.revolut_test.injection

import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Named

const val IO = "io"
const val MAIN_THREAD = "main_thread"

@Module
class SchedulerModule {

    @Named(IO)
    @Provides
    fun providesIOScheduler(): Scheduler {
        return Schedulers.io()
    }

    @Named(MAIN_THREAD)
    @Provides
    fun providesMainThreadScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}
