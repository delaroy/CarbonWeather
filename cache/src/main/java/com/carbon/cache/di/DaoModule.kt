package com.carbon.cache.di

import com.carbon.cache.weather.CarbonWeatherDao
import com.carbon.cache.db.WeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DaoModule {

    @[Provides Singleton]
    fun providesWeatherDao(
        database: WeatherDatabase,
    ): CarbonWeatherDao = database.carbonWeatherDao
}
