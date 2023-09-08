package com.valentinerutto.weather.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.valentinerutto.weather.R
import com.valentinerutto.weather.data.local.entities.DailyWeatherEntity
import com.valentinerutto.weather.databinding.RowWeeklyWeatherBinding


interface OnWeatherClicked

class ForecastAdapter(var itemClickListener: OnWeatherClicked) :
    ListAdapter<DailyWeatherEntity, ForecastAdapter.WeatherViewHolder>(
        diff
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        return from(parent)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val album = getItem(position)
        holder.bind(album, itemClickListener)
    }

    class WeatherViewHolder(private val binding: RowWeeklyWeatherBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(weather: DailyWeatherEntity, itemClickListener: OnWeatherClicked) {


            binding.temperatureValueTextview.text = weather.temperature
            binding.weekDayTextview.text = weather.day

            binding.forecastIconImageview.setImageResource(setForecastIcon(weather.weatherDesc))

        }

    }

    companion object {

        fun from(parent: ViewGroup): WeatherViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = RowWeeklyWeatherBinding.inflate(
                layoutInflater,
                parent, false
            )
            return WeatherViewHolder(binding)
        }

        val diff = object : DiffUtil.ItemCallback<DailyWeatherEntity>() {
            override fun areItemsTheSame(
                oldItem: DailyWeatherEntity,
                newItem: DailyWeatherEntity
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: DailyWeatherEntity,
                newItem: DailyWeatherEntity
            ): Boolean = oldItem.id == newItem.id
        }

        private fun setForecastIcon(description: String): Int {
            return if (description.contains("sun", true)) {
                R.drawable.ic_partlysunny_2
            } else if (description.contains("clear", true)) {
                R.drawable.ic_clear_2
            } else if (description.contains("rain", true)) {
                R.drawable.ic_rain_2
            } else if (description.contains("cloud", true)) {
                R.drawable.ic_cloud // icon added for cloud
            } else {
                R.drawable.ic_clear_2
            }
        }
    }


}