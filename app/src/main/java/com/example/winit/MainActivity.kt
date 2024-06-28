package com.example.winit


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController


import com.example.winit.ui.theme.WINITTheme
import com.google.firebase.auth.FirebaseAuth


class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val user:User = User("","","")
        val authRepository = AuthRepository()
        val authViewModel = AuthViewModel(authRepository ,user)



        setContent {

            WINITTheme {

                val navController = rememberNavController()
                val auth = FirebaseAuth.getInstance()
                val userLoggedIn by remember { mutableStateOf(auth.currentUser != null) }

                LaunchedEffect(userLoggedIn) {
                    if (userLoggedIn) {
                        navController.navigate(Screen.HomeScreen.route)
                    } else {
                        navController.navigate(Screen.FirstScreen.route)
                    }
                }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   Navigations(navController,authViewModel = authViewModel)
                }
            }
        }
    }
}





