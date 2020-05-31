package com.example.revolut_test.injection

import com.example.revolut_test.network.NetworkService
import dagger.Module
import dagger.Provides
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://hiring.revolut.codes/api/android/"

@Module
class NetworkModule {

    @Provides
    fun provideNetworkService(): NetworkService {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(NetworkService::class.java)
    }
}
