package com.carbon.domain.weather

import com.carbon.domain.model.WeatherResponseData
import com.carbon.domain.utils.Resource
import com.carbon.domain.utils.SuspendUseCase
import javax.inject.Inject

class FetchWeatherUseCase @Inject constructor(private val repository: WeatherRepository) :
    SuspendUseCase<Unit, WeatherResponseData?>() {

    override suspend fun invoke(param: Unit): WeatherResponseData? =
        repository.fetchWeather()
}