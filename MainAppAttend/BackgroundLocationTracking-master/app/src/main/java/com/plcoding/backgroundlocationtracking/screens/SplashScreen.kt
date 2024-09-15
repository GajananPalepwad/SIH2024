package com.plcoding.backgroundlocationtracking.screens

import android.content.Context
import android.util.Log
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.plcoding.backgroundlocationtracking.R
import com.plcoding.backgroundlocationtracking.api.ApiService
import com.plcoding.backgroundlocationtracking.api.RetrofitClient
import com.plcoding.backgroundlocationtracking.apimodels.response.EmployeeSignInResponse
import com.plcoding.backgroundlocationtracking.apimodels.response.UserDetailsResponse
import com.plcoding.backgroundlocationtracking.data.PreferenceHelper
import com.plcoding.backgroundlocationtracking.navigation.Screen
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun SplashScreen(
    navController: NavController,
    preferenceHelper: PreferenceHelper

) {
    val context = LocalContext.current
    val BASE_URL = context.getString(R.string.base_url)
    val retrofit = RetrofitClient.getClient(BASE_URL)
    val apiService = retrofit?.create(ApiService::class.java)
    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                })
        )

        // Navigate based on authentication state or fetched user details
        if (preferenceHelper.userId != -1) {
            getUserDetails(
                preferenceHelper.userId,
                apiService,
                preferenceHelper,
                context,
                navController
            )

        } else {
            navController.navigate(Screen.SignInScreen.route)
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(id = R.drawable.check_in),
            contentDescription = "App Logo",
            modifier = Modifier.scale(scale.value),
            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.Black)
        )
    }
}

 fun getUserDetails(
    userId: Int,
    apiService: ApiService?,
    preferenceHelper: PreferenceHelper,
    context: Context,
    navController: NavController
) {
    apiService?.getOfficeCo(userId)?.enqueue(object : Callback<UserDetailsResponse> {
        override fun onResponse(
            call: Call<UserDetailsResponse>,
            response: Response<UserDetailsResponse>
        ) {
            if (response.isSuccessful) {
                val userResponse = response.body()

                if (userResponse?.status == "success") {

                    // Store user data in preferences
                    preferenceHelper.userId = userResponse.user.user_id
                    preferenceHelper.name = userResponse.user.name
                    preferenceHelper.email = userResponse.user.email
                    preferenceHelper.employeeId = userResponse.user.employeeId
                    preferenceHelper.mobileNumber = userResponse.user.mobile_number
                    preferenceHelper.position = userResponse.user.position
                    preferenceHelper.orgId = userResponse.user.org_id
                    preferenceHelper.officeId = userResponse.user.office_id
                    preferenceHelper.approved = userResponse.user.approved
                    preferenceHelper.latitude = userResponse.office.latitude.toString()
                    preferenceHelper.longitude = userResponse.office.longitude.toString()


                    navController.navigate(Screen.MainScaffoldScreen.route)


                } else if (userResponse?.status == "error") {
                    Toast.makeText(
                        context,
                        "Failed to load data: ${userResponse.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } else {
                Log.d("DataFetch", "Failed to load data")
                Toast.makeText(
                    context,
                    "Failed to load data ${response.message()}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        override fun onFailure(call: Call<UserDetailsResponse>, t: Throwable) {
            Log.d("DataFetch", "Network Error: ${t.message}")
            Toast.makeText(context, "Network Error: ${t.message}", Toast.LENGTH_SHORT).show()
        }
    })
}