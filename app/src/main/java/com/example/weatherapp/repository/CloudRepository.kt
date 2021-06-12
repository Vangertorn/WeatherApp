package com.example.weatherapp.repository

import android.annotation.SuppressLint
import com.example.weatherapp.cloud.*
import com.example.weatherapp.datastore.AppSettings
import com.example.weatherapp.models.ViewHolderType
import com.example.weatherapp.models.WeatherForecastInfo
import com.example.weatherapp.models.WeatherInfo
import java.text.SimpleDateFormat
import java.util.*

class CloudRepository(
    private val cloudInterface: CloudInterface,
    private val appSettings: AppSettings
) {

    suspend fun importWeather(lat: String, lon: String): WeatherInfo? {
        val response = cloudInterface.importWeather(
            lat,
            lon,
            API_KEY
        )
        val weatherCloudResult: WeatherCloudResult? = response.body()
        if (weatherCloudResult == null) {
            return null
        } else {
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
    }

    suspend fun importForecastWeather(): WeatherForecastInfo? {
        val response = cloudInterface.importForecastWeather(
            appSettings.getLat().toString(),
            appSettings.getLon().toString(),
            API_KEY
        )
        val weatherForecastCloudResult: WeatherForecastCloudResult? = response.body()

        if (weatherForecastCloudResult == null) {
            return null
        } else {

            val list = mutableListOf<ViewHolderType>()

            list.addAll(weatherForecastCloudResult.list)
            for (index in list.indices) {
                if (index > 0) {
                    if (list[index] is ViewHolderType.ListElement && list[index - 1] is ViewHolderType.ListElement) {
                        val item = list[index] as ViewHolderType.ListElement
                        val lastItem = list[index - 1] as ViewHolderType.ListElement
                        val day = dayNameFormatter.format(item.dt * 1000)
                        val lastDay = dayNameFormatter.format(lastItem.dt * 1000)
                        if (day != lastDay) {
                            list.add(
                                index,
                                ViewHolderType.Day(dayNameFormatter.format(item.dt * 1000))
                            )
                        }
                    }
                }
            }
            if (dayNameFormatter.format((list[0] as ViewHolderType.ListElement).dt * 1000) != dayNameFormatter.format(
                    Date()
                )
            ) {
                list.add(
                    0,
                    ViewHolderType.Day(dayNameFormatter.format((list[0] as ViewHolderType.ListElement).dt * 1000))
                )
            } else {
                list.add(0, ViewHolderType.Day("today"))
            }


            return WeatherForecastInfo(
                cod = weatherForecastCloudResult.cod,
                message = weatherForecastCloudResult.message,
                cnt = weatherForecastCloudResult.cnt,
                list = list,
                city = weatherForecastCloudResult.city
            )
        }
    }

    companion object {
        const val API_KEY = "1418fa1f8925c57eadec0e0fda01ce1b"

        @SuppressLint("ConstantLocale")
        val dayNameFormatter = SimpleDateFormat("EEEE", Locale.getDefault())
    }
}