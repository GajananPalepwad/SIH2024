package com.plcoding.backgroundlocationtracking.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.plcoding.backgroundlocationtracking.screens.AttendanceScreen
import com.plcoding.backgroundlocationtracking.screens.HomeScreen

@Composable
fun Navigation(
    navController: NavHostController,
) {
//    NavHost(navController = navController, startDestination = Screen.AttendanceScreen.route) {
//
//        composable(Screen.SplashScreen.route) {
//            SplashScreen(
//                navController = navController
//            )
//        }
//
//    }

    NavHost(navController = navController, startDestination = Screen.AttendanceScreen.route) {
        composable(Screen.AttendanceScreen.route){
            AttendanceScreen(navController = navController)
        }
        composable(Screen.HomeScreen.route){
            HomeScreen(navController = navController)
        }

        //TODO Add more screens here
    }


}