package com.carbon.cache.weather

import com.carbon.data.model.WeatherResponse
import com.carbon.data.weather.WeatherCache
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WeatherCacheImpl @Inject constructor(
    private val carbonWeatherDao: CarbonWeatherDao,
    private val mapper: CachedWeatherMapper,
    private val cachedForecastMapper: CachedForecastMapper
) : WeatherCache {
    override suspend fun saveWeatherDetails(weatherResponse: WeatherResponse): Boolean =
        carbonWeatherDao.saveCachedWeather(mapper.to(weatherResponse)) > 0L

    override suspend fun saveWeatherForecast(weatherResponse: WeatherResponse): Long =
        carbonWeatherDao.saveCachedForecastWeather(cachedForecastMapper.to(weatherResponse))

    override suspend fun saveAllForecast(weatherResponse: List<WeatherResponse>): Boolean =
        carbonWeatherDao.saveAllForecasts(cachedForecastMapper.mapEntityList(weatherResponse)).size == weatherResponse.size

    override fun getAllForecast(): Flow<List<WeatherResponse>> {
        val forecasts = carbonWeatherDao.getForecastDetails()

        return forecasts.map {
            cachedForecastMapper.mapModelList(it)
        }
    }

    override suspend fun getWeather(): WeatherResponse? {
        if (carbonWeatherDao.getWeatherDetails() == null) {
            return null
        } else {
            return mapper.from(carbonWeatherDao.getWeatherDetails())
        }
    }


    override suspend fun deleteAllForecast(): Boolean =
        carbonWeatherDao.deleteWeatherForecast() > 0

    override suspend fun deleteWeather(): Boolean =
        carbonWeatherDao.deleteWeatherDetails() > 0

}