package com.example.weatherapp

import android.app.Application
import com.example.weatherapp.cloud.CloudInterface
import com.example.weatherapp.datastore.AppSettings
import com.example.weatherapp.repository.CloudRepository
import com.example.weatherapp.screen.forecast_fragment.ForecastFragmentViewModel
import com.example.weatherapp.screen.today_fragment.TodayFragmentViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.android.viewmodel.dsl.viewModel

class WeatherApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WeatherApp)
            modules(listOf(cloudModule, viewModel, repository, barnModel))
        }
    }

    private val viewModel = module {
        viewModel { TodayFragmentViewModel(get()) }
        viewModel { MainActivityViewModel(get()) }
        viewModel { ForecastFragmentViewModel(get()) }
    }

    private val cloudModule = module {
        factory { CloudInterface.get() }

    }

    private val repository = module {
        factory { CloudRepository(get(), get()) }
    }

    private val barnModel = module {
        single { AppSettings(get()) }
    }
}