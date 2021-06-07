package com.example.weatherapp.screen.today_fragment

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.requestWeatherInfo()
//        viewBinding.indicatorProgress.isVisible = true
        viewBinding.btnShare.setOnClickListener {

        }

        viewModel.weatherInfoLiveData.observe(this.viewLifecycleOwner) {
            viewBinding.tvLocation.text = "${it.name}, ${it.sys.country}"
            viewBinding.tvWeatherTitle.text = "${
                (it.main.temp - 273).toBigDecimal().setScale(1, RoundingMode.HALF_EVEN)
            } | ${it.weather[0].main}"
            viewBinding.tvHumidity.text = it.main.humidity.toString()
            viewBinding.tvPressure.text = it.main.pressure.toString()
            viewBinding.tvClouds.text = it.clouds.all.toString()
            viewBinding.tvGustWind.text = it.wind.gust.toString()
            viewBinding.tvSpeedWind.text = it.wind.speed.toString()


//            viewBinding.indicatorProgress.isVisible = false
        }
    }


    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {

    }
}