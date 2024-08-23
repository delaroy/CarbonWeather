package com.carbon.domain.weather

import com.carbon.domain.model.GeoCodeResponseData
import com.carbon.domain.utils.Resource
import com.carbon.domain.utils.SuspendUseCase
import javax.inject.Inject

class GetCoordinatesUseCase @Inject constructor(private val repository: WeatherRepository) :
    SuspendUseCase<String, Resource<List<GeoCodeResponseData>>>() {

    override suspend fun invoke(param: String): Resource<List<GeoCodeResponseData>> =
        repository.getCoordinates(param)
}