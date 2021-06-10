package com.example.weatherapp.cloud

import com.example.weatherapp.models.*
import com.google.gson.annotations.SerializedName

data class WeatherForecastCloudResult(
    val cod: String,
    val message: Long,
    val cnt: Long,
    val list: List<ListElement>,
    val city: City
)



data class ListElement(
    val dt: Long,
    val main: MainClass,
    val weather: List<Weather>,
    val clouds: Clouds,
    val wind: Wind,
    val visibility: Long,
    val pop: Double,
    val sys: Sys,

    @SerializedName("dt_txt")
    val dtTxt: String,

    val rain: Rain? = null
) : AdapterList(
)

data class Day(val day: String) : AdapterList()


data class WeatherCloudResult(

    val coord: Coord,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val visibility: Long,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Long,
    val sys: Sys,
    val timezone: Long,
    val id: Long,
    val name: String,
    val cod: Long
)











