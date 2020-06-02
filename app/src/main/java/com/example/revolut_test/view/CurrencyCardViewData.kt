package com.example.revolut_test.view

import androidx.annotation.DrawableRes

data class CurrencyCardViewData(
    val title: String,
    val subtitle: String,
    @DrawableRes
    val flag: Int
)