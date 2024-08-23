package com.plcoding.backgroundlocationtracking

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.rememberNavController
import com.plcoding.backgroundlocationtracking.navigation.Navigation
import com.plcoding.backgroundlocationtracking.ui.theme.BackgroundLocationTrackingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
            ),
            0
        )
        setContent {
            BackgroundLocationTrackingTheme {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
//////////////////////////////////Buttons to start and end background services////////////////////////////////////
//                    Row {
//                        Button(onClick = {
//                            Intent(applicationContext, LocationService::class.java).apply {
//                                action = LocationService.ACTION_START
//                                startService(this)
//                            }
//                        }) { Text(text = "Start") }
//                        Button(onClick = {
//                            Intent(applicationContext, LocationService::class.java).apply {
//                                action = LocationService.ACTION_STOP
//                                startService(this)
//                            }
//                        }) { Text(text = "Stop") }
//                    }

                    Navigation(navController = rememberNavController())
                }

            }
        }
    }

    }


