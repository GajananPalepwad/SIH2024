package com.plcoding.backgroundlocationtracking.screens

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
fun AnalyticsScreen(navController: NavController){
    DashboardScreen1()
}

@Composable
fun BarChart(modifier: Modifier = Modifier, data: List<Pair<String, Float>>, maxValue: Float, label: String) {
    Canvas(modifier = modifier) {
        val barHeight = size.height / data.size.toFloat()
        val maxBarWidth = size.width

        data.forEachIndexed { index, item ->
            val barWidth = (item.second / maxValue) * maxBarWidth
            val yOffset = index * barHeight

            // Draw bar
            drawRect(
                color = Color(0xFF00D1B2),
                topLeft = Offset(0f, yOffset + barHeight / 4),
                size = Size(barWidth, barHeight / 2),
            )

            // Draw text labels
            drawContext.canvas.nativeCanvas.apply {
                val textPaint = Paint().apply {
                    color = android.graphics.Color.WHITE
                    textSize = 36f
                }
                drawText(item.first, 0f, yOffset + barHeight / 2, textPaint)
                drawText(item.second.toString(), barWidth + 10, yOffset + barHeight / 2, textPaint)
            }
        }

        // Draw axis labels
        drawContext.canvas.nativeCanvas.apply {
            val textPaint = Paint().apply {
                color = android.graphics.Color.WHITE
                textSize = 36f
            }
            drawText(label, maxBarWidth / 2 - textPaint.measureText(label) / 2, size.height + 40, textPaint)
        }
    }
}

@Composable
fun DashboardScreen1() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {

        Card(
            colors = CardDefaults.cardColors(
                MaterialTheme.colorScheme.background
            ),
            shape = RoundedCornerShape(15.dp),
        ) {
            BarChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp).padding(20.dp),
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


        Card(
            colors = CardDefaults.cardColors(
                MaterialTheme.colorScheme.background
            ),
            shape = RoundedCornerShape(15.dp),
        ) {
            BarChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp).padding(20.dp),
                data = listOf(
                    "Jan" to 20f,
                    "Feb" to 15f,
                    "Mar" to 25f,
                    "Apr" to 18f,
                    "May" to 22f,
                    "June" to 19f,
                    "July" to 14f,
                    "Aug" to 21f,
                    "Sept" to 17f,
                    "Oct" to 24f,
                    "Nov" to 28f,
                    "Dec" to 30f
                ),
                maxValue = 30f,
                label = "Number of Days"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    DashboardScreen1()
}
