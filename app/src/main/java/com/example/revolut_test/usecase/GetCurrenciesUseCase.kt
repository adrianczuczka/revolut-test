package com.example.revolut_test.usecase

import com.example.revolut_test.model.Currency
import com.example.revolut_test.repository.NetworkRepository
import com.example.revolut_test.transformer.CurrencyNetworkResponseToCurrencyListTransformer
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class GetCurrenciesUseCase @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val currencyNetworkResponseToCurrencyListTransformer: CurrencyNetworkResponseToCurrencyListTransformer
) {
    fun execute(base: String): Flowable<List<Currency>> = networkRepository.getCurrencies(base).map {
        currencyNetworkResponseToCurrencyListTransformer.transform(it)
    }
}