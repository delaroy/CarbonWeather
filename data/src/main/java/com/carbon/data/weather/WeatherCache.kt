package com.carbon.data.weather

import com.carbon.data.model.WeatherResponse
import kotlinx.coroutines.flow.Flow

interface WeatherCache {

    suspend fun saveWeatherDetails(weatherResponse: WeatherResponse): Boolean

    suspend fun saveWeatherForecast(weatherResponse: WeatherResponse): Long

    suspend fun saveAllForecast(weatherResponse: List<WeatherResponse>): Boolean

    fun getAllForecast(): Flow<List<WeatherResponse>>

    suspend fun getWeather(): WeatherResponse?

    suspend fun deleteAllForecast(): Boolean

    suspend fun deleteWeather(): Boolean
}