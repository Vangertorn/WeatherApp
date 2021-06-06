package com.example.weatherapp.repository

import com.example.weatherapp.cloud.CloudInterface
import com.example.weatherapp.models.WeatherInfo

class CloudRepository(private val cloudInterface: CloudInterface) {

    suspend fun importWeather(): Boolean {
        val response = cloudInterface.importWeather("0","0", API_KEY)
        val weatherCloudResults = response.body() ?: emptyList()
        val weatherInfoList =
            weatherCloudResults.map {
                WeatherInfo(
                   coord = it.coord,
                    weather = it.weather,
                    base = it.base,
                    main = it.main,
                    visibility = it.visibility,
                    wind = it.wind,
                    clouds = it.clouds,
                    dt = it.dt,
                    sys = it.sys,
                    timezone = it.timezone,
                    id = it.id,
                    name = it.name,
                    cod = it.cod
                )
            }
        return response.isSuccessful
    }
    companion object{
        const val API_KEY = "1418fa1f8925c57eadec0e0fda01ce1b"
    }
}