package com.example.cprtrainingapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cprtrainingapp.ui.screens.HomeScreen
import com.example.cprtrainingapp.ui.screens.LoginScreen
import com.example.cprtrainingapp.ui.screens.ResultScreen
import com.example.cprtrainingapp.ui.screens.TrainingScreen

@Composable
fun CprNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController)
        }
        composable("home") {
            HomeScreen(navController)
        }
        composable("training") {
            TrainingScreen(navController)
        }
        composable("result") {
            ResultScreen(navController)
        }
    }
}
