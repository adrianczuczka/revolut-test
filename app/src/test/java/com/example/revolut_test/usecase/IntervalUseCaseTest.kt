package com.example.revolut_test.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit.SECONDS

class TimerUseCaseTest {

    private val testScheduler = TestScheduler()
    private val onSuccess: Consumer<Long> = mock()
    private val onError: Consumer<Throwable> = mock()

    private lateinit var useCase: IntervalUseCase

    @Before
    fun `set up`() {
        useCase = IntervalUseCase(testScheduler, testScheduler)
    }

    @Test
    fun `execute - use case calls onSuccess after 5 seconds`() {
        val interval = 5L

        useCase.execute(interval)
                .subscribe(onSuccess, onError)

        testScheduler.advanceTimeBy(interval, SECONDS)

        verify(onSuccess).accept(0L)
        verifyNoMoreInteractions(onSuccess)
        verifyZeroInteractions(onError)
    }

    @Test
    fun `execute - use case does nothing before 5 seconds`() {
        val interval = 5L

        useCase.execute(interval)
                .subscribe(onSuccess, onError)

        testScheduler.advanceTimeBy(4, SECONDS)

        verifyZeroInteractions(onSuccess, onError)
    }
}