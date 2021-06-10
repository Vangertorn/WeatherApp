package com.example.weatherapp.screen.forecast_fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentForecastBinding
import com.example.weatherapp.support.SupportFragmentInset
import org.koin.android.viewmodel.ext.android.viewModel

class ForecastFragment : SupportFragmentInset<FragmentForecastBinding>(R.layout.fragment_forecast) {
    override val viewBinding: FragmentForecastBinding by viewBinding()
    private val viewModel: ForecastFragmentViewModel by viewModel()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.requestForecastWeatherInfo()
        viewBinding.indicatorProgress.isVisible = true
        viewModel.weatherForecastInfoLiveData.observe(this.viewLifecycleOwner) {
            val adapter = ForecastWeatherRecyclerViewAdapter(it)
            viewBinding.recyclerViewWeather.adapter = adapter
            viewBinding.toolbarTitle.text = it.city.name
            viewBinding.indicatorProgress.isVisible = false
        }
    }


    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {

    }
}