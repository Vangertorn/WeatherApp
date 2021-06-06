package com.example.weatherapp.screen.main_fragment

import android.location.Location
import com.example.weatherapp.repository.CloudRepository
import com.example.weatherapp.support.CoroutineViewModel

class MainFragmentViewModel(private val cloudRepository: CloudRepository) : CoroutineViewModel() {

    fun saveLocation(locationResult: Location) {
        cloudRepository.current_location = locationResult
        }
    }

