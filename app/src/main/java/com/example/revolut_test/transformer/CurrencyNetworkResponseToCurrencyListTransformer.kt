package com.example.revolut_test.transformer

import com.example.revolut_test.model.Currency
import com.example.revolut_test.model.CurrencyNetworkResponse
import javax.inject.Inject

class CurrencyNetworkResponseToCurrencyListTransformer @Inject constructor() {
    fun transform(response: CurrencyNetworkResponse) =
            response.rates.map {
                Currency(it.key, it.value)
            }
}