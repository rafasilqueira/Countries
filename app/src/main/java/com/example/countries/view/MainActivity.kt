package com.example.countries.view

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.countries.R
import com.example.countries.adapter.CountryAdapter
import com.example.countries.viewmodel.CountryListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val countryAdapter = CountryAdapter()
    private lateinit var countryListViewModel: CountryListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        swipeRefresh.setOnRefreshListener {
            swipeRefresh.isRefreshing = false
            countryListViewModel.refreshData()
        }

        countryListViewModel = ViewModelProviders.of(this).get(CountryListViewModel::class.java)
        countryListViewModel.refreshData()

        recyclerView.apply {
            adapter = countryAdapter
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        countryListViewModel.countries.observe(this, Observer {
            it?.let {
                recyclerView.visibility = VISIBLE
                countryAdapter.updateItems(it)
            }
        })

        countryListViewModel.isError.observe(this, Observer {
            txtError.visibility = if (it) VISIBLE else GONE
        })

        countryListViewModel.loading.observe(this, Observer {

            it?.let { progressBar.visibility = if (it) VISIBLE else GONE }

            if (it) {
                recyclerView.visibility = GONE
                txtError.visibility = GONE
            }
        })
    }


}
