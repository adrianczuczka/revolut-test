package com.example.revolut_test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.revolut_test.injection.RevolutTestApplication
import com.example.revolut_test.viewmodel.currencyfragment.CurrencyViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as RevolutTestApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val currencyFragment = CurrencyFragment.newInstance()
        supportFragmentManager
                .beginTransaction()
                .add(R.id.containerFrameLayout, currencyFragment)
                .commitNow()
    }
}