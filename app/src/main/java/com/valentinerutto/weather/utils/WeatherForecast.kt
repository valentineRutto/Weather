package com.valentinerutto.weather.utils

data class WeatherForecast(
    val weather: Weather,
    val forecasts: List<Forecast>
)

data class Weather(
    val longitude: Float,
    val latitude: Float,
    val main: String,
    val temperature: Float,
    val temperatureMax: Float,
    val temperatureMin: Float
)

data class Forecast(
    val temperature: Float,
    val timeStampL: Long,
    val description: String,
    val timeStampS: String,
)