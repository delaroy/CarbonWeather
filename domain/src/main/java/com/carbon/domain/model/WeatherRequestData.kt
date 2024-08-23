package com.carbon.domain.model

data class WeatherRequestData (
    var lat: Double? = null,

    var lon: Double? = null,

    var appid: String? = null,

    var units: String? = null,

    var cnt: Int? = null

)