package com.randomx.travel.activity.tours

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.randomx.travel.R
import com.randomx.travel.activity.BaseActivity
import com.randomx.travel.activity.error.LocationErrorActivity
import com.randomx.travel.databinding.ActivityToursHomeBinding
import com.randomx.travel.model.LocationModel
import com.randomx.travel.network.ApiResponse
import com.randomx.travel.utils.ToolsUtils
import kotlinx.coroutines.runBlocking

class ToursHomeActivity : BaseActivity() {

    private lateinit var binding: ActivityToursHomeBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var locationExist: Boolean = false
    private var locationLat: Double = 0.0
    private var locationLon: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityToursHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root.findViewById<TextView>(R.id.toolbar_title).text = getString(R.string.tours_title)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        loadLocation()

    }


    private fun checkGranted(permission: String) = ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
    private fun checkPermissions() = checkGranted(android.Manifest.permission.ACCESS_COARSE_LOCATION) && checkGranted(android.Manifest.permission.ACCESS_FINE_LOCATION)
    private fun requestPermissions()
    {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||  locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER)
    }

    @SuppressLint("MissingPermission")
    private fun loadLocation()
    {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
                    if (location !== null) {
                        locationLat = location.latitude
                        locationLon = location.longitude
                        locationExist = true
                        val locationDetails = getLocation()
                        ToolsUtils.toast(this, "Hello from ${locationDetails.locationCity}, ${locationDetails.locationCountry}!! ($locationLat $locationLon)")
                    }
                    else
                    {
                        locationExist = false
                        ToolsUtils.goToActivity(this, LocationErrorActivity::class.java, null, true)
                    }

                }
            }
            else{
                ToolsUtils.goToActivity(this, LocationErrorActivity::class.java, null, true)
            }
        }
        else
        {
            requestPermissions()
        }
    }

    private fun getLocation(): LocationModel = runBlocking {
        val response: ApiResponse<LocationModel> = apiLocation().get(locationLat.toString(), locationLon.toString())
        return@runBlocking response.data?: LocationModel("", "", "", "", "", "")
    }

}