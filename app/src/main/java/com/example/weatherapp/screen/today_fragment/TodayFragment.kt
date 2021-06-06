package com.example.weatherapp.screen.today_fragment

import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentTodayBinding
import com.example.weatherapp.support.SupportFragmentInset

class TodayFragment: SupportFragmentInset<FragmentTodayBinding>(R.layout.fragment_today) {
    override val viewBinding: FragmentTodayBinding by viewBinding()



















    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {

    }
}