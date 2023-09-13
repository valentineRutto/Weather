package com.valentinerutto.weather.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_entity_table")
data class DailyWeatherEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val weather: String,
    val temperature: String,
    val day: String,
    val weatherDesc: String,
    val lastUpdated: String,
    val temperatureMax: String,
    val temperatureMin: String,
    val isFavorite: Boolean
)
