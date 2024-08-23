package com.carbon.cache.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.carbon.cache.weather.CachedForecastWeather
import com.carbon.cache.weather.CachedWeather
import com.carbon.cache.weather.CarbonWeatherDao
import com.carbon.cache.db.WeatherDatabase.Companion.DATABASE_VERSION


@Database(
    entities = [CachedForecastWeather::class, CachedWeather::class,], version = DATABASE_VERSION, exportSchema = false
)
abstract class WeatherDatabase : RoomDatabase() {

    abstract val carbonWeatherDao: CarbonWeatherDao

    companion object {
        const val DATABASE_VERSION = 1

        private const val DATABASE_NAME = "carbon_weather_database"

        @Volatile
        private var database: WeatherDatabase? = null

        fun build(
            context: Context
        ): WeatherDatabase {
            return database ?: synchronized(this) {
                val databaseBuilder = Room.databaseBuilder(
                    context.applicationContext,
                    WeatherDatabase::class.java,
                    DATABASE_NAME
                )
                databaseBuilder.fallbackToDestructiveMigration()
                val instance = databaseBuilder.build()
                instance.openHelper.writableDatabase
                database = instance
                instance
            }
        }
    }
}
