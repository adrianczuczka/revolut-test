package com.example.revolut_test.usecase

import com.example.revolut_test.injection.IO
import com.example.revolut_test.injection.MAIN_THREAD
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Scheduler
import java.util.concurrent.TimeUnit.SECONDS
import javax.inject.Inject
import javax.inject.Named

//To go further here, I would create a SchedulerHelper class to provide all these schedulers, and this class could then be subclassed into
//a testing version.
class IntervalUseCase @Inject constructor(
    @Named(IO)
    private val intervalScheduler: Scheduler,
    @Named(MAIN_THREAD)
    private val subscribeScheduler: Scheduler
) {

    fun execute(intervalInSeconds: Long): Flowable<Long> = Flowable.interval(
            intervalInSeconds, SECONDS, subscribeScheduler
    ).subscribeOn(intervalScheduler)
}