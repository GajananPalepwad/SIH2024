package com.plcoding.backgroundlocationtracking.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.plcoding.backgroundlocationtracking.R
import com.plcoding.backgroundlocationtracking.components.AppBottomBar
import com.plcoding.backgroundlocationtracking.navigation.Screen

@Composable
fun MainScaffoldScreen(
    navController: NavController
){
    var currentScreen by rememberSaveable { mutableStateOf(Screen.HomeScreen) }
    Scaffold(
        topBar = {
            //TODO: Add top bar
            ProfileSection()
        },
        containerColor = MaterialTheme.colorScheme.primary,
        bottomBar = {
            AppBottomBar(onItemSelected = { screen ->
                currentScreen = screen
            })
        }
    ) { paddingValues ->
        when (currentScreen) {
            Screen.HomeScreen -> HomeScreen(navController, paddingValues)
            Screen.AttendanceScreen -> AttendanceScreen(navController = navController)
            Screen.AnalyticsScreen -> AnalyticsScreen(navController = navController)
            Screen.SettingsScreen -> SettingsScreen(navController)
            else -> HomeScreen(navController, paddingValues)
        }
    }
}

@Composable
fun ProfileSection() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(start = 16.dp, end = 16.dp, top = 30.dp, bottom = 16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.profile), // Replace with actual profile image
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .border(1.dp, MaterialTheme.colorScheme.onSecondary, CircleShape)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text("Gajanan Palepwad", style = typography.bodyLarge, color = MaterialTheme.colorScheme.onSecondary)
            Text("Head of UX Design", style = typography.bodySmall, color = MaterialTheme.colorScheme.onSecondary)
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { /* Notification Click */ }) {
            Icon(
                Icons.Default.Notifications,
                contentDescription = "Notifications",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}