package com.example.weatherapp.screen.forecast_fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.cloud.Day
import com.example.weatherapp.cloud.ListElement
import com.example.weatherapp.models.WeatherForecastInfo
import com.squareup.picasso.Picasso
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*

class ForecastWeatherRecyclerViewAdapter(
    private val weatherForecastInfo: WeatherForecastInfo
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_FORECAST_HOLDER -> ForecastWeatherViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_weather_forecast, parent, false)
            )
            TYPE_DAY_HOLDER -> DayWeatherViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_weather_day, parent, false)
            )

            else -> ForecastWeatherViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_weather_forecast, parent, false)
            )
        }

    }


    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (getItemViewType(position) == TYPE_FORECAST_HOLDER) {
            (holder as ForecastWeatherViewHolder).bind(weatherForecastInfo.list[position] as ListElement)
        } else {
            (holder as DayWeatherViewHolder).bind(weatherForecastInfo.list[position] as Day)
        }
    }

    override fun getItemCount(): Int {
        return weatherForecastInfo.list.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (weatherForecastInfo.list[position]) {
            is ListElement -> TYPE_FORECAST_HOLDER
            is Day -> TYPE_DAY_HOLDER
            else -> INVALID
        }
    }

    inner class ForecastWeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvTime = itemView.findViewById<TextView>(R.id.tvTime)

        private val imWeather = itemView.findViewById<ImageView>(R.id.imageWeather)

        private val tvWeather = itemView.findViewById<TextView>(R.id.tvWeather)

        private val tvTemperature = itemView.findViewById<TextView>(R.id.tvTemperature)


        @SuppressLint("SetTextI18n")
        fun bind(item: ListElement) {

            Picasso.get().load("${IMAGE_URI}${item.weather[0].icon}@2x.png")
                .error(R.drawable.ic_cloud).into(imWeather)
            tvTime.text = horsFormatter.format(Date(item.dt * 1000))
            tvWeather.text = item.weather[0].description
            tvTemperature.text = "${
                (item.main.temp - 273).toBigDecimal().setScale(1, RoundingMode.HALF_EVEN)
            } °C"
        }
    }

    inner class DayWeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvDay = itemView.findViewById<TextView>(R.id.tvDay)


        fun bind(item: Day) {
            tvDay.text = item.day

        }
    }

    companion object {
        val horsFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())
        const val IMAGE_URI = "http://openweathermap.org/img/wn/"
        const val TYPE_FORECAST_HOLDER = 0
        const val TYPE_DAY_HOLDER = 1
        const val INVALID = -1

    }

}
