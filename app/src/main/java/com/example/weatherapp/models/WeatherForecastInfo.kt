package com.example.weatherapp.models

data class WeatherForecastInfo (
    val cod: String,
    val message: Long,
    val cnt: Long,
    val list: List<AdapterList>,
    val city: City
)