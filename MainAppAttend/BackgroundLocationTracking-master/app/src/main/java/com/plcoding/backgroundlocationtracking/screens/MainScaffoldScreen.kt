package com.plcoding.backgroundlocationtracking.screens

import android.app.Activity
import android.os.Build
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.plcoding.backgroundlocationtracking.components.AppBottomBar
import com.plcoding.backgroundlocationtracking.data.PreferenceHelper
import com.plcoding.backgroundlocationtracking.navigation.Screen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScaffoldScreen(
    navController: NavController,
    preferenceHelper: PreferenceHelper
) {
    val context = LocalContext.current
    var currentScreen by rememberSaveable { mutableStateOf(Screen.HomeScreen) }
    BackHandler {
        // Exit the app when back button is pressed

        (context as? Activity)?.finish()
    }

    Log.d("Preference Data", "User ID: ${preferenceHelper.userId}, " +
            "Name: ${preferenceHelper.name}, " +
            "Email: ${preferenceHelper.email}" +
            "latitude: ${preferenceHelper.latitude}" +
            "longitude: ${preferenceHelper.longitude}")
    Scaffold(
//        topBar = {
//            //TODO: Add top bar
//            ProfileSection()
//        },
        containerColor = MaterialTheme.colorScheme.primary,
        bottomBar = {
            AppBottomBar(onItemSelected = { screen ->
                currentScreen = screen
            })
        }
    ) { paddingValues ->
        when (currentScreen) {
            Screen.HomeScreen -> HomeScreen(navController, paddingValues, preferenceHelper = preferenceHelper)
            Screen.ScheduleScreen -> ScheduleScreen(navController = navController)
            Screen.AnalyticsScreen -> AnalyticsScreen(navController = navController, paddingValues)
            Screen.SettingsScreen -> SettingsScreen(navController, paddingValues)
            else -> HomeScreen(navController, paddingValues, preferenceHelper)
        }
    }
}
