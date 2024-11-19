package com.example.climaappsimple.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.climaappsimple.model.WeatherEntity
import com.example.climaappsimple.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    // LiveData para la UI
    private val _weatherData = MutableLiveData<List<WeatherEntity>?>()  // Acepta null
    val weatherData: LiveData<List<WeatherEntity>?> = _weatherData


    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    // Función para cargar los datos del clima
    fun loadWeatherData(city: String) {
        _isLoading.value = true

        // Hacer la llamada a la API para obtener los datos
        viewModelScope.launch {
            try {
                // Intentamos obtener los datos de la API
                val weatherFromApi = repository.fetchWeatherFromApi(city)
                if (weatherFromApi != null) {
                    // Si los datos son exitosos, los pasamos a la UI
                    _weatherData.value = weatherFromApi
                } else {
                    _errorMessage.value = "Error al obtener los datos del clima."
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Guardar los datos en Room
    fun saveWeatherData(weatherList: List<WeatherEntity>) {
        viewModelScope.launch {
            repository.saveWeatherData(weatherList)
        }
    }
}

