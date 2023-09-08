package com.valentinerutto.weather.data.network.model

import com.valentinerutto.weather.utils.ErrorType
import java.io.IOException
import java.net.HttpURLConnection

//fun OneCallForeCastResponse.toCoreModel(unit: String): Weather = Weather(
//    current = current.toCoreModel(unit = unit),
//    daily = daily.map { it.toCoreModel(unit = unit) },
//    hourly = hourly.map { it.toCoreModel(unit = unit) }
//)
//
//fun OneCallForeCastResponse.Current.toCoreModel(unit: String): CurrentWeather =
//    CurrentWeather(
//        temperature = formatTemperatureValue(temperature, unit),
//        feelsLike = formatTemperatureValue(feelsLike, unit),
//        weather = weather.map { it.toCoreModel() }
//    )
//
//fun DailyWeatherResponse.toCoreModel(unit: String): DailyWeather =
//    DailyWeather(
//        forecastedTime = getDate(forecastedTime,"EEEE dd/M"),
//        temperature = temperature.toCoreModel(unit = unit),
//        weather = weather.map { it.toCoreModel() }
//    )
//fun TemperatureResponse.toCoreModel(unit: String): Temperature =
//    Temperature(
//        min = formatTemperatureValue(min, unit),
//        max = formatTemperatureValue(max, unit)
//    )
//
//private fun formatTemperatureValue(temperature: Float, unit: String): String =
//    "${temperature.roundToInt()}${getUnitSymbols(unit = unit)}"

fun mapResponseCodeToThrowable(code: Int): Throwable = when (code) {
    HttpURLConnection.HTTP_UNAUTHORIZED -> UnauthorizedException("Unauthorized access : $code")
    in 400..499 -> ClientException("Client error : $code")
    in 500..600 -> ServerException("Server error : $code")
    else -> GenericException("Generic error : $code")
}

fun mapThrowableToErrorType(throwable: Throwable): ErrorType {    val errorType = when (throwable) {
        is ClientException -> ErrorType.CLIENT
        is ServerException -> ErrorType.NETWORK
        else -> ErrorType.NONE
    }
    return errorType
}