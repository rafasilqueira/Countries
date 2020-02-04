package com.example.countries

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.countries.model.Country
import com.example.countries.model.CountryService
import com.example.countries.viewmodel.CountryListViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class CountriesListViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var countryService: CountryService

    @InjectMocks
    var countriesListViewModel = CountryListViewModel()

    private var testSingle: Single<MutableList<Country>>? = null

    @Before
    fun setupMockito() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getCountriesSucess() {
        val country = Country("Brasil", "Brasilia", "url")
        val countries = mutableListOf(country)

        testSingle = Single.just(countries)

        `when`(countryService.getCountries()).thenReturn(testSingle)

        countriesListViewModel.refreshData()

        assertEquals(1, countriesListViewModel.countries.value?.size)
        assertEquals(false, countriesListViewModel.isError.value)
        assertEquals(false, countriesListViewModel.loading.value)

    }


    @Before
    fun setupRxSchedulers() {
        val immediate = object : Scheduler() {
            override fun scheduleDirect(run: Runnable?, delay: Long, unit: TimeUnit?): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }

            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
            }
        }

        RxJavaPlugins.setInitIoSchedulerHandler { immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { immediate }
        RxJavaPlugins.setSingleSchedulerHandler { immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }
    }
}