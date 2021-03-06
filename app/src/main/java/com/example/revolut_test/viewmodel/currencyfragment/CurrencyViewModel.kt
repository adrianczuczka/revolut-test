package com.example.revolut_test.viewmodel.currencyfragment

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.example.revolut_test.model.Currency
import com.example.revolut_test.transformer.CurrencyToCurrencyCardViewDataTransformer
import com.example.revolut_test.usecase.GetCurrenciesUseCase
import com.example.revolut_test.usecase.IntervalUseCase
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

private const val INTERVAL_IN_SECONDS = 1L

class CurrencyViewModel @Inject constructor(
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    private val currencyToCurrencyCardViewDataTransformer: CurrencyToCurrencyCardViewDataTransformer,
    private val intervalUseCase: IntervalUseCase
) : ViewModel() {

    fun getCurrencies(base: String) = LiveDataReactiveStreams.fromPublisher(
            intervalUseCase.execute(INTERVAL_IN_SECONDS).flatMap {
                getCurrenciesUseCase.execute(base).subscribeOn(Schedulers.io())
            }
    )

    fun transformCurrencies(currency: Currency) = currencyToCurrencyCardViewDataTransformer.transform(currency)
}