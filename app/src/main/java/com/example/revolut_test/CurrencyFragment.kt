package com.example.revolut_test

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.revolut_test.injection.RevolutTestApplication
import com.example.revolut_test.view.CurrencyAdapter
import javax.inject.Inject

class CurrencyFragment : Fragment() {

    @Inject
    lateinit var currencyAdapter: CurrencyAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val appContext = activity?.applicationContext as RevolutTestApplication
        appContext.appComponent.inject(this)
    }

    companion object {
        fun newInstance(): CurrencyFragment = CurrencyFragment()
    }
}