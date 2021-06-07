package com.example.weatherapp.repository
import com.example.weatherapp.cloud.*
import com.example.weatherapp.datastore.AppSettings
import com.example.weatherapp.models.WeatherInfo

class CloudRepository(private val cloudInterface: CloudInterface, private val appSettings: AppSettings) {
    var currentLat = appSettings.coordinatesLatFlow()
    var currentLon = appSettings.coordinatesLonFlow()

    suspend fun importWeather(): WeatherInfo {
        val response = cloudInterface.importWeather(
            appSettings.getLat().toString(),
            appSettings.getLon().toString(),
            API_KEY
        )
        val weatherCloudResult: WeatherCloudResult = response.body()!!
//            WeatherCloudResult(
//                coord = Coord(0.0,0.0
//                ),
//                weather = emptyList(),
//                base = "0",
//                main = Main(0.0,0.0,0.0,0.0,0L,0L),
//                visibility = 0,
//                wind = Wind(0.0,0L,0.0),
//                clouds = Clouds(0L),
//                dt = 0,
//                sys = Sys(0L,0L,"0",0L,0L),
//                timezone = 0,
//                id = 0,
//                name = "0",
//                cod = 0
//            )
//        }
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

    companion object {
        const val API_KEY = "1418fa1f8925c57eadec0e0fda01ce1b"
    }
}