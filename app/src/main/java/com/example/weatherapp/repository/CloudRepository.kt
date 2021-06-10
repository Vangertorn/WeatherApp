package com.example.weatherapp.repository

import com.example.weatherapp.cloud.*
import com.example.weatherapp.datastore.AppSettings
import com.example.weatherapp.models.AdapterList
import com.example.weatherapp.models.WeatherForecastInfo
import com.example.weatherapp.models.WeatherInfo
import java.text.SimpleDateFormat
import java.util.*

class CloudRepository(
    private val cloudInterface: CloudInterface,
    private val appSettings: AppSettings
) {

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
        val list = mutableListOf<AdapterList>()

        list.addAll(weatherForecastCloudResult.list)
        for (index in list.indices) {
            if (index > 0) {
                if (list[index] is ListElement && list[index - 1] is ListElement){
                    val item = list[index] as ListElement
                    val lastItem = list[index - 1] as ListElement
                    val day = dayNameFormatter.format(item.dt * 1000)
                    val lastDay = dayNameFormatter.format(lastItem.dt * 1000)
                    if (day != lastDay) {
                        list.add(index, Day(dayNameFormatter.format(item.dt * 1000)))
                    }
                }
            }
        }
        list.add(0, Day("today"))

        return WeatherForecastInfo(
            cod = weatherForecastCloudResult.cod,
            message = weatherForecastCloudResult.message,
            cnt = weatherForecastCloudResult.cnt,
            list = list,
            city = weatherForecastCloudResult.city
        )

    }

    companion object {
        const val API_KEY = "1418fa1f8925c57eadec0e0fda01ce1b"
        val dayNameFormatter = SimpleDateFormat("EEEE", Locale.getDefault())
    }
}