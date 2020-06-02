package com.example.revolut_test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blongho.country_data.World
import com.example.revolut_test.injection.RevolutTestApplication

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as RevolutTestApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        World.init(applicationContext)
        val currencyFragment = CurrencyFragment.newInstance()
        supportFragmentManager
                .beginTransaction()
                .add(R.id.containerFrameLayout, currencyFragment)
                .commitNow()
    }
}