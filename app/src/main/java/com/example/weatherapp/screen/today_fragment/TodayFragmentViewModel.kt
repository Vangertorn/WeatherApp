package com.example.weatherapp.screen.today_fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.weatherapp.datastore.AppSettings
import com.example.weatherapp.models.WeatherInfo
import com.example.weatherapp.repository.CloudRepository
import com.example.weatherapp.support.CoroutineViewModel
import com.example.weatherapp.support.NetworkConnection
import kotlinx.coroutines.launch

class TodayFragmentViewModel(
    private val cloudRepository: CloudRepository,
    appSettings: AppSettings,
    networkConnection: NetworkConnection
) : CoroutineViewModel() {

    val weatherInfoLiveData = MutableLiveData<WeatherInfo?>()

    val coordinatesLatLiveData = appSettings.coordinatesLatFlow().asLiveData()

    val coordinatesLonLiveData = appSettings.coordinatesLonFlow().asLiveData()

    val networkConnectionLiveData = networkConnection

    fun requestWeatherInfo(lat: String, lon: String) {
        launch {
            weatherInfoLiveData.postValue(cloudRepository.importWeather(lat, lon))
        }
    }


}