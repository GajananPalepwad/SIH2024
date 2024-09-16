package com.plcoding.backgroundlocationtracking.locationClasses

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.LocationServices
import com.plcoding.backgroundlocationtracking.R
import com.plcoding.backgroundlocationtracking.api.ApiService
import com.plcoding.backgroundlocationtracking.api.RetrofitClient
import com.plcoding.backgroundlocationtracking.apimodels.request.CheckInRequest
import com.plcoding.backgroundlocationtracking.apimodels.request.CheckOutRequest
import com.plcoding.backgroundlocationtracking.apimodels.response.ApiResponse
import com.plcoding.backgroundlocationtracking.data.PreferenceHelper
import com.plcoding.backgroundlocationtracking.screens.getUserDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.*

class LocationService : Service() {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var locationClient: LocationClient
    private lateinit var preferenceHelper: PreferenceHelper


    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        locationClient = DefaultLocationClient(
            applicationContext,
            LocationServices.getFusedLocationProviderClient(applicationContext)
        )
        preferenceHelper = PreferenceHelper(applicationContext)
        // Notification Channel creation for Oreo and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID, "Location Service", NotificationManager.IMPORTANCE_HIGH
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> start()
            ACTION_STOP -> stop()
        }
        return START_STICKY // This will restart the service if it is killed
    }

    private fun start() {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Tracking location...")
            .setContentText("Location: null")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setOngoing(true)
            .build()

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        locationClient
            .getLocationUpdates(10L)
            .catch { e -> e.printStackTrace() }
            .onEach { location ->
                val lat = location.latitude.toString()
                val long = location.longitude.toString()
                val updatedNotification = NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Tracking location...")
                    .setContentText("Location: ($lat, $long)")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setOngoing(true)
                    .build()
                // this will check 200 meter
                checkLocation(location.latitude, location.longitude)
                notificationManager.notify(NOTIFICATION_ID, updatedNotification)
            }
            .launchIn(serviceScope)

        startForeground(NOTIFICATION_ID, notification)
    }

    private fun stop() {
        stopForeground(true)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()

        // Restart the service when destroyed
        val restartServiceIntent = Intent(applicationContext, this::class.java).also {
            it.setPackage(packageName)
        }
        val restartServicePendingIntent = PendingIntent.getService(
            this,
            1,
            restartServiceIntent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmService = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmService.set(
            AlarmManager.ELAPSED_REALTIME,
            SystemClock.elapsedRealtime() + 1000,
            restartServicePendingIntent
        )
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)

        // Restart the service when the task is removed
        val restartServiceIntent = Intent(applicationContext, LocationService::class.java).also {
            it.setPackage(packageName)
        }
        val restartServicePendingIntent: PendingIntent =
            PendingIntent.getService(
                this,
                1,
                restartServiceIntent,
                PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
            )
        val alarmService: AlarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        alarmService.set(
            AlarmManager.ELAPSED_REALTIME,
            SystemClock.elapsedRealtime() + 1000,
            restartServicePendingIntent
        )
    }

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
        const val CHANNEL_ID = "location"
        const val NOTIFICATION_ID = 1
    }

    private fun haversine(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val R = 6371000.0 // Radius of the Earth in meters
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)

        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2) * sin(dLon / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return R * c // Distance in meters
    }

    fun checkLocation(liveLat: Double, liveLon: Double) {
        val officeLat = preferenceHelper.latitude.toString().toDouble() ?: 0.0// add sharedpref lat
        val officeLon = preferenceHelper.longitude.toString().toDouble() ?: 0.0// add sharedpref lon
        val distance = haversine(liveLat, liveLon, officeLat, officeLon)
        val context: Context = applicationContext

        val BASE_URL = context.getString(R.string.base_url)
        val retrofit = RetrofitClient.getClient(BASE_URL)
        val apiService = retrofit?.create(ApiService::class.java)
        when (distance) {
            in 0.0..200.00 -> {


                if (!preferenceHelper.isCheckedIn) {
                    // Set the flag that the user has checked in
                    preferenceHelper.isCheckedIn = true

                    // Send a notification to the user
                    sendNotification(
                        "Location Alert",
                        "You are inside the 200-meter radius of the office."
                    )

                    // Prepare the CheckInRequest data
                    val checkInRequest = CheckInRequest(
                        user_id = preferenceHelper.userId,
                        office_id = preferenceHelper.officeId,
                        check_in_time = getCurrentTime(), // Replace this with a function or variable that gives the current time in "hh:mm:ss"
                        attendance_date = getCurrentDate() // Replace this with a function or variable that gives the current date in "yyyy-mm-dd"
                    )

                    // Call the userCheckIn function, passing the apiService, checkInRequest, and context
                    userCheckIn(apiService, checkInRequest, context)
                }


            }

            else -> {

                if (preferenceHelper.isCheckedIn) {
                    // Set the flag that the user has checked out
                    preferenceHelper.isCheckedIn = false

                    // Send a notification to the user
                    sendNotification(
                        "Location Alert",
                        "You have checked out and are outside the 200-meter radius of the office."
                    )

                    // Prepare the CheckOutRequest data
                    val checkOutRequest = CheckOutRequest(
                        user_id = preferenceHelper.userId,
                        office_id = preferenceHelper.officeId,
                        check_out_time = getCurrentTime(), // Replace this with a function or variable that gives the current time in "hh:mm:ss"
                        attendance_date = getCurrentDate() // Replace this with a function or variable that gives the current date in "yyyy-MM-dd"
                    )

                    // Call the userCheckOut function, passing the apiService, checkOutRequest, and context
                    userCheckOut(apiService, checkOutRequest, context)
                }

            }
        }

    }

    private fun sendNotification(title: String, content: String) {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(
            NOTIFICATION_ID + 1,
            notification
        ) // Unique ID for this notification
    }

    fun userCheckIn(
        apiService: ApiService?,
        checkInRequest: CheckInRequest,
        context: Context

    ) {
        apiService?.checkIn(checkInRequest)?.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(
                call: Call<ApiResponse>,
                response: Response<ApiResponse>
            ) {
                if (response.isSuccessful) {
                    val checkInRepsonse = response.body()

                    if (checkInRepsonse?.status == "success") {

                        // Store user data in preferences
                        Toast.makeText(context, "Check In Successful", Toast.LENGTH_SHORT).show()
                    } else if (checkInRepsonse?.status == "error") {
                        Toast.makeText(context, "Check In Failed", Toast.LENGTH_SHORT).show()
                    }

                } else {
                    Log.d("CheckIn", "CheckIn failed: ${response.message()}")
                    Toast.makeText(
                        context,
                        "CheckIn failed: ${response.message()}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.d("CheckIn", "Network Error: ${t.message}")
                Toast.makeText(context, "Network Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    fun userCheckOut(
        apiService: ApiService?,
        checkOutRequest: CheckOutRequest,
        context: Context
    ) {
        apiService?.checkOut(checkOutRequest)?.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(
                call: Call<ApiResponse>,
                response: Response<ApiResponse>
            ) {
                if (response.isSuccessful) {
                    val checkOutResponse = response.body()

                    if (checkOutResponse?.status == "success") {
                        // Store user data in preferences or notify user
                        Toast.makeText(context, "Check Out Successful", Toast.LENGTH_SHORT).show()
                    } else if (checkOutResponse?.status == "error") {
                        Toast.makeText(context, "Check Out Failed", Toast.LENGTH_SHORT).show()
                    }

                } else {
                    Log.d("CheckOut", "CheckOut failed: ${response.message()}")
                    Toast.makeText(context, "CheckOut failed: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.d("CheckOut", "Network Error: ${t.message}")
                Toast.makeText(context, "Network Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    fun getCurrentTime(): String {
        val timeFormatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return timeFormatter.format(Date())
    }

    fun getCurrentDate(): String {
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormatter.format(Date())
    }


}
