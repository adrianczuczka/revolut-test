package com.example.revolut_test.usecase

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit.SECONDS
import javax.inject.Inject

class IntervalUseCase @Inject constructor() {

    fun execute(intervalInSeconds: Long): Flowable<Long> = Flowable.interval(
            intervalInSeconds, SECONDS, AndroidSchedulers.mainThread()
    ).subscribeOn(Schedulers.io())
}