package com.carbon.cache.db

import com.carbon.cache.weather.CachedForecastWeather
import com.carbon.cache.weather.CachedWeather
import java.util.UUID

private fun generateId(): String = UUID.randomUUID().toString()

fun createCachedWeather(id: String = generateId()): CachedWeather =
    CachedWeather(
        id = id,
        weatherMain = "rain",
        weatherMainTemperature = 20.1,
        weatherDescription = "light rain",
        weatherIcon = "304D",
        temperatureMin = 20.1,
        temperatureMax = 30.3,
        feelsLike = 26.2,
        cityName = "Lagos",
        countryCode = "NG",
        humidity = 20,
        pressure = 34,
        windSpeed = 34.1,
        weatherDate = 23674666
    )

fun createForecastWeather(id: String = generateId()): CachedForecastWeather =
    CachedForecastWeather(
        id = id,
        weatherMain = "rain",
        weatherMainTemperature = 20.1,
        weatherDescription = "light rain",
        weatherIcon = "304D",
        temperatureMin = 20.1,
        temperatureMax = 30.3,
        feelsLike = 26.2,
        cityName = "Lagos",
        countryCode = "NG",
        humidity = 20,
        pressure = 34,
        windSpeed = 34.1,
        weatherDate = 23674666
    )