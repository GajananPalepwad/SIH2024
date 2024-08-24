package com.plcoding.backgroundlocationtracking.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.benchmark.perfetto.Row
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.plcoding.backgroundlocationtracking.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.plcoding.backgroundlocationtracking.ui.theme.DarkGray
import com.plcoding.backgroundlocationtracking.ui.theme.LightGray
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScheduleScreen(navController: NavController) {
    DashboardScreen()
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DashboardScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Calendar Header
        CalendarHeader()
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            shape = RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp),
            modifier = Modifier
                .fillMaxSize(),
            backgroundColor = MaterialTheme.colorScheme.surface
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                // Attendance Section
                AttendanceSection()

                Spacer(modifier = Modifier.height(16.dp))

                // Tasks Section
                TasksSection()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarHeader() {
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(
                ImageVector.vectorResource(R.drawable.menu_ic),
                contentDescription = "Menu",
                tint = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(43.dp)
                    .border(1.dp, MaterialTheme.colorScheme.onBackground, CircleShape)
                    .padding(8.dp)
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "Mar 2023",
                    color = MaterialTheme.colorScheme.onSecondary,
                    fontWeight = FontWeight.SemiBold,
                )
                Text("Wed, 8 March 2023", color = Color.Gray)
            }
            Icon(
                ImageVector.vectorResource(R.drawable.calendar2_ic),
                contentDescription = "Calendar",
                tint = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(43.dp)
                    .border(1.dp, MaterialTheme.colorScheme.onBackground, CircleShape)
                    .padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Calendar Row
        DynamicCalendar()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DynamicCalendar() {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    // Calculate the middle index to center the current date
    val startIndex = Int.MAX_VALUE / 2

    // Scroll state for LazyRow
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = startIndex - 3)

    LazyRow(
        state = listState, // Set the initial scroll position to center the current date
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(Int.MAX_VALUE) { index ->
            // Calculate the date based on the index
            val date = LocalDate.now().plusDays(index - startIndex.toLong())
            DateCircle(
                day = date.dayOfMonth.toString(),
                label = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                isSelected = date == selectedDate,
                onDateSelected = { selectedDate = date }
            )
        }
    }
}

@Composable
fun DateCircle(day: String, label: String, isSelected: Boolean = false, onDateSelected: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent)
                .clickable(onClick = onDateSelected)
        ) {
            Text(
                text = day,
                color = if (isSelected) MaterialTheme.colorScheme.onPrimary else Color.Gray
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun AttendanceSection() {
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Your Attendance",
                color = MaterialTheme.colorScheme.onSecondary,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f),
                fontSize = 16.sp
            )
            Text("See more", color = MaterialTheme.colorScheme.primary)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            AttendanceCard(
                "Check In",
                ImageVector.vectorResource(R.drawable.check_in),
                "07:30",
                "On Time",
                isCheckedIn = true,
                Modifier
                    .padding(0.dp, 8.dp, 4.dp, 8.dp)
                    .weight(1f)
            )
            AttendanceCard(
                "Check Out",
                ImageVector.vectorResource(R.drawable.check_out),
                "Not Yet",
                "Start at 17:00",
                isCheckedIn = false,
                Modifier
                    .padding(4.dp, 8.dp, 0.dp, 8.dp)
                    .weight(1f)
            )
        }
    }
}

@Composable
fun AttendanceCard(
    label: String,
    icon: ImageVector,
    time: String,
    status: String,
    isCheckedIn: Boolean,
    modifier: Modifier
) {
    Card(
        backgroundColor = MaterialTheme.colorScheme.background,
        shape = RoundedCornerShape(16.dp),
        modifier = modifier,
        elevation = 0.dp
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    icon,
                    contentDescription = label,
                    tint = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.size(22.dp)
                )

                Text(
                    label,
                    color = MaterialTheme.colorScheme.onSecondary,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                time, color = MaterialTheme.colorScheme.onSecondary,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                status, color = Color.Gray,
                modifier = Modifier.align(Alignment.Start)
            )
        }
    }
}

@Composable
fun TasksSection() {
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Your Tasks", color = MaterialTheme.colorScheme.onSecondary,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f),
                fontSize = 16.sp
            )
            Text("See more", color = MaterialTheme.colorScheme.primary)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            backgroundColor = MaterialTheme.colorScheme.background,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth(),
            elevation = 0.dp
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row {
                    Text(
                        "UX Research Audit",
                        color = MaterialTheme.colorScheme.onSecondary,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.weight(1f),
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        Icons.Default.MoreVert,
                        contentDescription = "More",
                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "We will research competitors and user needs by deep interviewing them.",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 14.sp
                )

                HorizontalDivider(
                    color = MaterialTheme.colorScheme.onSecondary,
                    thickness = 0.5.dp,
                    modifier = Modifier.padding(vertical = 15.dp)
                )


                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row {
                        Image(
                            painter = painterResource(id = R.drawable.profile),
                            contentDescription = "User 1",
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .border(1.dp, Color.White, CircleShape)

                        )
                        // Add other avatars similarly
                    }
                    Row {
                        Icon(
                            ImageVector.vectorResource(R.drawable.comment_ic),
                            contentDescription = "Comments",
                            tint = MaterialTheme.colorScheme.onSecondary
                        )

                        Text(
                            text = "2",
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(start = 4.dp),
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Icon(
                            ImageVector.vectorResource(R.drawable.attach_ic),
                            contentDescription = "Attachments",
                            tint = MaterialTheme.colorScheme.onSecondary,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                        Text(
                            text = "2",
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(start = 4.dp),
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                }
            }
        }
    }
}
