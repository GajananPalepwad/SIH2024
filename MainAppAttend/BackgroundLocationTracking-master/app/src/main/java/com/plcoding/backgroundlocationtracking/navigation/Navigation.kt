package com.plcoding.backgroundlocationtracking.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.plcoding.backgroundlocationtracking.screens.AttendanceScreen
import com.plcoding.backgroundlocationtracking.screens.HomeScreen
import com.plcoding.backgroundlocationtracking.screens.SignInScreen
import com.plcoding.backgroundlocationtracking.screens.SignUpScreen

@Composable
fun Navigation(
    navController: NavHostController,
) {

    NavHost(navController = navController, startDestination = Screen.SignInScreen.route) {
        composable(Screen.AttendanceScreen.route){
            AttendanceScreen(navController = navController)
        }
        composable(Screen.HomeScreen.route){
            HomeScreen(navController = navController)
        }
        composable(Screen.SignUpScreen.route){
            SignUpScreen(navController = navController)
        }
        composable(Screen.SignInScreen.route){
            SignInScreen(navController = navController)
        }

        //TODO Add more screens here
    }


}