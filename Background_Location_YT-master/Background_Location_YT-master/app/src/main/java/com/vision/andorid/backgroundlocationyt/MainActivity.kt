package com.vision.andorid.backgroundlocationyt

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.vision.andorid.backgroundlocationyt.databinding.ActivityMainBinding
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding!!


    private var service: Intent?=null

    private val backgroundLocation =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {

            }
        }

    private val locationPermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            when {
                it.getOrDefault(android.Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        if (ActivityCompat.checkSelfPermission(
                                this,
                                android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            backgroundLocation.launch(android.Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                        }
                    }

                }
                it.getOrDefault(android.Manifest.permission.ACCESS_FINE_LOCATION, false) -> {

                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        service = Intent(this,LocationService::class.java)

        binding.apply {
            btnStartLocationTracking.setOnClickListener {
                requestIgnoreBatteryOptimizations()
                checkPermissions()
            }

            btnRemoveLocationTracking.setOnClickListener {
                stopService(service)
            }
        }

    }

    fun requestIgnoreBatteryOptimizations() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val intent = Intent()
            val packageName = packageName
            val pm = getSystemService(PowerManager::class.java)

            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                intent.action = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
                intent.data = Uri.parse("package:$packageName")
                startActivity(intent)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this)
        }
    }

    fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                locationPermissions.launch(
                    arrayOf(
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }else{
                startService(service)
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val intent = Intent()
            val packageName = packageName
            val pm = getSystemService(POWER_SERVICE) as PowerManager
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                intent.action = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
                intent.data = Uri.parse("package:$packageName")
                startActivity(intent)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(service)
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this)
        }
    }

    @Subscribe
    fun receiveLocationEvent(locationEvent: LocationEvent){
        binding.tvLatitude.text = "Latitude -> ${locationEvent.latitude}"
        binding.tvLongitude.text = "Longitude -> ${locationEvent.longitude}"
    }

}


















