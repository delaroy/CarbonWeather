package com.carbon.data.weather

import com.carbon.data.model.direct.GeoCodeResponse
import com.carbon.data.util.Mapper
import com.carbon.domain.model.GeoCodeResponseData
import javax.inject.Inject

class GeoMapper @Inject constructor() : Mapper<GeoCodeResponse, GeoCodeResponseData> {

    override fun from(entity: GeoCodeResponse): GeoCodeResponseData = entity.run {
        GeoCodeResponseData(
            name = name,
            lat = lat,
            lon = lon,
            country = country,
            state = state
        )
    }

    override fun to(domain: GeoCodeResponseData): GeoCodeResponse = domain.run {
        GeoCodeResponse(
            name = name,
            lat = lat,
            lon = lon,
            country = country,
            state = state
        )
    }

}
