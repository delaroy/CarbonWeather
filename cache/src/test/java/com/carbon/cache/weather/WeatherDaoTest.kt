package com.carbon.cache.weather

import android.os.Handler
import android.os.Looper
import com.carbon.cache.db.DatabaseTest
import com.carbon.cache.db.createCachedWeather
import com.carbon.cache.db.createForecastWeather
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

internal class WeatherDaoTest : DatabaseTest() {

    private lateinit var dao: CarbonWeatherDao

    @Before
    fun init() {
        dao = database.carbonWeatherDao
    }

    @Test
    fun `verify that current weather data is saved`() {
        val weatherData = createCachedWeather()
        val result = dao.saveCachedWeather(cachedWeather = weatherData)

        // then we verify that the weather data is saved
        Assert.assertEquals(result, 1)
    }

    @Test
    fun `verify that forecast weather data is saved`() {
        val forecastData = arrayListOf(createForecastWeather())
        val result = dao.saveAllForecasts(cachedForecastWeather = forecastData)

        // then we verify that the weather data is saved
        Assert.assertEquals(result.size, 1)
    }

    @Test
    fun `verify that all forecasts are retrieved successfully ` () = runTest  {
        val waitTime = 2000L
        val forecasts = arrayListOf(
            createForecastWeather(),
            createForecastWeather()
        )

        // save all forecasts
        dao.saveAllForecasts(forecasts)

        Handler(Looper.getMainLooper()).postDelayed({
            val result = dao.getForecastDetails()

            launch {
                Assert.assertEquals(forecasts.size, result.toList().size)
            }
        }, waitTime)
    }

    @Test
    fun `verify that all forecast data are deleted successfully`() {
        val forecast =
            arrayListOf(createForecastWeather(), createForecastWeather())

        dao.saveAllForecasts(forecast)

        // delete all forecast
        val result = dao.deleteWeatherForecast()

        Assert.assertEquals(result, 2)
    }

    @Test
    fun `verify that current weather data are deleted successfully`() {

        dao.saveCachedWeather(createCachedWeather())

        // delete weather detail
        val result = dao.deleteWeatherDetails()

        Assert.assertEquals(result, 1)
    }


    suspend fun <T> Flow<List<T>>.flattenToList() =
        flatMapConcat { it.asFlow() }.toList()

}