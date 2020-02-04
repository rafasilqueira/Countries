package com.example.countries.model

import com.example.countries.dependencyInjection.DaggerApiComponent
import javax.inject.Inject


class CountryService {

    @Inject
    lateinit var countriesAPI: CountriesAPI

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getCountries() = countriesAPI.getCountries()

}