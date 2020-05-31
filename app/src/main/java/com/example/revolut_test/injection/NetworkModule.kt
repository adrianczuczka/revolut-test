package com.example.revolut_test.injection

import com.example.revolut_test.network.NetworkService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

private const val BASE_URL = "https://hiring.revolut.codes/api/android/"

@Module
class NetworkModule {

    @Provides
    fun provideNetworkService(): NetworkService {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .build()
                .create(NetworkService::class.java)
    }
}
