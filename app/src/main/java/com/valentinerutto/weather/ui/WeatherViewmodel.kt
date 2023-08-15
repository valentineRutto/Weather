package com.valentinerutto.weather.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valentinerutto.weather.WeatherRepository
import com.valentinerutto.weather.core.Result
import com.valentinerutto.weather.data.network.model.ForecastResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewmodel(private val repository: WeatherRepository) : ViewModel() {
    private val _successResponse = MutableLiveData<ForecastResponse>()
    val successfulResponse: LiveData<ForecastResponse?>
        get() = _successResponse

    private val _errorResponse = MutableLiveData<String>()
    val errorResponse: LiveData<String>
        get() = _errorResponse


    private suspend fun fetch() {

        when (val result = repository.fetchWeatherData()) {

            is Result.Success -> {
                _successResponse.postValue(result.data!!)
            }

            is Result.Error -> {
                _errorResponse.postValue(result.errorType.name)
            }
        }
    }

    fun fetchResponse() {
        viewModelScope.launch(Dispatchers.IO) {
            fetch()
        }
    }

}