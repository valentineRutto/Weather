package com.valentinerutto.weather.data.local.entities

import androidx.room.Entity

@Entity("tableName = current_weather_table")
data class CurrentWeatherEntitiy(
    val id: Int,
    val day:String,
    val weather: String,
    val temperature: String,
    val weatherDesc: String,
    val lastUpdated: String,
    val temperatureMax: String,
    val temperatureMin: String,
    val isFavorite: Boolean
)