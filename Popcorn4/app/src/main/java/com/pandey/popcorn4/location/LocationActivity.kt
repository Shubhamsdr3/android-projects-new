package com.pandey.popcorn4.location

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.pandey.popcorn4.R
import com.pandey.popcorn4.base.BaseActivity
import kotlinx.android.synthetic.main.activity_location.*

class LocationActivity: BaseActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (!(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            fusedLocationClient.lastLocation.addOnSuccessListener {
                if (it != null) {
                    latitude_value.text = it.latitude.toString()
                    longitude_value.text = it.longitude.toString()
                }
            }
        }
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_location
    }
}