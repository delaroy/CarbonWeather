package com.carbon.domain.weather

import com.carbon.domain.model.GeoCodeResponseData
import com.carbon.domain.model.WeatherRequestData
import com.carbon.domain.model.WeatherResponseData
import com.carbon.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface WeatherRepository
{
    suspend fun fetchAllForecast(): Flow<List<WeatherResponseData>>

    suspend fun fetchWeather(): WeatherResponseData?

    suspend fun getCoordinates(city: String): Resource<List<GeoCodeResponseData>>

    suspend fun createWeather(weatherRequestData: WeatherRequestData): Resource<String>

    suspend fun createForecast(weatherRequestData: WeatherRequestData): Resource<String>
}
