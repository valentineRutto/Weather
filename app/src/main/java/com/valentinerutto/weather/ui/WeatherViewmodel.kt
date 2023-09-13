package com.valentinerutto.weather.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valentinerutto.weather.BuildConfig
import com.valentinerutto.weather.WeatherRepository
import com.valentinerutto.weather.data.local.entities.DailyWeatherEntity
import com.valentinerutto.weather.utils.DefaultLocation
import com.valentinerutto.weather.utils.ResourceStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewmodel(private val repository: WeatherRepository) : ViewModel() {
    val _successResponse = MutableLiveData<List<DailyWeatherEntity>>()
    val successfulResponse: LiveData<List<DailyWeatherEntity>?>
        get() = _successResponse

    private val _errorResponse = MutableLiveData<String>()
    val errorResponse: LiveData<String>
        get() = _errorResponse

    var _location = MutableLiveData<DefaultLocation>()
    val location: LiveData<DefaultLocation>
        get() = _location

    val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading


    private val _favouriteList = MutableLiveData<List<DailyWeatherEntity>?>()
    val favouriteList: LiveData<List<DailyWeatherEntity>?>
        get() = _favouriteList

    suspend fun getWeatherAndForecast(
        latitude: String, longitude: String
    ) {

        val resource = repository.getWeatherAndForecastData(
            latitude, longitude, BuildConfig.OPEN_WEATHER_API_KEY
        )

        when (resource.status) {

            ResourceStatus.ERROR -> {
                _errorResponse.postValue(resource.errorType.name)
            }

            ResourceStatus.SUCCESS -> {
                _successResponse.postValue(resource.data!!)
            }
        }
    }


    fun fetchSaveWeather(latitude: String, longitude: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getWeatherAndForecast(latitude, longitude)
        }
    }

    suspend fun updateWeatherData(dailyWeatherEntity: DailyWeatherEntity) {
        return repository.updateWeatherData(dailyWeatherEntity)
    }

    suspend fun getSavedData(
    ): List<DailyWeatherEntity> {

        return repository.getSavedData()

    }
}