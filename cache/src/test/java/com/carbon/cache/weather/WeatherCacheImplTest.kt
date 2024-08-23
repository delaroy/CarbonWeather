package com.carbon.cache.weather

import com.carbon.cache.db.createCachedWeather
import com.carbon.cache.db.createForecastWeather
import com.carbon.data.weather.WeatherCache
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class WeatherCacheImplTest {

    private val weatherDao: CarbonWeatherDao = mock()
    private val forecastMapper: CachedForecastMapper = CachedForecastMapper()
    private val weatherMapper: CachedWeatherMapper = CachedWeatherMapper()

    private lateinit var weatherCache: WeatherCache

    @Before
    fun init() {
        weatherCache = WeatherCacheImpl(
            carbonWeatherDao = weatherDao,
            mapper = weatherMapper,
            cachedForecastMapper = forecastMapper
        )
    }

    @Test
    fun `return success if weather data insertion is successful`() = runTest {
        // given that a data is inserted in the database
        val weather = createCachedWeather()
        whenever(weatherDao.saveCachedWeather(weather))
            .thenReturn(1L)

        // when the method is called
        val result = weatherCache.saveWeatherDetails(weatherMapper.from(weather))

        // verify that the dao method is called
        verify(weatherDao).saveCachedWeather(weather)
        // then we assert true to confirm that insertion is successful
        Assert.assertTrue(result)
    }


    @Test
    fun `return error if the insertion fails`() = runTest {
        // given that a data is inserted in the database
        val weather = createCachedWeather()
        whenever(weatherDao.saveCachedWeather(weather))
            .thenReturn(0L)

        // when the method is called
        val result = weatherCache.saveWeatherDetails(weatherMapper.from(weather))

        // verify that the dao method is called
        verify(weatherDao).saveCachedWeather(weather)
        // then we assert true to confirm that insertion is successful
        Assert.assertFalse(result)
    }

    @Test
    fun `verify that all forecasts are retrieved successfully `() = runTest {
        val forecasts = arrayListOf(
            createForecastWeather(),
            createForecastWeather()
        )

        whenever(weatherDao.getForecastDetails())
            .thenReturn(flowOf(forecasts))

        // when the method is called
        val result = weatherCache.getAllForecast()

        // verify that the dao method is called
        verify(weatherDao).getForecastDetails()

        Assert.assertEquals(result.first().size, forecasts.size)
    }

}