package com.carbon.data.weather

import com.carbon.data.utils.createWeather
import com.carbon.data.utils.createWeatherResp
import com.carbon.data.weather.WeatherMapper
import org.junit.Assert
import org.junit.Test

internal class WeatherMapperTest {

    private val mapper = WeatherMapper()

    @Test
    fun `test map to entity`() {
        val domain = createWeather()

        val entity = mapper.to(domain)

        Assert.assertEquals(entity.weatherMain, domain.weatherMain)
    }

    @Test
    fun `test map from entity`() {

        val entity = createWeatherResp()

        val domain = mapper.from(entity)

        Assert.assertEquals(domain.weatherMain, entity.weatherMain)
    }
}