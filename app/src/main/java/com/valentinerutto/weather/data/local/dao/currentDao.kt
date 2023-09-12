package com.valentinerutto.weather.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.valentinerutto.weather.data.local.entities.CurrentWeatherEntitiy
import com.valentinerutto.weather.data.local.entities.DailyWeatherEntity
import com.valentinerutto.weatherapp.data.local.dao.BaseDao
@Dao
interface currentDao:BaseDao<CurrentWeatherEntitiy> {
    @Query("SELECT * FROM weather_entity_table")
    fun getSavedCurrentWeather(): List<CurrentWeatherEntitiy>

}