package com.example.revolut_test.usecase

import com.example.revolut_test.model.Currency
import com.example.revolut_test.model.CurrencyNetworkResponse
import com.example.revolut_test.repository.NetworkRepository
import com.example.revolut_test.transformer.CurrencyNetworkResponseToCurrencyListTransformer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.subscribers.TestSubscriber
import org.junit.Before
import org.junit.Test

private const val TEST_BASE = "EUR"
private const val FIRST_KEY = "EUR"
private const val SECOND_KEY = "USD"
private const val THIRD_KEY = "GBP"
private const val FIRST_VALUE = 1.0
private const val SECOND_VALUE = 2.0
private const val THIRD_VALUE = 3.0

class GetCurrenciesUseCaseTest {
    private lateinit var useCase: GetCurrenciesUseCase

    private val subscriber: TestSubscriber<List<Currency>> = TestSubscriber()
    private val response = CurrencyNetworkResponse(
            TEST_BASE,
            mapOf(
                    FIRST_KEY to FIRST_VALUE,
                    SECOND_KEY to SECOND_VALUE,
                    THIRD_KEY to THIRD_VALUE
            )
    )
    private val currencies = listOf(
            Currency(FIRST_KEY, FIRST_VALUE),
            Currency(SECOND_KEY, SECOND_VALUE),
            Currency(THIRD_KEY, THIRD_VALUE)
    )

    private val repository: NetworkRepository = mock {
        on { getCurrencies(TEST_BASE) } doReturn Flowable.just(response)
    }
    private val transformer: CurrencyNetworkResponseToCurrencyListTransformer = mock {
        on { transform(response) } doReturn currencies
    }

    @Before
    fun `set up`() {
        useCase = GetCurrenciesUseCase(repository, transformer)
    }

    @Test
    fun `execute - should return a list of currencies`() {
        useCase.execute(TEST_BASE).subscribe(subscriber)

        subscriber.assertValue(currencies)
        verify(repository).getCurrencies(TEST_BASE)
        verify(transformer).transform(response)
        verifyNoMoreInteractions(repository, transformer)
    }
}