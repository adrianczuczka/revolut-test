package com.example.revolut_test.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.revolut_test.model.Currency
import com.example.revolut_test.model.CurrencyNetworkResponse
import com.example.revolut_test.network.NetworkService
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val networkService: NetworkService
) {
    private val _liveData = MutableLiveData<List<Currency>>()
    val liveData: LiveData<List<Currency>> = _liveData

    fun getCurrencies(base: String): Observable<CurrencyNetworkResponse> = networkService.getCurrencies(base)
}