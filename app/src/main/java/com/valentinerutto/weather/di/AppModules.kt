package com.valentinerutto.weather.di

import androidx.room.Room
import com.valentinerutto.weather.App
import com.valentinerutto.weather.WeatherRepository
import com.valentinerutto.weather.core.Constants
import com.valentinerutto.weather.data.WeatherDatabase
import com.valentinerutto.weather.data.network.api.WeatherApiService
import com.valentinerutto.weather.ui.WeatherViewmodel
import com.valentinerutto.weatherapp.data.network.api.RetrofitClient
import com.valentinerutto.weatherapp.data.network.api.RetrofitClient.createOkClient
import com.valentinerutto.weatherapp.data.network.api.RetrofitClient.createRetrofit
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
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



    single {
        RetrofitClient.createRetrofit(Constants.BASE_URL, get())
    }

    single{
        Room.databaseBuilder(androidContext(),
            WeatherDatabase::class.java,Constants.DB_NAME).fallbackToDestructiveMigration()
            .build()
    }
    single { get<WeatherDatabase>().currentWeatherDao() }
    single {
        WeatherRepository(get(),get())
    }

viewModel { WeatherViewmodel(get()) }
}


