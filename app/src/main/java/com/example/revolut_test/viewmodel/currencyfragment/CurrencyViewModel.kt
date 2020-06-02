package com.example.revolut_test.viewmodel.currencyfragment

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.example.revolut_test.usecase.GetCurrenciesUseCase
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class CurrencyViewModel @Inject constructor(
    private val getCurrenciesUseCase: GetCurrenciesUseCase
) : ViewModel() {

    fun getCurrencies(base: String) = LiveDataReactiveStreams.fromPublisher(getCurrenciesUseCase.execute(base).subscribeOn(Schedulers.io()))

}