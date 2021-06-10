package com.example.weatherapp

import android.location.Location
import com.example.weatherapp.datastore.AppSettings
import com.example.weatherapp.support.CoroutineViewModel
import kotlinx.coroutines.launch

class MainActivityViewModel(private val appSettings: AppSettings): CoroutineViewModel() {

    fun saveLocation(locationResult: Location) {
        launch {
            appSettings.setLat(locationResult.latitude)
            appSettings.setLon(locationResult.longitude)
        }
    }
}