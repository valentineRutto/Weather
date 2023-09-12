package com.valentinerutto.weather.di

import com.valentinerutto.weather.App
import com.valentinerutto.weather.WeatherRepository
import com.valentinerutto.weather.data.WeatherDatabase
import com.valentinerutto.weather.data.network.api.WeatherApiService
import com.valentinerutto.weather.ui.WeatherViewmodel
import com.valentinerutto.weather.utils.Constants
import com.valentinerutto.weatherapp.data.network.api.RetrofitClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.Scope
import org.koin.dsl.module
import retrofit2.Retrofit

val appModules = module {

    single { App.INSTANCE }

    single<WeatherApiService> {
        (get() as Retrofit).create(
            WeatherApiService::class.java
        )
    }

    single { RetrofitClient.createOkClient() }


    single {
        RetrofitClient.createRetrofit(Constants.BASE_URL, get())
    }

    single { WeatherDatabase.getDatabase(context = androidContext()) }


    single {
        WeatherRepository(get(), weatherDao = database().weatherDao())
    }

    viewModel { WeatherViewmodel(get()) }
}

fun Scope.database() = get<WeatherDatabase>()


