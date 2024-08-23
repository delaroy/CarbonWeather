package com.carbon.cache.mapper

import com.carbon.cache.db.createForecastWeather
import com.carbon.cache.weather.CachedForecastMapper
import org.junit.Assert
import org.junit.Test

internal class CachedForecastMapperTest {

    private val mapper = CachedForecastMapper()

    @Test
    fun `test map to cache`() {
        val entity = createForecastWeather()

        val cache = mapper.from(entity)

        Assert.assertEquals(cache.weatherMain, entity.weatherMain)
    }

    @Test
    fun `test map from cache`() {

        val cache = createForecastWeather()

        val entity = mapper.from(cache)

        Assert.assertEquals(entity.weatherMain, cache.weatherMain)
    }
}