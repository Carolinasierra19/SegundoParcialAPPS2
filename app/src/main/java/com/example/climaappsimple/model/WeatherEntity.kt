package com.example.climaappsimple.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_table")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: String,
    val temp: Float,
    val maxTemp: Float,
    val minTemp: Float,
    val description: String,
    val icon: String
)

