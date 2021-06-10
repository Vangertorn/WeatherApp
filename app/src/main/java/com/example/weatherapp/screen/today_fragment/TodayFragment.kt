package com.example.weatherapp.screen.today_fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentTodayBinding
import com.example.weatherapp.support.SupportFragmentInset
import com.squareup.picasso.Picasso

import org.koin.android.viewmodel.ext.android.viewModel
import java.math.RoundingMode

class TodayFragment : SupportFragmentInset<FragmentTodayBinding>(R.layout.fragment_today) {
    override val viewBinding: FragmentTodayBinding by viewBinding()
    private val viewModel: TodayFragmentViewModel by viewModel()


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.coordinatesLatLiveData.observe(this.viewLifecycleOwner) {
            viewModel.requestWeatherInfo()
            viewBinding.indicatorProgress.isVisible = true
        }


        viewModel.weatherInfoLiveData.observe(this.viewLifecycleOwner) { WeatherInfo ->
            val picasso = Picasso.Builder(requireContext())
                .listener { _, _, exception -> exception.printStackTrace() }.build()

            picasso.load("$IMAGE_URI${WeatherInfo.weather[0].icon}@2x.png").fit()
                .error(R.drawable.ic_cloud)
                .into(viewBinding.ivWeather)

            viewBinding.tvLocation.text = "${WeatherInfo.name}, ${WeatherInfo.sys.country}"

            val temp =
                (WeatherInfo.main.temp - 273).toBigDecimal().setScale(1, RoundingMode.HALF_EVEN)
            viewBinding.tvWeatherTitle.text = "$temp | ${WeatherInfo.weather[0].main}"

            viewBinding.tvHumidity.text = "${WeatherInfo.main.humidity}%"

            viewBinding.tvPressure.text = "${WeatherInfo.main.pressure} hPa"

            viewBinding.tvClouds.text = "${WeatherInfo.clouds.all}%"

            viewBinding.tvGustWind.text = "${WeatherInfo.wind.gust} m/s"

            viewBinding.tvSpeedWind.text = "${WeatherInfo.wind.speed} km/h"
            viewBinding.indicatorProgress.isVisible = false

            viewBinding.btnShare.setOnClickListener {
                val shareIntent = Intent().apply {
                    this.action = Intent.ACTION_SEND
                    this.putExtra(
                        Intent.EXTRA_TEXT,
                        "The temperature is $temp in ${WeatherInfo.name} and there is ${WeatherInfo.weather[0].main}"
                    )
                    this.type = "text/plain"
                }
                startActivity(shareIntent)
            }

        }


    }


    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
    }

    companion object {
        const val IMAGE_URI = "http://openweathermap.org/img/wn/"
    }

}