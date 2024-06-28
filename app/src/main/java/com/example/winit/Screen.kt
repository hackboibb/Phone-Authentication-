package com.example.winit

sealed class Screen (val route : String){


    object NumberScreen: Screen("number_screen")
    object OtpScreen: Screen("otp_Screen")
    object HomeScreen: Screen("next_screen")
    object FirstScreen:Screen("first_screen")
    object SignUPScreen:Screen("signup_screen")
}