package com.example.climaappsimple.network

import com.example.climaappsimple.model.WeatherModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.climaappsimple.BuildConfig

// Interfaz para realizar las peticiones a Weatherbit API
interface WeatherApiService {

    // Endpoint para obtener el clima actual y el pronóstico de 7 días
    @GET("forecast/daily")
    suspend fun getWeatherForecast(
        @Query("city") city: String, // Nombre de la ciudad
        @Query("key") apiKey: String = BuildConfig.CLIMA_KEY, // API Key desde BuildConfig
        @Query("days") days: Int = 7 // Número de días de pronóstico (máx. 16 para Weatherbit)
    ): Response<WeatherModel>
}

