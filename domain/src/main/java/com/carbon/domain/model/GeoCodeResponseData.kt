package com.carbon.domain.model

data class GeoCodeResponseData (
    var name: String? = null,

    var lat: Double? = null,

    var lon: Double? = null,

    var country: String? = null,

    var state: String? = null
)