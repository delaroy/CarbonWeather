package com.carbon.data.weather

import com.carbon.data.model.WeatherResponse
import com.carbon.data.util.Mapper
import com.carbon.domain.model.WeatherResponseData
import javax.inject.Inject

class WeatherMapper @Inject constructor() : Mapper<WeatherResponse, WeatherResponseData> {

    override fun from(entity: WeatherResponse): WeatherResponseData = entity.run {
        WeatherResponseData(
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

    override fun to(domain: WeatherResponseData): WeatherResponse = domain.run {
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

}
