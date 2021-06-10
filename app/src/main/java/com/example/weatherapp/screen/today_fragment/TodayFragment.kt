package com.example.weatherapp.screen.today_fragment

import android.annotation.SuppressLint
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

        viewModel.requestWeatherInfo()
        viewBinding.indicatorProgress.isVisible = true
        viewBinding.btnShare.setOnClickListener {

        }

        viewModel.weatherInfoLiveData.observe(this.viewLifecycleOwner) {
            val picasso = Picasso.Builder(requireContext())
                .listener { _, _, exception -> exception.printStackTrace() }.build()

            picasso.load("$IMAGE_URI${it.weather[0].icon}@2x.png").fit().error(R.drawable.ic_cloud)
                .into(viewBinding.ivWeather)

            viewBinding.tvLocation.text = "${it.name}, ${it.sys.country}"

            viewBinding.tvWeatherTitle.text = "${
                (it.main.temp - 273).toBigDecimal().setScale(1, RoundingMode.HALF_EVEN)
            } | ${it.weather[0].main}"

            viewBinding.tvHumidity.text = it.main.humidity.toString()

            viewBinding.tvPressure.text = it.main.pressure.toString()

            viewBinding.tvClouds.text = it.clouds.all.toString()

            viewBinding.tvGustWind.text = it.wind.gust.toString()

            viewBinding.tvSpeedWind.text = it.wind.speed.toString()
            viewBinding.indicatorProgress.isVisible = false


        }
    }


    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        viewBinding.toolbarToday.setPadding(0, top, 0, 0)
    }

    companion object {
        const val IMAGE_URI = "http://openweathermap.org/img/wn/"
    }

}