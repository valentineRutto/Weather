package com.valentinerutto.weather.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.valentinerutto.weatherapp.data.local.dao.BaseDao
@Entity(tableName = "current_weather_table")
data class CurrentWeather(
    @PrimaryKey
                          val id: Int )
