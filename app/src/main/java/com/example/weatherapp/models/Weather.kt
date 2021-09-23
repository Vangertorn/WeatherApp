package com.example.weatherapp.models

data class Weather(
    val id: Long,
    val main: String,
    val description: String,
    val icon: String
)