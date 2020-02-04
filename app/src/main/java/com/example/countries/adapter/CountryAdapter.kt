package com.example.countries.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.countries.R
import com.example.countries.model.Country
import com.example.countries.utils.getProgressDrawable
import com.example.countries.utils.loadImage
import kotlinx.android.synthetic.main.list_item_country.view.*

class CountryAdapter(
    val countries: MutableList<Country> = ArrayList()
) : AbstractAdapter<Country>(countries) {

    override fun updateItems(items: MutableList<Country>) {
        countries.clear()
        countries.addAll(items)
        notifyDataSetChanged()
    }

    override fun setupViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CountryVH(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_country,
                parent,
                false
            )
        )
    }

    override fun onBindData(holder: RecyclerView.ViewHolder, genericType: Country) {
        if (holder is CountryVH) {
            holder.bind(genericType)
        }
    }

    class CountryVH(view: View) : RecyclerView.ViewHolder(view) {

        private val countryName = view.countryName
        private val capital = view.capital
        private val img = view.img
        private val progress = getProgressDrawable(view.context)

        fun bind(country: Country) {
            countryName.text = country.name
            capital.text = country.capital
            img.loadImage(country.flagPNG, progress)
        }
    }
}

