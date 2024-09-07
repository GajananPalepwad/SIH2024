package com.plcoding.backgroundlocationtracking.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.plcoding.backgroundlocationtracking.R

@Composable
fun HomeScreen(navController: NavController, paddingValues: PaddingValues) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(MaterialTheme.colorScheme.background),
    ) {
        item {
            // Top Profile Section
            ProfileSection()
            Spacer(modifier = Modifier.height(16.dp))

            // Attendance Details Section
            AttendanceDetailsSection(typography)

        }
    }
}

@Composable
fun MapScreen() {
    val location = LatLng(19.113560, 77.288187)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(location, 15f)
    }

    val isDarkTheme = isSystemInDarkTheme()
    val mapStyle = if (isDarkTheme) R.raw.map_dark_style else R.raw.map_light_style

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)  // Fixed height for the map
            .padding(vertical = 16.dp, horizontal = 8.dp)
    ) {
        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
                .border(2.dp, MaterialTheme.colorScheme.background, RoundedCornerShape(16.dp)),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(
                mapStyleOptions = MapStyleOptions.loadRawResourceStyle(
                    LocalContext.current, mapStyle
                )
            )
        ) {
            Marker(state = MarkerState(position = location))
            Circle(
                center = location,
                radius = 200.0,  // Radius in meters
                strokeColor = MaterialTheme.colorScheme.primary,
                strokeWidth = 2f,
                fillColor = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}


@Composable
fun AttendanceDetailsSection(typography: androidx.compose.material3.Typography) {
    Card(
        shape = RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxSize()
                .padding(12.dp)
        ) {
            AttendanceSummary()

            Spacer(modifier = Modifier.height(16.dp))

            // Today Tasks Section
            TodayTasksSection(typography)
            // Map Screen Section
            MapScreen()  // Call the updated MapScreen
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
//            .background(LightGray)
            .padding(start = 16.dp, end = 16.dp, top = 10.dp, bottom = 5.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.profile), // Replace with actual profile image
            contentDescription = "Profile Picture",
            contentScale = androidx.compose.ui.layout.ContentScale.Crop,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .border(1.dp, MaterialTheme.colorScheme.onSecondary, CircleShape)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.weight(1f), verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "Gajanan Palepwad",
                style = typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSecondary,
                fontSize = 19.sp
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                "Head of UX Design",
                style = typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        IconButton(onClick = { /* Notification Click */ }) {
            Icon(
                ImageVector.vectorResource(R.drawable.bell_ic),
                contentDescription = "Notifications",
                tint = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier
                    .clip(CircleShape)
                    .border(1.dp, MaterialTheme.colorScheme.onBackground, CircleShape)
                    .padding(10.dp)
                    .size(55.dp)
            )
        }
    }
}

@Composable
fun AttendanceSummary() {
    Column(
        verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
    ) {
        Row {
            val modifier = Modifier
                .weight(1f)
                .padding(5.dp, 8.dp, 5.dp, 0.dp)
            AttendanceInfoCard(
                "08:46", "Checked In", ImageVector.vectorResource(R.drawable.check_in), modifier
            )
            AttendanceInfoCard(
                "17:15", "Checked Out", ImageVector.vectorResource(R.drawable.check_out), modifier
            )
        }
        Row {
            val modifier = Modifier
                .weight(1f)
                .padding(5.dp, 10.dp, 5.dp, 0.dp)
            AttendanceInfoCard(
                "96%", "On Time", ImageVector.vectorResource(R.drawable.tick_ic), modifier
            )
            AttendanceInfoCard(
                "28 days",
                "Total Attended",
                ImageVector.vectorResource(R.drawable.calendar_ic),
                modifier
            )
        }
    }
}

@Composable
fun TodayTasksSection(typography: androidx.compose.material3.Typography) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            "Today Tasks",
            color = MaterialTheme.colorScheme.onSecondary,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(8.dp)
                .weight(1f),
            fontSize = 16.sp
        )
        Text(
            "See more",
            style = typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(8.dp),
            fontWeight = FontWeight.SemiBold
        )
    }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
    ) {
        val modifier = Modifier
            .weight(1f)
            .padding(5.dp, 8.dp, 5.dp, 8.dp)
        TaskCard("UX Research Audit", "09:30 - 10:30", 0.8f, modifier)
        TaskCard("Deep Ideation", "11:00 - 13:30", 0.48f, modifier)
    }
}

@Composable
fun AttendanceInfoCard(time: String, label: String, icon: ImageVector, modifier: Modifier) {
    Card(
        colors = CardDefaults.cardColors(
            MaterialTheme.colorScheme.background
        ), shape = RoundedCornerShape(15.dp), modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    time,
                    color = MaterialTheme.colorScheme.onSecondary,
                    style = typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                )
                Icon(
                    icon,
                    contentDescription = label,
                    tint = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.size(30.dp)
                )
            }
            Text(
                label,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 15.sp,
                modifier = Modifier.align(Alignment.Start)
            )
        }
    }
}

@Composable
fun TaskCard(title: String, time: String, progress: Float, modifier: Modifier) {
    Card(
        colors = CardDefaults.cardColors(
            MaterialTheme.colorScheme.background
        ), shape = RoundedCornerShape(15.dp), modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                title,
                color = MaterialTheme.colorScheme.onSecondary,
                style = typography.bodyLarge,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                time,
                color = MaterialTheme.colorScheme.onBackground,
                style = typography.bodySmall,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@Composable
fun CheckInCard(title: String, time: String, date: String, sideText: String) {
    Card(
        colors = CardDefaults.cardColors(
            MaterialTheme.colorScheme.background
        ), shape = RoundedCornerShape(15.dp), modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 10.dp, 16.dp, 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Card(
                shape = CircleShape,
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiary),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.check_in),
                    contentDescription = "Check In Icon",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(8.dp)
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = title, style = typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onSecondary,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Text(
                        text = date, style = typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onBackground, fontSize = 14.sp
                        )
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = time, style = typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp
                        ), textAlign = TextAlign.Right
                    )
                    Text(
                        text = sideText, style = typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onBackground, fontSize = 14.sp
                        )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun AttendanceScreenPreview() {
    val typography = MaterialTheme.typography
    AttendanceDetailsSection(typography)
}
