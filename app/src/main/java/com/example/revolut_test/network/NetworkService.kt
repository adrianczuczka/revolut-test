package com.example.revolut_test.network

import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {
    @GET("latest")
    fun getCurrencies(@Query("base") base: String)
}