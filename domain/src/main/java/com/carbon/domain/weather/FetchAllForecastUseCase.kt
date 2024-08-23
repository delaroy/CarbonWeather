package com.carbon.domain.weather

import com.carbon.domain.model.WeatherResponseData
import com.carbon.domain.utils.Resource
import com.carbon.domain.utils.SuspendUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchAllForecastUseCase @Inject constructor(private val repository: WeatherRepository) :
    SuspendUseCase<Unit, Flow<List<WeatherResponseData>>>() {

    override suspend fun invoke(param: Unit): Flow<List<WeatherResponseData>> =
        repository.fetchAllForecast()
}