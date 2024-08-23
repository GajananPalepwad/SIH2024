package com.plcoding.backgroundlocationtracking.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.plcoding.backgroundlocationtracking.R
import com.plcoding.backgroundlocationtracking.ui.theme.appColor
import com.plcoding.backgroundlocationtracking.ui.theme.appColorTransparent

@Composable
fun HomeScreen(navController: NavController, paddingValues: PaddingValues) {
    val typography = MaterialTheme.typography
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(MaterialTheme.colorScheme.background),
    ) {
        // Top Profile Section

        Spacer(modifier = Modifier.height(16.dp))

        // Attendance Details Section
        AttendanceDetailsSection(typography)
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

            Spacer(modifier = Modifier.height(10.dp))

            // Attendances Section
            Text("Your Attendances", style = typography.bodyLarge, color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.padding(8.dp))
            CheckInCard("Check In", "07:47", "August 21, 2024", "Early 13 mins")
        }
    }
}

@Composable
fun AttendanceSummary() {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row {
            val modifier = Modifier
                .weight(1f)
                .padding(5.dp, 8.dp, 5.dp, 0.dp)
            AttendanceInfoCard("08:46", "Checked In", ImageVector.vectorResource(R.drawable.check_in), modifier)
            AttendanceInfoCard("17:15", "Checked Out", ImageVector.vectorResource(R.drawable.check_out), modifier)
        }
        Row {
            val modifier = Modifier
                .weight(1f)
                .padding(5.dp, 10.dp, 5.dp, 0.dp)
            AttendanceInfoCard("96%", "On Time", ImageVector.vectorResource(R.drawable.tick_ic), modifier)
            AttendanceInfoCard("28 days", "Total Attended", ImageVector.vectorResource(R.drawable.calendar_ic), modifier)
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
        Text("Today Tasks", style = typography.bodyLarge, color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.padding(8.dp))
        Text("See more", style = typography.bodySmall, color = appColor, modifier = Modifier.padding(8.dp))
    }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp, 0.dp, 3.dp, 0.dp)
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
            MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(15.dp),
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(time, color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.bodyMedium)
                Icon(icon, contentDescription = label, tint = MaterialTheme.colorScheme.onBackground)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(label, color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun TaskCard(title: String, time: String, progress: Float, modifier: Modifier) {
    Card(
        colors = CardDefaults.cardColors(
            MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(15.dp),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(title, color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.bodyLarge, maxLines = 1)
            Spacer(modifier = Modifier.height(8.dp))
            Text(time, color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(16.dp))
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier.fillMaxWidth(),
                color = appColor,
            )
        }
    }
}

@Composable
fun CheckInCard(title: String, time: String, date: String, sideText: String) {
    Card(
        colors = CardDefaults.cardColors(
            MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier.fillMaxWidth()
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
                colors = CardDefaults.cardColors(appColorTransparent),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.check_in),
                    contentDescription = "Check In Icon",
                    tint = appColor,
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
                        text = title,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Text(
                        text = date,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color(0xFFA3A3A3),
                            fontSize = 14.sp
                        )
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = time,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = appColor,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp
                        ),
                        textAlign = TextAlign.Right
                    )
                    Text(
                        text = sideText,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color(0xFFA3A3A3),
                            fontSize = 14.sp
                        )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AttendanceScreenPreview() {
    AttendanceScreen(navController = NavController(LocalContext.current))
}
