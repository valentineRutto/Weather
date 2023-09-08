package com.valentinerutto.weather.data.network.api

import com.valentinerutto.weather.data.network.model.CurrentWeatherResponse
import com.valentinerutto.weather.data.network.model.ForecastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: String, @Query("lon") lon: String, @Query("appid") apiKey: String
        ,@Query("units") units: String = "metric"
    ): Response<CurrentWeatherResponse
>
    @GET("forecast")
    suspend fun getOneCallForecast(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric",
        @Query("exclude") exclude: String = "current,minutely,hourly,alerts"

    ): ForecastResponse

}