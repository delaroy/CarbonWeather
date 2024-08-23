package com.carbon.data.api

import com.carbon.data.BuildConfig.BASE_URL_COORD
import com.carbon.data.model.direct.GeoCodeResponse
import com.carbon.data.model.forecast.Forecast
import com.carbon.data.model.weather.CurrentWeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double? = null,
        @Query("lon") long: Double? = null,
        @Query("appid") appId: String? = null,
        @Query("units") units: String? = null
    ): Response<CurrentWeatherResponse>

    @GET(BASE_URL_COORD + "direct")
    suspend fun getDirectGeoCoding(
        @Query("q") cityName: String? = null,
        @Query("appid") appId: String? = null
    ): Response<List<GeoCodeResponse>>

    @GET("forecast/daily")
    suspend fun getForecast(
        @Query("lat") lat: Double? = null,
        @Query("lon") long: Double? = null,
        @Query("cnt") cnt: Int? = null,
        @Query("appid") appId: String? = null,
        @Query("units") units: String? = null
    ): Response<Forecast>

}
