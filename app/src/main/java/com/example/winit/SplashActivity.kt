package com.example.winit

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image

import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.winit.ui.theme.WINITTheme
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.appCheck
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory

import kotlinx.coroutines.delay
@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //initialize firebase
        FirebaseApp.initializeApp(this)

        Firebase.appCheck.installAppCheckProviderFactory(
            PlayIntegrityAppCheckProviderFactory.getInstance()
        )




        setContent {
            WINITTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SplashScreen(onTimeout = {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    })
                }
            }
        }
    }
}

@Composable
private fun SplashScreen(onTimeout: () -> Unit) {
    var scale by remember { mutableStateOf(1f) }

    LaunchedEffect(true) {
        scale = 3f
        delay(1000) // Duration of the animation
        onTimeout()
    }

    val animatedScale by animateFloatAsState(
        targetValue = scale,
        animationSpec = tween(durationMillis = 2000)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.logo_winit),
            contentDescription = null,
            modifier = Modifier.size((animatedScale * 100).dp) )
    }

}
