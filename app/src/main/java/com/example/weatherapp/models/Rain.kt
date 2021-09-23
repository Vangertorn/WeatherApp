package com.example.weatherapp.models

import com.google.gson.annotations.SerializedName

data class Rain(
    @SerializedName("3h")
    val the3H: Double
)