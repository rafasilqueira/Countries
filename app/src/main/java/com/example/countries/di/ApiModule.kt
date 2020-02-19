package com.example.countries.di

import com.example.countries.BuildConfig
import com.example.countries.model.CountriesAPI
import com.example.countries.model.CountryService
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


const val BASE_URL = "https://raw.githubusercontent.com"

@Module
class ApiModule {

    private val loggingInterceptor = HttpLoggingInterceptor()

    private val requestInterceptor = Interceptor { chain ->
        val newRequest = chain.request().newBuilder()
            //.addHeader("Authorization", "NA-DISP-AUTH $data")
            .addHeader("FullObject", "1")
            .addHeader("Timezone", TimeZone.getDefault().id)
            .build()
        chain.proceed(newRequest)
    }

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(requestInterceptor)
        .addInterceptor(loggingInterceptor).build()

    @Provides
    fun providesCountriesApi(): CountriesAPI {

        loggingInterceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(CountriesAPI::class.java)
    }

    @Provides
    fun providesCountriesService(): CountryService = CountryService()

}
