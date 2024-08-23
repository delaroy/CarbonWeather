package com.carbon.cache.weather

import com.carbon.data.model.WeatherResponse
import com.carbon.data.util.Mapper
import javax.inject.Inject

class CachedWeatherMapper @Inject constructor() : Mapper<CachedWeather, WeatherResponse> {

    override fun from(entity: CachedWeather): WeatherResponse = entity.run {
        WeatherResponse(
            id = id,
            weatherMain = weatherMain,
            weatherMainTemperature = weatherMainTemperature,
            weatherDescription = weatherDescription,
            weatherIcon = weatherIcon,
            temperatureMin = temperatureMin,
            temperatureMax = temperatureMax,
            feelsLike = feelsLike,
            cityName = cityName,
            countryCode = countryCode,
            humidity = humidity,
            pressure = pressure,
            windSpeed = windSpeed,
            weatherDate = weatherDate
        )
    }

    override fun to(domain: WeatherResponse): CachedWeather = domain.run {
        CachedWeather(
            id = id,
            weatherMain = weatherMain,
            weatherMainTemperature = weatherMainTemperature,
            weatherDescription = weatherDescription,
            weatherIcon = weatherIcon,
            temperatureMin = temperatureMin,
            temperatureMax = temperatureMax,
            feelsLike = feelsLike,
            cityName = cityName,
            countryCode = countryCode,
            humidity = humidity,
            pressure = pressure,
            windSpeed = windSpeed,
            weatherDate = weatherDate
        )
    }

}