package com.example.revolut_test.network

import com.example.revolut_test.model.CurrencyNetworkResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {
    @GET("latest")
    fun getCurrencies(@Query("base") base: String): Observable<CurrencyNetworkResponse>
}