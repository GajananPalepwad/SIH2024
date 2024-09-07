package com.plcoding.backgroundlocationtracking.screens

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.plcoding.backgroundlocationtracking.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnalyticsScreen(navController: NavController, paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(paddingValues),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val pagerState = rememberPagerState(0, pageCount = { 2 })

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            when (page) {
                0 -> WeeklyBarChart()
                1 -> MonthlyBarChart()
            }
        }

    }
}

@Composable
fun BarChart(
    modifier: Modifier = Modifier,
    data: List<Pair<String, Float>>,
    maxValue: Float,
    label: String
) {
        Column (
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Canvas(modifier = modifier) {
                val barWidth = size.width / data.size
                val maxBarHeight = size.height

                data.forEachIndexed { index, item ->
                    val barHeight = (item.second / maxValue) * maxBarHeight
                    val xOffset = index * barWidth

                    // Draw bar with border
                    drawRect(
                        color = Color(0xFF00D1B2),
                        topLeft = Offset(xOffset.toFloat() + barWidth / 4, maxBarHeight - barHeight),
                        size = Size(barWidth / 2, barHeight)
                    )

                    // Draw text labels
                    drawContext.canvas.nativeCanvas.apply {
                        val textPaint = Paint().apply {
                            color = android.graphics.Color.GRAY
                            textSize = 36f
                            textAlign = Paint.Align.LEFT
                        }
                        drawText(item.first, xOffset + barWidth / 4f, maxBarHeight + 40f, textPaint)
                        drawText(
                            item.second.toInt().toString(),
                            xOffset + barWidth / 4f,
                            maxBarHeight - barHeight - 20f,
                            textPaint
                        )
                    }
                }


            }
            Text(text = label,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSecondary,
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                fontWeight = FontWeight.Bold)
        }



}

@Composable
private fun WeeklyBarChart() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = "Weekly Attendance",
            color = MaterialTheme.colorScheme.onSecondary,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp)
        )

        Card(
            shape = RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp),
            modifier = Modifier
                .fillMaxSize()
        ) {

            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .fillMaxSize()
                    .padding(12.dp)
            ) {

                Card(
                    colors = CardDefaults.cardColors(
                        MaterialTheme.colorScheme.background
                    ),
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier.padding(5.dp, 8.dp, 5.dp, 0.dp)
                ) {
                    BarChart(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(20.dp),
                        data = listOf(
                            "Mon" to 8f,
                            "Tue" to 9f,
                            "Wed" to 7f,
                            "Thu" to 10f,
                            "Fri" to 6f,
                            "Sat" to 8f
                        ),
                        maxValue = 10f,
                        label = "Number of Hours"
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Today",
                    color = MaterialTheme.colorScheme.onSecondary,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 10.dp)
                )
                TaskCard(
                    "UX Research Audit", "09:30 - 10:30", 0.8f, modifier = Modifier
                        .weight(1f)
                        .padding(5.dp, 8.dp, 5.dp, 8.dp)
                )
                AttendanceInfoCard(
                    "1",
                    "Leaves Taken",
                    ImageVector.vectorResource(R.drawable.check_out),
                    modifier = Modifier.padding(5.dp, 8.dp, 5.dp, 8.dp)
                )

                Row {
                    AttendanceInfoCard(
                        "75.58%",
                        "Current Week",
                        ImageVector.vectorResource(R.drawable.calendar_ic),
                        modifier = Modifier
                            .weight(1f)
                            .padding(5.dp, 8.dp, 5.dp, 0.dp)
                    )
                    AttendanceInfoCard(
                        "95%",
                        "Last Week",
                        ImageVector.vectorResource(R.drawable.calendar_ic),
                        modifier = Modifier
                            .weight(1f)
                            .padding(5.dp, 8.dp, 5.dp, 0.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun MonthlyBarChart() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = "Monthly Attendance",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSecondary,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp)
        )

        Card(
            shape = RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp),
            modifier = Modifier
                .fillMaxSize()
        ) {

            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .fillMaxSize()
                    .padding(12.dp)
            ) {

                Card(
                    colors = CardDefaults.cardColors(
                        MaterialTheme.colorScheme.background
                    ),
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier.padding(5.dp, 8.dp, 5.dp,8.dp)
                ) {
                    BarChart(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp, horizontal = 10.dp)
                            .height(300.dp)
                            ,
                        data = listOf(
                            "Jan" to 20f,
                            "Feb" to 15f,
                            "Mar" to 25f,
                            "Apr" to 18f,
                            "May" to 22f,
                            "Jun" to 19f,
                            "Jul" to 14f,
                            "Aug" to 21f,
                            "Sep" to 17f,
                            "Oct" to 24f,
                            "Nov" to 28f,
                            "Dec" to 30f
                        ),
                        maxValue = 31f,
                        label = "Number of Days"
                    )
                }

                AttendanceInfoCard(
                    "5",
                    "Leaves Taken",
                    ImageVector.vectorResource(R.drawable.check_out),
                    modifier = Modifier.padding(5.dp, 8.dp, 5.dp, 8.dp)
                )

                Row {
                    AttendanceInfoCard(
                        "50.58%",
                        "Current Month",
                        ImageVector.vectorResource(R.drawable.calendar_ic),
                        modifier = Modifier
                            .weight(1f)
                            .padding(5.dp, 8.dp, 5.dp, 0.dp)
                    )
                    AttendanceInfoCard(
                        "88%",
                        "Last Month",
                        ImageVector.vectorResource(R.drawable.calendar_ic),
                        modifier = Modifier
                            .weight(1f)
                            .padding(5.dp, 8.dp, 5.dp, 0.dp)
                    )
                }
            }
        }
    }
}
