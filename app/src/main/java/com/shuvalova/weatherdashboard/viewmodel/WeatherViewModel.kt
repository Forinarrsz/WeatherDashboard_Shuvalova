package com.shuvalova.weatherdashboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shuvalova.weatherdashboard.data.WeatherData
import com.shuvalova.weatherdashboard.data.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

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
                error = null,
                loadingProgess = "Запуск загрузки..."
            )
            try {
                coroutineScope {
                    val tempDeferred = async { repository.fetchTemperature() }
                    val humDeferred = async { repository.fetchHumidity() }
                    val windDeferred = async { repository.fetchWindSpeed() }
                    val temperature = tempDeferred.await()
                    val humidity = humDeferred.await()
                    val windSpeed = windDeferred.await()
                    _weatherState.value = WeatherData(
                        temperature = temperature,
                        humidity = humidity,
                        windSpeed = windSpeed,
                        isLoading = false,
                        error = null,
                        loadingProgess = "Загрузка завершена!"
                    )
                }
            } catch (e: Exception) {
                _weatherState.value = _weatherState.value.copy(
                    isLoading = false,
                    error = "Ошибка загрузки: ${e.message}",
                    loadingProgess = ""
                )
            }

                }
            }
    fun toggleErrorSimulation() {
        repository.toggleErrorSimulation()
    }


        }
