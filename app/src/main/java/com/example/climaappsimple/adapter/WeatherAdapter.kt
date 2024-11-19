package com.example.climaappsimple.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.climaappsimple.databinding.ItemWeatherBinding
import com.example.climaappsimple.model.WeatherEntity

class WeatherAdapter : ListAdapter<WeatherEntity, WeatherAdapter.WeatherViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding = ItemWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)


        holder.itemView.setOnClickListener {

            // Toast.makeText(holder.itemView.context, "Clicked on: ${item.date}", Toast.LENGTH_SHORT).show()
        }
    }

    class WeatherViewHolder(private val binding: ItemWeatherBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(weather: WeatherEntity) {
            binding.tvDate.text = weather.date
            binding.tvTemperature.text = "${weather.temp}°C"
            binding.tvDescription.text = weather.description
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<WeatherEntity>() {
            override fun areItemsTheSame(oldItem: WeatherEntity, newItem: WeatherEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: WeatherEntity, newItem: WeatherEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}

