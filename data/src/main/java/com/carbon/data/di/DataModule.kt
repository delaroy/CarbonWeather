package com.carbon.data.di

import com.carbon.data.weather.WeatherRepositoryImpl
import com.carbon.domain.weather.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface DataModule {

    @Binds
    fun bindWeatherRepository(weatherRepositoryImpl: WeatherRepositoryImpl) : WeatherRepository

}