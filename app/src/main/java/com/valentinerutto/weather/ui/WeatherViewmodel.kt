package com.valentinerutto.weather.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.valentinerutto.weather.WeatherRepository
import com.valentinerutto.weather.data.local.entities.DailyWeatherEntity
import com.valentinerutto.weather.utils.DefaultLocation
import com.valentinerutto.weather.utils.ResourceStatus
import com.valentinerutto.weather.utils.WeatherForecast

class WeatherViewmodel(private val repository: WeatherRepository) : ViewModel() {
    private val _successResponse = MutableLiveData<List<DailyWeatherEntity>>()
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

    suspend fun getWeatherAndForecast(
        latitude: String, longitude: String, appId: String
    ) {
        _isLoading.value = true

        val resource = repository.getWeatherAndForecastData(latitude, longitude, appId)

        when (resource.status) {

            ResourceStatus.ERROR -> {
                _isLoading.value = false
                _errorResponse.postValue(resource.errorType.name)
            }

            ResourceStatus.SUCCESS -> {
                _isLoading.value = false
                _successResponse.postValue(resource.data!!)
            }
        }
    }


}