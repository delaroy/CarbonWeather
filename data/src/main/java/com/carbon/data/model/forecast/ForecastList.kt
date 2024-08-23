package com.carbon.data.model.forecast

import com.carbon.data.model.weather.Weather

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import kotlin.collections.List


data class ForecastList (
    @SerializedName("dt")
    @Expose
    var dt: Long? = null,

    @SerializedName("sunrise")
    @Expose
    var sunrise: Int? = null,

    @SerializedName("sunset")
    @Expose
    var sunset: Int? = null,

    @SerializedName("temp")
    @Expose
    var temp: Temp? = null,

    @SerializedName("feels_like")
    @Expose
    var feelsLike: FeelsLike? = null,

    @SerializedName("pressure")
    @Expose
    var pressure: Int? = null,

    @SerializedName("humidity")
    @Expose
    var humidity: Int? = null,

    @SerializedName("weather")
    @Expose
    var weather: List<Weather>? = null,

    @SerializedName("speed")
    @Expose
    var speed: Double? = null,

    @SerializedName("deg")
    @Expose
    var deg: Int? = null,

    @SerializedName("gust")
    @Expose
    var gust: Double? = null,

    @SerializedName("clouds")
    @Expose
    var clouds: Int? = null,

    @SerializedName("pop")
    @Expose
    var pop: Double? = null,

    @SerializedName("rain")
    @Expose
    var rain: Double? = null
)