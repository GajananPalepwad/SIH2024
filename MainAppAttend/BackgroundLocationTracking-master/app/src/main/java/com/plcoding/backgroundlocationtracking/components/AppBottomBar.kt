package com.plcoding.backgroundlocationtracking.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.plcoding.backgroundlocationtracking.R
import com.plcoding.backgroundlocationtracking.navigation.Screen
import com.plcoding.backgroundlocationtracking.ui.theme.LightGray

@Composable
fun AppBottomBar(onItemSelected: (Screen) -> Unit) {
    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }
    //TODO Add selected and unselected colors
    val items = listOf(
        BottomNavigationItem(
            title = "Home",
            selectedIcon = ImageVector.vectorResource(R.drawable.filled_home_ic),
            unselectedIcon = ImageVector.vectorResource(R.drawable.home_ic),
            route = Screen.HomeScreen
        ),
        BottomNavigationItem(
            title = "Schedule",
            selectedIcon = ImageVector.vectorResource(R.drawable.filled_calendar_ic),
            unselectedIcon = ImageVector.vectorResource(R.drawable.calendar_ic),
            route = Screen.ScheduleScreen
        ),
        BottomNavigationItem(
            title = "Aanalytics",
            selectedIcon = ImageVector.vectorResource(R.drawable.filled_analytics_ic),
            unselectedIcon = ImageVector.vectorResource(R.drawable.analytics_ic),
            route = Screen.AnalyticsScreen
        ),
        BottomNavigationItem(
            title = "Settings",
            selectedIcon = ImageVector.vectorResource(R.drawable.filled_settings_ic),
            unselectedIcon = ImageVector.vectorResource(R.drawable.settings_ic),
            route = Screen.SettingsScreen
        )
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .height(70.dp)
            .border(2.dp, Color.Black)) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                    onItemSelected(item.route)
                },
                icon = {
                    Icon(
                        imageVector = if (index == selectedItemIndex) item.selectedIcon else item.unselectedIcon,
                        contentDescription = "${item.title} Button",
                        modifier = Modifier.requiredSize(25.dp)
                    )
                },
                label = { Text(text = item.title, color = MaterialTheme.colorScheme.onSecondary)},
                colors = NavigationBarItemColors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.onSecondary,
                    selectedIndicatorColor = MaterialTheme.colorScheme.tertiary,
                    unselectedIconColor = MaterialTheme.colorScheme.onBackground,
                    unselectedTextColor = MaterialTheme.colorScheme.onBackground,
                    disabledIconColor = MaterialTheme.colorScheme.tertiary,
                    disabledTextColor = MaterialTheme.colorScheme.tertiary
                )
            )
        }
    }
}

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: Screen
)

@Preview
@Composable
fun BottomBarPreview(){
    AppBottomBar({})
}