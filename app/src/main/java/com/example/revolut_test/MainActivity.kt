package com.example.revolut_test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.revolut_test.injection.RevolutTestApplication
import com.example.revolut_test.viewmodel.mainactivity.MainActivityViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as RevolutTestApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        viewModel.getCurrencies("EUR").observe(this, Observer {
        })
        val currencyFragment = CurrencyFragment.newInstance()
        supportFragmentManager
                .beginTransaction()
                .add(R.id.containerFrameLayout, currencyFragment)
                .commitNow()
    }
}