package com.plcoding.backgroundlocationtracking.screens

import android.app.Activity
import android.os.Build
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.plcoding.backgroundlocationtracking.R
import com.plcoding.backgroundlocationtracking.components.AppBottomBar
import com.plcoding.backgroundlocationtracking.data.PreferenceHelper
import com.plcoding.backgroundlocationtracking.navigation.Screen
import com.plcoding.backgroundlocationtracking.ui.theme.LightGray

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScaffoldScreen(
    navController: NavController
) {
    val context = LocalContext.current
    var currentScreen by rememberSaveable { mutableStateOf(Screen.HomeScreen) }
    val preferenceHelper = PreferenceHelper(context)
    BackHandler {
        // Exit the app when back button is pressed
            (context as? Activity)?.finish()
    }
    Log.d("Preference Data", "User ID: ${preferenceHelper.userId}, Name: ${preferenceHelper.name}, Email: ${preferenceHelper.email}")
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
            Screen.HomeScreen -> HomeScreen(navController, paddingValues)
            Screen.ScheduleScreen -> ScheduleScreen(navController = navController)
            Screen.AnalyticsScreen -> AnalyticsScreen(navController = navController, paddingValues)
            Screen.SettingsScreen -> SettingsScreen(navController,paddingValues)
            else -> HomeScreen(navController, paddingValues)
        }
    }
}
