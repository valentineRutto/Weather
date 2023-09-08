package com.valentinerutto.weather.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valentinerutto.weather.BuildConfig
import com.valentinerutto.weather.WeatherRepository
import com.valentinerutto.weather.utils.ResourceStatus
import com.valentinerutto.weather.utils.WeatherForecast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewmodel(private val repository: WeatherRepository) : ViewModel() {
    private val _successResponse = MutableLiveData<WeatherForecast>()
    val successfulResponse: LiveData<WeatherForecast?>
        get() = _successResponse

    private val _errorResponse = MutableLiveData<String>()
    val errorResponse: LiveData<String>
        get() = _errorResponse

    var _latitude = MutableLiveData<String>()
    val latitude: LiveData<String>
        get() = _latitude

    var _longitude = MutableLiveData<String>()
    val longitude: LiveData<String>
        get() = _longitude

    lateinit var mlatitude: String
    lateinit var mlongitude: String


    suspend fun getWeatherAndForecast(
        latitude: String, longitude: String, appId: String
    ) {
        val resource = repository.getWeatherAndForecastData(latitude, longitude, appId)

        when (resource.status) {
            ResourceStatus.ERROR -> {
                _errorResponse.postValue(resource.errorType.name)
            }

            ResourceStatus.SUCCESS -> {
                _successResponse.postValue(resource.data!!)
            }
        }
    }

    fun fetchResponse() {
        viewModelScope.launch(Dispatchers.IO) {
            getWeatherAndForecast(
                latitude.value!!, longitude.value!!, BuildConfig.OPEN_WEATHER_API_KEY
            )
        }
    }

}