package com.example.climaappsimple.model

// Modelo de datos para representar la respuesta de Weatherbit API
data class WeatherModel(
    val data: List<DayWeather>, // Lista de pronósticos diarios
    val city_name: String, // Nombre de la ciudad
    val country_code: String // Código del país
)

// Modelo para representar un día específico en el pronóstico
data class DayWeather(
    val datetime: String, // Fecha en formato YYYY-MM-DD
    val temp: Float, // Temperatura promedio
    val max_temp: Float, // Temperatura máxima
    val min_temp: Float, // Temperatura mínima
    val weather: WeatherDescription // Descripción del clima
)

// Descripción del clima (estado e ícono)
data class WeatherDescription(
    val description: String, // Descripción (ejemplo: Clear Sky)
    val icon: String // Código del ícono (ejemplo: c01d para "clear day")
)
