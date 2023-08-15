package com.valentinerutto.weather.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.valentinerutto.weather.core.Temperature

data class DailyWeatherEntity(

    val id: Int, val weather:String,val temperature: String)
