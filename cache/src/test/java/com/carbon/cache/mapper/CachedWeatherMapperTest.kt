package com.carbon.cache.mapper

import com.carbon.cache.db.createCachedWeather
import com.carbon.cache.weather.CachedWeatherMapper
import org.junit.Assert
import org.junit.Test

internal class CachedWeatherMapperTest {

    private val mapper = CachedWeatherMapper()

    @Test
    fun `test map to cache`() {
        val entity = createCachedWeather()

        val cache = mapper.from(entity)

        Assert.assertEquals(cache.weatherMain, entity.weatherMain)
    }

    @Test
    fun `test map from cache`() {

        val cache = createCachedWeather()

        val entity = mapper.from(cache)

        Assert.assertEquals(entity.weatherMain, cache.weatherMain)
    }
}