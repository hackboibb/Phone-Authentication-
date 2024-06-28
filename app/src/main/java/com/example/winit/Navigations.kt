package com.example.winit


import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.winit.ui.theme.FirstScreen
import com.example.winit.ui.theme.HomeScreen
import com.example.winit.ui.theme.OtpScreen
import com.example.winit.ui.theme.SignUpScreen

@Composable
fun Navigations(navController: NavHostController, authViewModel: AuthViewModel) {
    NavHost(navController = navController, startDestination = Screen.FirstScreen.route) {
        composable(Screen.FirstScreen.route) {
            FirstScreen(
                navController = navController,
                authViewModel = authViewModel,
                onCodeSent = { navController.navigate(Screen.OtpScreen.route) },
                user = authViewModel.user
            )
        }

        composable(Screen.SignUPScreen.route) {
            SignUpScreen(
                authViewModel = authViewModel,
                navController = navController,
                user = authViewModel.user
            )
        }

        composable(Screen.OtpScreen.route) {
            OtpScreen(
                authViewModel = authViewModel,
                navController = navController,
                OnSuccess = { navController.navigate(Screen.HomeScreen.route) }
            )
        }

        composable(Screen.HomeScreen.route) {
            HomeScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }
    }
}