package com.example.weatherapp.screen.main_fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.viewpager.widget.ViewPager
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentActivityBinding
import com.example.weatherapp.support.SupportFragmentInset
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.weatherapp.adapter.ViewPagerAdapter
import com.example.weatherapp.screen.today_fragment.TodayFragment
import com.google.android.gms.location.*
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener


import org.koin.android.viewmodel.ext.android.viewModel

class MainFragment() :
    SupportFragmentInset<FragmentActivityBinding>(R.layout.fragment_activity),
    MultiplePermissionsListener {
    override val viewBinding: FragmentActivityBinding by viewBinding()
    private val viewModel: MainFragmentViewModel by viewModel()
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Dexter.withContext(this.context).withPermissions(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        ).withListener(this).check()
    }


    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        viewBinding.appBar.setPadding(0, top, 0, 0)
    }

    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
        if (report!!.areAllPermissionsGranted()) {
            buildLocationRequest()
            buildLocationCallBack()

            if (ActivityCompat.checkSelfPermission(
                    this.requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this.requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                return
            }
            fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(this.requireContext())
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.myLooper()!!
            )
        }
    }

    private fun buildLocationCallBack() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                super.onLocationResult(locationResult)
                viewModel.saveLocation(locationResult.lastLocation)
                setupViewPager(viewBinding.viewPager)
                viewBinding.tabs.setupWithViewPager(viewBinding.viewPager)

                Log.d(
                    "Location",
                    "${locationResult.lastLocation.latitude} + ${locationResult.lastLocation.longitude}"
                )
            }
        }
    }


    private fun buildLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 3000
        locationRequest.smallestDisplacement = 10.0f
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(requireActivity().supportFragmentManager)
        adapter.addFragment(TodayFragment(), "Today")
        viewPager.adapter = adapter

    }

    override fun onPermissionRationaleShouldBeShown(
        p0: MutableList<PermissionRequest>?,
        p1: PermissionToken?
    ) {
        Snackbar.make(viewBinding.rootView, "Permission Denied", Snackbar.LENGTH_LONG).show()
    }


}