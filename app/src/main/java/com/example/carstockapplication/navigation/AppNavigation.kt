package com.example.carstockapplication.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.carstockapplication.presentation.screens.LoginScreen
import com.example.carstockapplication.presentation.screens.NewVehicleScreen
import com.example.carstockapplication.presentation.screens.VehicleListScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(
                viewModel = hiltViewModel(),
                onLoginSuccess = {
                    navController.navigate("vehicles") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        composable("vehicles") {
            VehicleListScreen(
                viewModel = hiltViewModel(),
                createVehicle = {
                    navController.navigate("vehicles/new")
                }
            )
        }

        composable("vehicles/new") {
            NewVehicleScreen(viewModel = hiltViewModel())
        }
    }
}