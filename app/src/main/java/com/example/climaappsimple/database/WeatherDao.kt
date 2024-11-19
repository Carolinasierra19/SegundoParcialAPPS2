package com.example.climaappsimple.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.climaappsimple.model.WeatherEntity

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeatherData(weatherList: List<WeatherEntity>)

    @Query("SELECT * FROM weather_table")
    fun getWeatherData(): List<WeatherEntity>

    @Query("DELETE FROM weather_table")
    fun clearWeatherData()
}



