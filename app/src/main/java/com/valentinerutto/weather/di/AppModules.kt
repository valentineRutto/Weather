package com.valentinerutto.weather.di

import androidx.room.Room
import com.valentinerutto.weather.App
import com.valentinerutto.weather.data.WeatherDatabase
import com.valentinerutto.weather.data.network.api.WeatherApiService
import com.valentinerutto.weatherapp.data.network.api.RetrofitClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit

val appModules = module{

    single { App.INSTANCE}

    single<WeatherApiService> {
        (get() as Retrofit).create(
            WeatherApiService::class.java
        )
    }

    single { RetrofitClient.createOkClient() }

    val baseUrl = "https://api.openweathermap.org/data/2.5/"

    single {
        RetrofitClient.createRetrofit(baseUrl, get())
    }

    single{
        Room.databaseBuilder(androidContext(),
            WeatherDatabase::class.java,"weather-database")
            .build()
    }

}