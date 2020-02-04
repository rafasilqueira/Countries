package com.example.countries.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.countries.di.DaggerApiComponent
import com.example.countries.model.Country
import com.example.countries.model.CountryService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CountryListViewModel : ViewModel() {

    @Inject
    lateinit var countryService: CountryService

    init {
        DaggerApiComponent.create().inject(this)
    }

    private val disposable = CompositeDisposable()

    val countries = MutableLiveData<MutableList<Country>>()
    val isError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refreshData() {
        fetchCountries()
    }

    private fun fetchCountries() {
        loading.value = true

        disposable.add(
            countryService.getCountries()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MutableList<Country>>() {
                    override fun onSuccess(value: MutableList<Country>?) {
                        countries.value = value
                        sucessResult()
                    }

                    override fun onError(e: Throwable?) {
                        errorResult()
                    }
                })

        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    private fun sucessResult() {
        isError.value = false
        loading.value = false
    }

    private fun errorResult() {
        isError.value = true
        loading.value = false
    }
}