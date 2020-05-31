package com.example.revolut_test.injection

import android.app.Application
import com.example.revolut_test.MainActivity
import dagger.Component

@Component(modules = [NetworkModule::class])
interface ApplicationComponent {
    fun inject(mainActivity: MainActivity)
}

class RevolutTestApplication : Application() {
    val appComponent = DaggerApplicationComponent.create()
}