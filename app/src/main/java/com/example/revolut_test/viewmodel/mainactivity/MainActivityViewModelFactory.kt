package com.example.revolut_test.viewmodel.mainactivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Provider

class MainActivityViewModelFactory(private val provider: Provider<MainActivityViewModel>) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return provider.get() as T
    }
}