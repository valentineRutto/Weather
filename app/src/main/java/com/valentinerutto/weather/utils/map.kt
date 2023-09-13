package com.valentinerutto.weather.utils

import com.valentinerutto.weather.data.local.entities.DailyWeatherEntity
import com.valentinerutto.weather.data.network.model.CurrentWeatherResponse
import com.valentinerutto.weather.data.network.model.ForecastResponse

fun map(response: CurrentWeatherResponse?): Weather {
    return response.let {
        Weather(
            longitude = it?.coordinate?.longitude.orZero,
            latitude = it?.coordinate?.latitude.orZero,
            main = it?.weathers?.get(0)?.main.orEmpty(),
            temperature = it?.main?.temperature.orZero,
            temperatureMin = it?.main?.temperatureMin.orZero,
            temperatureMax = it?.main?.temperatureMax.orZero
        )
    }
}

fun mapTemp(response: ForecastResponse?): List<Forecast> {
    return response?.temperatures?.filter { t ->
        t.timeStampS!!.contains("12:00")
    }?.map {
        Forecast(
            temperature = it.main?.temperature.orZero,
            timeStampL = it.timeStampL.orZero,
            description = it.weathers?.get(0)?.main.orEmpty(),
            timeStampS = it.timeStampS.toString(),
        )
    } ?: listOf()
}

fun mapToEntity(weatherForecast: WeatherForecast): List<DailyWeatherEntity> {
    return weatherForecast.forecasts.map {
        DailyWeatherEntity(
            weather = it.description,
            temperatureMax = weatherForecast.weather.temperatureMax.toString(),
            temperatureMin = weatherForecast.weather.temperatureMin.toString(),
            temperature = it.temperature.toString(),
            day = getDayOfWeek(it.timeStampL),
            weatherDesc = weatherForecast.weather.main,
            lastUpdated =getCurrentDateTime(),
            isFavorite = false
        )
    }
}