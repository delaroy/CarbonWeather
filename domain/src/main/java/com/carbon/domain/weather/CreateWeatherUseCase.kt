package com.carbon.domain.weather

import com.carbon.domain.model.WeatherRequestData
import com.carbon.domain.utils.Resource
import com.carbon.domain.utils.SuspendUseCase
import javax.inject.Inject

class CreateWeatherUseCase @Inject constructor(private val repository: WeatherRepository) :
    SuspendUseCase<WeatherRequestData, Resource<String>>() {

    override suspend fun invoke(param: WeatherRequestData): Resource<String> =
        repository.createWeather(param)
}