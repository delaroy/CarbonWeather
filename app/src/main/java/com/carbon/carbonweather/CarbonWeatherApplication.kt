package com.carbon.carbonweather

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CarbonWeatherApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }

}