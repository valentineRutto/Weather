package com.valentinerutto.weather.data.local.dao

import androidx.room.Dao
import com.valentinerutto.weather.data.network.model.CurrentWeatherResponse
import com.valentinerutto.weatherapp.data.local.dao.BaseDao

@Dao
interface CurrentWeatherDao : BaseDao<CurrentWeatherResponse>