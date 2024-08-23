package com.carbon.data.weather

import com.carbon.data.BuildConfig
import com.carbon.data.di.IoDispatcher
import com.carbon.data.api.ApiService
import com.carbon.data.model.WeatherResponse
import com.carbon.data.util.safeApiCall
import com.carbon.domain.model.GeoCodeResponseData
import com.carbon.domain.model.WeatherRequestData
import com.carbon.domain.model.WeatherResponseData
import com.carbon.domain.utils.Resource
import com.carbon.domain.weather.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherCache: WeatherCache,
    private val geoMapper: GeoMapper,
    private val weatherMapper: WeatherMapper,
    private val api : ApiService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : WeatherRepository {
    override suspend fun fetchAllForecast(): Flow<List<WeatherResponseData>> =
        withContext(dispatcher) {
            return@withContext weatherCache.getAllForecast().map {
                weatherMapper.mapModelList(it)
            }
        }

    override suspend fun fetchWeather(): WeatherResponseData? =
        withContext(dispatcher) {
            val weather = weatherCache.getWeather()

            weather?.let {
                return@withContext weatherMapper.from(weather)
            }
        }


    override suspend fun getCoordinates(city: String): Resource<List<GeoCodeResponseData>> =
        withContext(dispatcher) {
            val data = safeApiCall {
                api.getDirectGeoCoding(cityName = city, appId = BuildConfig.API_KEY)
            }

            if (data.isError())
                return@withContext Resource.error(data.message)

            return@withContext Resource.success(data.data?.let { geoMapper.mapModelList(it) })
        }

    override suspend fun createWeather(weatherRequestData: WeatherRequestData): Resource<String> =
        withContext(dispatcher) {
            val data = safeApiCall {
                api.getCurrentWeather(
                    lat = weatherRequestData.lat,
                    long = weatherRequestData.lon,
                    appId = weatherRequestData.appid,
                    units = weatherRequestData.units
                )
            }

            if (data.isError())
                return@withContext Resource.error(data.message)

            weatherCache.deleteWeather()
            val weatherData = data.data
            weatherCache.saveWeatherDetails(WeatherResponse(
                id = weatherData?.id.toString(),
                weatherMain = weatherData?.weather?.get(0)?.main ?: "",
                weatherMainTemperature = weatherData?.mainWeather?.temp ?: 0.0,
                weatherDescription = weatherData?.weather?.get(0)?.description ?: "",
                weatherIcon = weatherData?.weather?.get(0)?.icon ?: "",
                temperatureMin = weatherData?.mainWeather?.tempMin ?: 0.0,
                temperatureMax = weatherData?.mainWeather?.tempMax ?: 0.0,
                feelsLike = weatherData?.mainWeather?.feelsLike ?: 0.0,
                cityName = weatherData?.name ?: "",
                countryCode = weatherData?.sys?.country ?: "",
                humidity = weatherData?.mainWeather?.humidity ?: 0,
                pressure = weatherData?.mainWeather?.pressure ?: 0,
                windSpeed = weatherData?.wind?.speed ?: 0.0,
                weatherDate = weatherData?.dt ?: 0L
            ))
            return@withContext Resource.success(
                "Successfully added"
            )
        }

    override suspend fun createForecast(weatherRequestData: WeatherRequestData): Resource<String> =
        withContext(dispatcher) {
            val data = safeApiCall {
                api.getForecast(
                    lat = weatherRequestData.lat,
                    long = weatherRequestData.lon,
                    appId = weatherRequestData.appid,
                    units = weatherRequestData.units,
                    cnt = weatherRequestData.cnt
                )
            }

            if (data.isError())
                return@withContext Resource.error(data.message)

            weatherCache.deleteAllForecast()
            val forecastData = data.data
            val weatherList = mutableListOf<WeatherResponse>()

            forecastData?.list?.map {weatherData ->
                weatherList.add(WeatherResponse(
                    id = weatherData.dt.toString(),
                    weatherMain = weatherData.weather?.get(0)?.main ?: "",
                    weatherMainTemperature = weatherData.temp?.day ?: 0.0,
                    weatherDescription = weatherData.weather?.get(0)?.description ?: "",
                    weatherIcon = weatherData.weather?.get(0)?.icon ?: "",
                    temperatureMin = weatherData.temp?.min ?: 0.0,
                    temperatureMax = weatherData.temp?.max ?: 0.0,
                    feelsLike = weatherData.feelsLike?.day ?: 0.0,
                    cityName = forecastData.city?.name ?: "",
                    countryCode = forecastData.city?.country ?: "",
                    humidity = weatherData.humidity ?: 0,
                    pressure = weatherData.pressure ?: 0,
                    windSpeed = weatherData.speed ?: 0.0,
                    weatherDate = weatherData.dt ?: 0L
                ))
            }

            weatherCache.saveAllForecast(weatherList)

            return@withContext Resource.success(
                "Successfully added"
            )
        }
}
