package com.example.weatherapp

import android.app.Application
import com.example.weatherapp.cloud.CloudInterface
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class WeatherApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WeatherApp)
            modules(listOf(cloudModule))
        }
    }
    private val cloudModule = module {
        factory { CloudInterface.get() }
    }
}