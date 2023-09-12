package com.valentinerutto.weather

import com.valentinerutto.weather.data.local.dao.WeatherDao
import com.valentinerutto.weather.data.local.entities.DailyWeatherEntity
import com.valentinerutto.weather.data.network.api.WeatherApiService
import com.valentinerutto.weather.data.network.model.mapResponseCodeToThrowable
import com.valentinerutto.weather.utils.ErrorType
import com.valentinerutto.weather.utils.Forecast
import com.valentinerutto.weather.utils.Resource
import com.valentinerutto.weather.utils.Weather
import com.valentinerutto.weather.utils.WeatherForecast
import com.valentinerutto.weather.utils.map
import com.valentinerutto.weather.utils.mapTemp
import com.valentinerutto.weather.utils.mapToEntity
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext

class WeatherRepository(
    private val apiService: WeatherApiService, private val weatherDao: WeatherDao
) {
    private suspend fun getWeatherData(
        latitude: String,
        longitude: String,
        apiKey: String
    ): Resource<Weather> {
        val response = apiService.getCurrentWeather(latitude, longitude, apiKey)
        return if (!response.isSuccessful) {
            mapResponseCodeToThrowable(response.code())
            Resource.error(ErrorType.NETWORK, null)
        } else {
            Resource.success(map(response.body()))
        }
    }

    private suspend fun getForecast5Data(
        latitude: String,
        longitude: String,
        apiKey: String
    ): Resource<List<Forecast>> {
        val response = apiService.getOneCallForecast(latitude, longitude, apiKey)

        return if (response == null) {
            Resource.error(ErrorType.NETWORK, null)
        } else {
            Resource.success(mapTemp(response))
        }
    }

    suspend fun getWeatherAndForecastData(
        latitude: String, longitude: String, appId: String
    ): Resource<List<DailyWeatherEntity>> {

        val weatherResource = withContext(NonCancellable) {
            getWeatherData(latitude, longitude, appId)
        }

        val forecast5Resource = withContext(NonCancellable) {
            getForecast5Data(latitude, longitude, appId)
        }

        return if (weatherResource.data == null || forecast5Resource.data == null) Resource.error(
            weatherResource.errorType,
            null
        )

        else {

            val entity = mapToEntity(
                WeatherForecast(
                    weather = weatherResource.data, forecasts = forecast5Resource.data
                )
            )

            weatherDao.insert(entity)

            Resource.success(
                data = weatherDao.getSavedWeather()
            )

        }
    }
}

