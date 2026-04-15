package com.shuvalova.weatherdashboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shuvalova.weatherdashboard.data.WeatherData
import com.shuvalova.weatherdashboard.data.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val repository = WeatherRepository()

    private val _weatherState = MutableStateFlow(WeatherData())

    val weatherState: StateFlow<WeatherData> = _weatherState.asStateFlow()

    init {
        loadWeatherData()
    }
    fun loadWeatherData() {
        viewModelScope.launch {
            _weatherState.value = _weatherState.value.copy(
                isLoading = true,
                error = null
            )

        }
    }
}