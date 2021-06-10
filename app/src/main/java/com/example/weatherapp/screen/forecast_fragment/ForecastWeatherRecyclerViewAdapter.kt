package com.example.weatherapp.screen.forecast_fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.cloud.ListElement
import com.example.weatherapp.models.WeatherForecastInfo
import com.example.weatherapp.screen.today_fragment.TodayFragment
import com.squareup.picasso.Picasso
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*

class ForecastWeatherRecyclerViewAdapter(
    private val weatherForecastInfo: WeatherForecastInfo
) :
    RecyclerView.Adapter<ForecastWeatherRecyclerViewAdapter.ForecastWeatherViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ForecastWeatherRecyclerViewAdapter.ForecastWeatherViewHolder = ForecastWeatherViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_weather_forecast, parent, false)
    )

    override fun onBindViewHolder(
        holder: ForecastWeatherViewHolder,
        position: Int
    ) {
        holder.bind(weatherForecastInfo.list[position])
    }

    override fun getItemCount(): Int {
        return weatherForecastInfo.list.size
    }

    inner class ForecastWeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvTime = itemView.findViewById<TextView>(R.id.tvTime)

        private val imWeather = itemView.findViewById<ImageView>(R.id.imageWeather)

        private val tvWeather = itemView.findViewById<TextView>(R.id.tvWeather)

        private val tvTemperature = itemView.findViewById<TextView>(R.id.tvTemperature)


        @SuppressLint("SetTextI18n")
        fun bind(item: ListElement) {

            Picasso.get().load("${IMAGE_URI}${item.weather[0].icon}@2x.png").error(R.drawable.ic_cloud).into(imWeather)
            tvTime.text = horsFormatter.format(Date(item.dt * 1000))
            tvWeather.text = item.weather[0].description
            tvTemperature.text = "${
                (item.main.temp - 273).toBigDecimal().setScale(1, RoundingMode.HALF_EVEN)
            } °C"
        }
    }

    companion object {
        val horsFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())
        const val IMAGE_URI = "http://openweathermap.org/img/wn/"

    }
}
