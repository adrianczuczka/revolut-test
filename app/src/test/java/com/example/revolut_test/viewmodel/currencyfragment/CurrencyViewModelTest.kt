package com.example.revolut_test.viewmodel.currencyfragment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.revolut_test.model.Currency
import com.example.revolut_test.transformer.CurrencyToCurrencyCardViewDataTransformer
import com.example.revolut_test.usecase.GetCurrenciesUseCase
import com.example.revolut_test.usecase.IntervalUseCase
import com.example.revolut_test.view.CurrencyCardViewData
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import io.reactivex.rxjava3.core.Flowable
import org.junit.Before
import org.junit.Rule
import org.junit.Test

private const val TEST_BASE = "EUR"
private const val FIRST_NAME = "EUR"
private const val SECOND_NAME = "USD"
private const val THIRD_NAME = "GBP"
private const val FIRST_RATE = 1.0
private const val SECOND_RATE = 2.0
private const val THIRD_RATE = 3.0

class CurrencyViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: CurrencyViewModel

    private val observer: Observer<List<Currency>> = mock()
    private val firstCurrency: Currency = mock {
        on { name } doReturn FIRST_NAME
        on { rate } doReturn FIRST_RATE
    }

    private val currencies = listOf(
            firstCurrency,
            Currency(SECOND_NAME, SECOND_RATE),
            Currency(THIRD_NAME, THIRD_RATE)
    )

    private val currencyData: CurrencyCardViewData = mock()

    private val getCurrenciesUseCase: GetCurrenciesUseCase = mock {
        on { execute(TEST_BASE) } doReturn Flowable.just(currencies)
    }

    private val currencyToCurrencyCardViewDataTransformer: CurrencyToCurrencyCardViewDataTransformer = mock {
        on { transform(firstCurrency) } doReturn currencyData
    }

    private val intervalUseCase: IntervalUseCase = mock()

    @Before
    fun `set up`() {
        viewModel = CurrencyViewModel(getCurrenciesUseCase, currencyToCurrencyCardViewDataTransformer, intervalUseCase)
    }

    @Test
    fun `getCurrencies - should return liveData with currency list`() {
        viewModel.getCurrencies(TEST_BASE).observeForever(observer)

        verify(observer).onChanged(currencies)
        verify(getCurrenciesUseCase).execute(TEST_BASE)
        verifyNoMoreInteractions(observer, getCurrenciesUseCase)
        verifyZeroInteractions(currencyToCurrencyCardViewDataTransformer)
    }

    @Test
    fun `transformCurrencies - should delegate to transformer`() {
        val actual = viewModel.transformCurrencies(firstCurrency)

        assertThat(actual).isEqualTo(currencyData)
        verify(currencyToCurrencyCardViewDataTransformer).transform(firstCurrency)
    }
}