package com.carbon.data.utils

import com.carbon.data.model.WeatherResponse
import com.carbon.domain.model.WeatherResponseData
import java.util.UUID

private fun generateId(): String = UUID.randomUUID().toString()

fun createWeather(id: String = generateId()): WeatherResponseData =
    WeatherResponseData(
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

fun createWeatherResp(id: String = generateId()): WeatherResponse =
    WeatherResponse(
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
