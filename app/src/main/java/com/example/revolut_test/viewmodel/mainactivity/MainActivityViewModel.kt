package com.example.revolut_test.viewmodel.mainactivity

import androidx.lifecycle.ViewModel
import com.example.revolut_test.usecase.GetCurrenciesUseCase
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val getCurrenciesUseCase: GetCurrenciesUseCase
) : ViewModel() {

    fun getCurrencies(base: String) = getCurrenciesUseCase.execute(base)

}