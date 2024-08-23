package com.carbon.cache.di

import android.content.Context
import com.carbon.cache.db.WeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @[Provides Singleton]
    fun provideDatabase(
        @ApplicationContext context: Context
    ): WeatherDatabase =
        WeatherDatabase.build(context = context)

}
