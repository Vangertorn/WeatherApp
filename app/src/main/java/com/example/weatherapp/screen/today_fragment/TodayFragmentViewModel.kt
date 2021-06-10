package com.example.weatherapp.screen.today_fragment

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.weatherapp.datastore.AppSettings
import com.example.weatherapp.models.WeatherInfo
import com.example.weatherapp.repository.CloudRepository
import com.example.weatherapp.support.CoroutineViewModel
import kotlinx.coroutines.launch

class TodayFragmentViewModel(private val cloudRepository: CloudRepository, private val appSettings: AppSettings): CoroutineViewModel() {

    val weatherInfoLiveData = MutableLiveData<WeatherInfo>()

    val coordinatesLatLiveData = appSettings.coordinatesLatFlow().asLiveData()

    fun requestWeatherInfo(){
        launch {
            weatherInfoLiveData.postValue(cloudRepository.importWeather())
        }
    }



}