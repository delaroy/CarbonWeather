package com.carbon.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherResponseData(
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
): Parcelable