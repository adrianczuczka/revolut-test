package com.example.revolut_test.model

data class CurrencyNetworkResponse(
    val baseCurrency: String,
    val rates: Map<String, Double>
)