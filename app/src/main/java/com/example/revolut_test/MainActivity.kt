package com.example.revolut_test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.revolut_test.injection.RevolutTestApplication

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as RevolutTestApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }
}