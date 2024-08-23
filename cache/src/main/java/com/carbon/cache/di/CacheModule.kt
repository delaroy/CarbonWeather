package com.carbon.cache.di

import com.carbon.cache.weather.WeatherCacheImpl
import com.carbon.data.weather.WeatherCache
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
interface CacheModule {

    @Binds
    fun bindWeatherCache(weatherCacheImpl: WeatherCacheImpl): WeatherCache

}
