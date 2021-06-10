package com.example.weatherapp.screen.forecast_fragment

import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.models.WeatherForecastInfo

import com.example.weatherapp.repository.CloudRepository
import com.example.weatherapp.support.CoroutineViewModel
import kotlinx.coroutines.launch

class ForecastFragmentViewModel(private val cloudRepository: CloudRepository): CoroutineViewModel() {
    val weatherForecastInfoLiveData = MutableLiveData<WeatherForecastInfo>()
    fun requestForecastWeatherInfo(){
        launch {
            weatherForecastInfoLiveData.postValue(cloudRepository.importForecastWeather())
        }
    }
}