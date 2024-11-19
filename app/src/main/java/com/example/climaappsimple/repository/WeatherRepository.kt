package com.example.climaappsimple.repository

import com.example.climaappsimple.database.WeatherDao
import com.example.climaappsimple.model.WeatherEntity
import com.example.climaappsimple.network.WeatherApiService

class WeatherRepository(
    private val weatherDao: WeatherDao,
    private val apiService: WeatherApiService
) {

    suspend fun fetchWeatherFromApi(city: String): List<WeatherEntity>? {
        val response = apiService.getWeatherForecast(city)
        return if (response.isSuccessful) {
            // Convertir la respuesta de la API a una lista de WeatherEntity
            response.body()?.data?.map {
                WeatherEntity(
                    date = it.datetime,
                    temp = it.temp,
                    maxTemp = it.max_temp,
                    minTemp = it.min_temp,
                    description = it.weather.description,
                    icon = it.weather.icon
                )
            }
        } else {
            null
        }
    }

    suspend fun saveWeatherData(weatherList: List<WeatherEntity>) {
        weatherDao.insertWeatherData(weatherList)
    }

    suspend fun getWeatherFromDatabase(): List<WeatherEntity> {
        return weatherDao.getWeatherData()
    }

    suspend fun clearWeatherData() {
        weatherDao.clearWeatherData()
    }
}

