package com.example.countries.di

import com.example.countries.model.CountryService
import com.example.countries.viewmodel.CountryListViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject(service: CountryService)
    fun inject(countryListViewModel: CountryListViewModel)
}