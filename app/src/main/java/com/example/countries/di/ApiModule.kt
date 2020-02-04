package com.example.countries.di

import com.example.countries.model.CountriesAPI
import com.example.countries.model.CountryService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://raw.githubusercontent.com"

@Module
class ApiModule {

    @Provides
    fun providesCountriesApi(): CountriesAPI = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(CountriesAPI::class.java)

    @Provides
    fun providesCountriesService(): CountryService = CountryService()

}
