package com.carbon.cache.weather

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_forecast")
data class CachedForecastWeather(
    @PrimaryKey
    val id: String,
    val weatherMain: String,
    val weatherMainTemperature: Double,
    val weatherDescription: String,
    val weatherIcon: String,
    val temperatureMin: Double,
    val temperatureMax: Double,
    val feelsLike: Double,
    val cityName: String,
    val countryCode: String,
    val humidity: Int,
    val pressure: Int,
    val windSpeed: Double,
    val weatherDate: Long
)
