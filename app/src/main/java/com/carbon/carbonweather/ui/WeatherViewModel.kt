package com.carbon.carbonweather.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carbon.carbonweather.BuildConfig
import com.carbon.carbonweather.utils.Event
import com.carbon.carbonweather.utils.LiveEvent
import com.carbon.carbonweather.utils.LiveEventResource
import com.carbon.carbonweather.utils.MutableLiveEvent
import com.carbon.carbonweather.utils.MutableLiveEventResource
import com.carbon.domain.model.WeatherRequestData
import com.carbon.domain.model.WeatherResponseData
import com.carbon.domain.utils.Resource
import com.carbon.domain.weather.CreateForecastUseCase
import com.carbon.domain.weather.CreateWeatherUseCase
import com.carbon.domain.weather.FetchAllForecastUseCase
import com.carbon.domain.weather.FetchWeatherUseCase
import com.carbon.domain.weather.GetCoordinatesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val createForecastUseCase: CreateForecastUseCase,
    private val getCoordinatesUseCase: GetCoordinatesUseCase,
    private val createWeatherUseCase: CreateWeatherUseCase,
    private val fetchAllForecastUseCase: FetchAllForecastUseCase,
    private val fetchWeatherUseCase: FetchWeatherUseCase
) : ViewModel() {

    private val _getCoordinates = MutableLiveEventResource<String>()
    val getCoordinates: LiveEventResource<String> = _getCoordinates

    private val _currentWeatherDetails = MutableLiveEvent<WeatherResponseData>()
    val currentWeatherDetails: LiveEvent<WeatherResponseData> = _currentWeatherDetails

    private val _weatherForecasts = MutableLiveEvent<List<WeatherResponseData>>()
    val weatherForecasts: LiveEvent<List<WeatherResponseData>> = _weatherForecasts

    fun getCoordinates(city: String) {
        viewModelScope.launch {
            _getCoordinates.value = Event(Resource.loading())
            viewModelScope.launch {
                val coordinates = getCoordinatesUseCase(param = city)
                if (coordinates.isSuccess()) {
                    val data = coordinates.data
                    val latitude = data?.get(0)?.lat
                    val longitude = data?.get(0)?.lon

                    createWeatherUseCase(
                        WeatherRequestData(
                            lat = latitude,
                            lon = longitude,
                            appid = BuildConfig.API_KEY,
                            units = "metric",
                            cnt = 7 )
                    )

                    createForecastUseCase(WeatherRequestData(
                        lat = latitude,
                        lon = longitude,
                        appid = BuildConfig.API_KEY,
                        units = "metric",
                        cnt = 7
                    ))

                    _getCoordinates.value = Event(Resource.success("successfuk"))
                } else {
                    _getCoordinates.value =
                        Event(Resource.error(message = "error"))
                }
            }
        }
    }

    fun fetchCurrentWeather() {
        viewModelScope.launch {
            val data = fetchWeatherUseCase.invoke(Unit)
            data?.let {
                _currentWeatherDetails.value = Event(it)
            }
        }
    }

    fun fetchAllForecasts() {
        viewModelScope.launch {
            fetchAllForecastUseCase.invoke(Unit)
                .collect {value ->
                    _weatherForecasts.value = Event(value)
                }
        }
    }
}
