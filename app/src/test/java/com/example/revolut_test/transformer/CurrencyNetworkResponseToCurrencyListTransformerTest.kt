package com.example.revolut_test.transformer

import com.example.revolut_test.model.Currency
import com.example.revolut_test.model.CurrencyNetworkResponse
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.Before
import org.junit.Test

private const val FIRST_KEY = "EUR"
private const val SECOND_KEY = "USD"
private const val THIRD_KEY = "GBP"
private const val FIRST_VALUE = 1.0
private const val SECOND_VALUE = 2.0
private const val THIRD_VALUE = 3.0

class CurrencyNetworkResponseToCurrencyListTransformerTest {

    lateinit var transformer: CurrencyNetworkResponseToCurrencyListTransformer

    private val response: CurrencyNetworkResponse = mock {
        on { rates } doReturn mapOf(
                FIRST_KEY to FIRST_VALUE,
                SECOND_KEY to SECOND_VALUE,
                THIRD_KEY to THIRD_VALUE
        )
    }

    @Before
    fun `set up`() {
        transformer = CurrencyNetworkResponseToCurrencyListTransformer()
    }

    @Test
    fun `transform - given currency network response, should return list of currencies`() {
        val currencies = listOf(
                Currency(FIRST_KEY, FIRST_VALUE),
                Currency(SECOND_KEY, SECOND_VALUE),
                Currency(THIRD_KEY, THIRD_VALUE)
        )

        val actual = transformer.transform(response)

        assertThat(actual).isEqualTo(currencies)
    }
}