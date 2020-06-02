package com.example.revolut_test.viewmodel.currencyfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Provider

class CurrencyViewModelFactory(private val provider: Provider<CurrencyViewModel>) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return provider.get() as T
    }
}