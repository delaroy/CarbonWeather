package com.carbon.data.weather

import com.carbon.data.api.ApiService
import com.carbon.data.utils.MainDispatcherRule
import com.carbon.data.utils.createWeatherResp
import com.carbon.domain.weather.WeatherRepository
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class WeatherRepositoryImplTest {

    @Mock
    private lateinit var weatherCache: WeatherCache

    @Mock
    private lateinit var apiService: ApiService

    @Mock
    private lateinit var geoMapper: GeoMapper

    @Mock
    private lateinit var weatherMapper: WeatherMapper

    private lateinit var weatherRepository: WeatherRepository

    @get:Rule
    var dispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        weatherRepository = WeatherRepositoryImpl(
            weatherCache = weatherCache,
            geoMapper = geoMapper,
            weatherMapper = weatherMapper,
            api = apiService,
            dispatcher = dispatcherRule.testDispatcher
        )
    }

    @Test
    fun `get all forecasts from the cache`() = runTest {
        //given that the forecasts cache returns all the cart
        whenever(weatherCache.getAllForecast())
            .thenReturn(flowOf(listOf(createWeatherResp())))

        //when we get the forecasts list from the repository
        val result = weatherRepository.fetchAllForecast()

        //verify that the forecasts cache is called
        verify(weatherCache).getAllForecast()

        //then assert that result has value
        Assert.assertNotNull(result)
    }

}