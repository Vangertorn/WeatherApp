package com.example.weatherapp.screen

import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentActivityBinding
import com.example.weatherapp.support.SupportFragmentInset
import by.kirich1409.viewbindingdelegate.viewBinding

class MainFragment:SupportFragmentInset<FragmentActivityBinding>(R.layout.fragment_activity) {
    override val viewBinding: FragmentActivityBinding by viewBinding()
















    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {

    }


}