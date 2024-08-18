package com.plcoding.backgroundlocationtracking

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.plcoding.backgroundlocationtracking.locationClasses.LocationService
import com.plcoding.backgroundlocationtracking.ui.theme.BackgroundLocationTrackingTheme
import com.plcoding.backgroundlocationtracking.ui.theme.DarkGray
import com.plcoding.backgroundlocationtracking.ui.theme.LightGray
import com.plcoding.backgroundlocationtracking.ui.theme.appColor

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
////////////////////////////////////Buttons to start and end background services////////////////////////////////////
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

                    AttendanceScreen()
                }
            }
        }
    }

    @Composable
    @Preview
    fun AttendanceScreen() {
        val typography = MaterialTheme.typography
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LightGray)
        ) {
            // Top Profile Section
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile), // Replace with actual profile image
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.White, CircleShape)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text("Gajanan Palepwad", style = typography.h6, color = Color.White)
                    Text("Head of UX Design", style = typography.body2, color = Color.Gray)
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { /* Notification Click */ }) {
                    Icon(
                        Icons.Default.Notifications,
                        contentDescription = "Notifications",
                        tint = Color.White
                    )
                }
            }

            // Attendance Details Section
            Card(shape = RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp), modifier = Modifier
                .fillMaxWidth()
                .height(0.dp)
                .weight(1f)) {
                Column (modifier = Modifier
                    .background(DarkGray)
                    .padding(12.dp)){
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row {
                            val modifier = Modifier
                                .width(0.dp)
                                .weight(1f)
                                .padding(5.dp, 8.dp, 5.dp, 0.dp)
                            AttendanceInfoCard("08:46", "Checked In", ImageVector.vectorResource(R.drawable.check_in), modifier)
                            AttendanceInfoCard("17:15", "Checked Out", ImageVector.vectorResource(R.drawable.check_out), modifier)
                        }
                        Row {
                            val modifier = Modifier
                                .width(0.dp)
                                .weight(1f)
                                .padding(5.dp, 10.dp, 5.dp, 0.dp)
                            AttendanceInfoCard("96%", "On Time", ImageVector.vectorResource(R.drawable.tick_ic), modifier)
                            AttendanceInfoCard("28 days", "Total Attended", ImageVector.vectorResource(R.drawable.calendar_ic), modifier)
                        }
                    }


                    Spacer(modifier = Modifier.height(16.dp))

                    // Today Tasks Section
                    Text("Today Tasks", style = typography.h6, color = Color.White, modifier = Modifier.padding(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth().padding(3.dp, 0.dp, 3.dp, 0.dp)
                    ) {
                        val modifier = Modifier
                            .fillMaxWidth().weight(1f)
                            .padding(5.dp, 8.dp, 5.dp, 8.dp)
                        TaskCard("UX Research Audit", "09:30 - 10:30", 0.8f, modifier)
                        TaskCard("Deep Ideation", "11:00 - 13:30", 0.48f, modifier)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Attendances Section
                    Text("Your Attendances", style = typography.h6, color = Color.White, modifier = Modifier.padding(8.dp))
                    // Implement attendances view here

                }
            }

        }
    }

    @Composable
    fun AttendanceInfoCard(time: String, label: String, icon: ImageVector, modifier: Modifier) {
        Card(
            backgroundColor = LightGray,
            shape = RoundedCornerShape(15.dp),
            modifier = modifier
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(10.dp, 10.dp, 10.dp, 10.dp).fillMaxWidth()
            ) {
                Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                    Text(time, color = Color.White, style = MaterialTheme.typography.h6, modifier = Modifier.align(Alignment.CenterVertically ))
                    Icon(icon, contentDescription = label, tint = Color.White)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(label, color = Color.Gray, style = MaterialTheme.typography.body2, modifier = Modifier.align(Alignment.Start))
            }
        }
    }

    @Composable
    fun TaskCard(title: String, time: String, progress: Float, modifier: Modifier) {
        Card(
            backgroundColor = LightGray,
            shape = RoundedCornerShape(15.dp),
            modifier = modifier
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(title, color = Color.White, style = MaterialTheme.typography.subtitle1, maxLines = 1)
                Spacer(modifier = Modifier.height(8.dp))
                Text(time, color = Color.Gray, style = MaterialTheme.typography.body2)
                Spacer(modifier = Modifier.height(16.dp))
                LinearProgressIndicator(
                    progress = progress,
                    color = appColor,
                    backgroundColor = Color(0xFF373737),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                )
            }
        }
    }

}