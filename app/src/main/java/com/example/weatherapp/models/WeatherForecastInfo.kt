package com.example.weatherapp.models

import com.example.weatherapp.cloud.City
import com.example.weatherapp.cloud.ListElement

data class WeatherForecastInfo (
    val cod: String,
    val message: Long,
    val cnt: Long,
    val list: List<ListElement>,
    val city: City
)