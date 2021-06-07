package com.example.weatherapp.screen.today_fragment

import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.models.WeatherInfo
import com.example.weatherapp.repository.CloudRepository
import com.example.weatherapp.support.CoroutineViewModel
import kotlinx.coroutines.launch

class TodayFragmentViewModel(private val cloudRepository: CloudRepository): CoroutineViewModel() {

    val weatherInfoLiveData = MutableLiveData<WeatherInfo>()

    fun requestWeatherInfo(){
        launch {
            weatherInfoLiveData.postValue(cloudRepository.importWeather())
        }
    }


}