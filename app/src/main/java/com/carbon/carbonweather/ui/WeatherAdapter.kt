package com.carbon.carbonweather.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.carbon.carbonweather.databinding.ForecastItemsBinding
import com.carbon.domain.model.WeatherResponseData

class WeatherAdapter : ListAdapter<WeatherResponseData, ViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ForecastItemsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        item.let {
            holder.apply {
                bind(item)
                itemView.tag = item
            }
        }

    }
}

class ViewHolder(private val binding: ForecastItemsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: WeatherResponseData) {
        binding.apply {
            model = item
            executePendingBindings()
        }
    }
}

class DiffCallBack : DiffUtil.ItemCallback<WeatherResponseData>() {

    override fun areItemsTheSame(oldItem: WeatherResponseData, newItem: WeatherResponseData): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: WeatherResponseData,
        newItem: WeatherResponseData
    ): Boolean =
        oldItem == newItem
}
