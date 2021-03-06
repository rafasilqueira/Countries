package com.example.countries.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class AbstractAdapter<T>(private var mItems: MutableList<T>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    abstract fun updateItems(items: MutableList<T>)

    abstract fun setupViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    abstract fun onBindData(holder: RecyclerView.ViewHolder, genericType: T)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        setupViewHolder(parent, viewType)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        onBindData(holder, this.mItems[position])
    }

    override fun getItemCount() = this.mItems.size

    fun getItem(position: Int): T = this.mItems[position]


}