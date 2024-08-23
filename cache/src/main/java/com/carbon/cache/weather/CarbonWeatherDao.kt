package com.carbon.cache.weather

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CarbonWeatherDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveCachedWeather(cachedWeather: CachedWeather): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveAllForecasts(cachedForecastWeather: List<CachedForecastWeather>): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveCachedForecastWeather(cachedForecastWeather: CachedForecastWeather): Long

    @Query("SELECT * FROM `weather_forecast`")
    fun getForecastDetails(): Flow<List<CachedForecastWeather>>

    @Query("SELECT * FROM `weather`")
    fun getWeatherDetails(): CachedWeather

    @Query("DELETE FROM `weather`")
    fun deleteWeatherDetails(): Int

    @Query("DELETE FROM `weather_forecast`")
    fun deleteWeatherForecast(): Int

}
