package com.plcoding.backgroundlocationtracking.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.plcoding.backgroundlocationtracking.data.PreferenceHelper
import com.plcoding.backgroundlocationtracking.screens.EditUserDetailsScreen
import com.plcoding.backgroundlocationtracking.screens.MainScaffoldScreen
import com.plcoding.backgroundlocationtracking.screens.SignInScreen
import com.plcoding.backgroundlocationtracking.screens.SignUpScreen
import com.plcoding.backgroundlocationtracking.screens.SplashScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(
    navController: NavHostController,
    preferenceHelper: PreferenceHelper
) {

    NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {

        composable(Screen.SignUpScreen.route){
            SignUpScreen(navController = navController)
        }
        composable(Screen.SignInScreen.route){
            SignInScreen(navController = navController, preferenceHelper = preferenceHelper)
        }

        composable(Screen.MainScaffoldScreen.route){
            MainScaffoldScreen(navController = navController, preferenceHelper = preferenceHelper)
        }
        composable(Screen.UserDetailsEditScreen.route){
            EditUserDetailsScreen(navController = navController, preferenceHelper = preferenceHelper)
        }
        composable(Screen.SplashScreen.route){
            SplashScreen(navController = navController, preferenceHelper = preferenceHelper)
        }

        //TODO Add more screens here
    }


}