package com.example.weatherapp

import android.location.Location
import com.example.weatherapp.datastore.AppSettings
import com.example.weatherapp.support.CoroutineViewModel
import com.example.weatherapp.support.NetworkConnection
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val appSettings: AppSettings,
    networkConnection: NetworkConnection
) : CoroutineViewModel() {

    val networkConnectionLiveData = networkConnection

    fun saveLocation(locationResult: Location) {
        launch {
            appSettings.setLat(locationResult.latitude)
            appSettings.setLon(locationResult.longitude)
        }
    }


}