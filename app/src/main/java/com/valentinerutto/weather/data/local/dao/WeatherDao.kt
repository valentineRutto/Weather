package com.valentinerutto.weather.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.valentinerutto.weather.data.local.entities.DailyWeatherEntity
import com.valentinerutto.weatherapp.data.local.dao.BaseDao

@Dao
interface WeatherDao : BaseDao<DailyWeatherEntity> {


    @Query("SELECT * FROM weather_entity_table")
    fun getSavedWeather(): List<DailyWeatherEntity>

    @Query("SELECT * FROM weather_entity_table WHERE isFavorite = 1 ")
    fun getFavouriteWeather(): List<DailyWeatherEntity>

}