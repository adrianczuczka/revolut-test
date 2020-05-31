package com.example.revolut_test.repository

import com.example.revolut_test.model.CurrencyNetworkResponse
import com.example.revolut_test.network.NetworkService
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val networkService: NetworkService
) {
    fun getCurrencies(base: String): Flowable<CurrencyNetworkResponse> = networkService.getCurrencies(base)
}