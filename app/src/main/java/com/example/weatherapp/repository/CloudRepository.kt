package com.example.weatherapp.repository

import com.example.weatherapp.cloud.*
import com.example.weatherapp.datastore.AppSettings
import com.example.weatherapp.models.WeatherForecastInfo
import com.example.weatherapp.models.WeatherInfo

class CloudRepository(
    private val cloudInterface: CloudInterface,
    private val appSettings: AppSettings
) {
    var currentLat = appSettings.coordinatesLatFlow()
    var currentLon = appSettings.coordinatesLonFlow()

    suspend fun importWeather(): WeatherInfo {
        val response = cloudInterface.importWeather(
            appSettings.getLat().toString(),
            appSettings.getLon().toString(),
            API_KEY
        )
        val weatherCloudResult: WeatherCloudResult = response.body()!!
        return WeatherInfo(
            coord = weatherCloudResult.coord,
            weather = weatherCloudResult.weather,
            base = weatherCloudResult.base,
            main = weatherCloudResult.main,
            visibility = weatherCloudResult.visibility,
            wind = weatherCloudResult.wind,
            clouds = weatherCloudResult.clouds,
            dt = weatherCloudResult.dt,
            sys = weatherCloudResult.sys,
            timezone = weatherCloudResult.timezone,
            id = weatherCloudResult.id,
            name = weatherCloudResult.name,
            cod = weatherCloudResult.cod
        )

    }

    suspend fun importForecastWeather(): WeatherForecastInfo {
        val response = cloudInterface.importForecastWeather(
            appSettings.getLat().toString(),
            appSettings.getLon().toString(),
            API_KEY
        )
        val weatherForecastCloudResult: WeatherForecastCloudResult = response.body()!!
        return WeatherForecastInfo(
            cod = weatherForecastCloudResult.cod,
            message = weatherForecastCloudResult.message,
            cnt = weatherForecastCloudResult.cnt,
            list = weatherForecastCloudResult.list,
            city = weatherForecastCloudResult.city
        )

    }

    companion object {
        const val API_KEY = "1418fa1f8925c57eadec0e0fda01ce1b"
    }
}