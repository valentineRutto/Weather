package com.valentinerutto.weather

import com.valentinerutto.weather.core.DefaultLocation
import com.valentinerutto.weather.core.Result
import com.valentinerutto.weather.data.local.dao.CurrentWeatherDao
import com.valentinerutto.weather.data.network.api.WeatherApiService
import com.valentinerutto.weather.data.network.model.OneCallForeCastResponse
import com.valentinerutto.weather.data.network.model.mapResponseCodeToThrowable

class WeatherRepository(
    private val apiService: WeatherApiService,
    private val weatherDao: CurrentWeatherDao
) {
    suspend fun fetchWeatherData(
        defaultLocation: DefaultLocation = DefaultLocation(),
    ): Result<OneCallForeCastResponse> =
        try {
            val response = apiService.getOneCallForecast(
                lat = defaultLocation.latitude,
                lon = defaultLocation.longitude,
                apiKey = BuildConfig.OPEN_WEATHER_API_KEY,
            )

            if (response.isSuccessful && response.body() != null) {
                Result.Success(response.body()!!)

            } else {
                val throwable = mapResponseCodeToThrowable(response.code())
                throw throwable
            }

        } catch (e: Exception) {
            throw e
        }
}
