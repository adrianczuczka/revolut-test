package com.example.revolut_test.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.revolut_test.model.Currency
import com.example.revolut_test.model.CurrencyNetworkResponse
import com.example.revolut_test.network.NetworkService
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val networkService: NetworkService
) {
    private val _currenciesLiveData = MutableLiveData<List<Currency>>()
    val currenciesLiveData: LiveData<List<Currency>> = _currenciesLiveData

    fun getCurrencies(base: String): Flowable<CurrencyNetworkResponse> = networkService.getCurrencies(base)
}