package com.example.revolut_test.viewmodel.mainactivity

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.revolut_test.model.Currency
import com.example.revolut_test.usecase.GetCurrenciesUseCase
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import io.reactivex.rxjava3.core.Flowable
import org.junit.Before
import org.junit.Rule
import org.junit.Test

private const val TEST_BASE = "EUR"
private const val FIRST_KEY = "EUR"
private const val SECOND_KEY = "USD"
private const val THIRD_KEY = "GBP"
private const val FIRST_VALUE = 1.0
private const val SECOND_VALUE = 2.0
private const val THIRD_VALUE = 3.0

class MainActivityViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainActivityViewModel

    private val observer: Observer<List<Currency>> = mock()

    private val currencies = listOf(
            Currency(FIRST_KEY, FIRST_VALUE),
            Currency(SECOND_KEY, SECOND_VALUE),
            Currency(THIRD_KEY, THIRD_VALUE)
    )

    private val getCurrenciesUseCase: GetCurrenciesUseCase = mock {
        on { execute(TEST_BASE) } doReturn Flowable.just(currencies)
    }

    @Before
    fun `set up`() {
        viewModel = MainActivityViewModel(getCurrenciesUseCase)
    }

    @Test
    fun `getCurrencies - should return liveData with currency list`() {
        viewModel.getCurrencies(TEST_BASE).observeForever(observer)

        verify(observer).onChanged(currencies)
        verify(getCurrenciesUseCase).execute(TEST_BASE)
        verifyNoMoreInteractions(observer, getCurrenciesUseCase)
    }
}