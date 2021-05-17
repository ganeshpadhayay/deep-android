package com.example.ganesh.deep_android.ktx

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.example.ganesh.deep_android.R
import com.example.ganesh.myktxlibrary.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.launch

class KTXExampleActivity : AppCompatActivity() {

    companion object {
        const val TAG = "KTXCODELAB"
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_k_t_x_example)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        startUpdatingLocation()
    }

    override fun onStart() {
        super.onStart()
        if (!hasPermission(ACCESS_FINE_LOCATION)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(ACCESS_FINE_LOCATION), 0)
            }
        }

        lifecycleScope.launch {
            getLastKnownLocation()
        }
    }

    private suspend fun getLastKnownLocation() {
        try {
            val lastLocation = fusedLocationClient.awaitLastLocation()
            showLocation(R.id.textView, lastLocation)
        } catch (e: Exception) {
            findAndSetText(R.id.textView, "Unable to get location.")
            Log.d(TAG, "Unable to get location", e)
        }
    }

    private fun startUpdatingLocation() {
        fusedLocationClient.locationFlow()
                .conflate()
                .catch { e ->
                    findAndSetText(R.id.textView, "Unable to get location.")
                    Log.d(TAG, "Unable to get location", e)
                }
                .asLiveData()
                .observe(this, Observer { location ->
                    showLocation(R.id.textView, location)
                    Log.d(TAG, location.toString())
                })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            recreate()
        }
    }
}