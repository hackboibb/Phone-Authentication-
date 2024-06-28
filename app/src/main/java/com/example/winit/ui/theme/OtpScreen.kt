package com.example.winit.ui.theme

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.winit.AuthViewModel
import com.example.winit.R
import com.example.winit.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpScreen(authViewModel: AuthViewModel, navController: NavController, OnSuccess:() ->Unit) {


    var Otp by remember { mutableStateOf("") }
    val verificationId by authViewModel.verificationId.observeAsState()
    val verificationState by authViewModel.verificationState.observeAsState()
    val context = LocalContext.current
    var loading by remember { mutableStateOf(false) }




    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.bg_new),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth()

        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 370.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center


        ) {
            Surface(
                shape = RoundedCornerShape(40.dp), // Make the corner rounded
                border = BorderStroke(1.dp, Color.Gray),
                modifier = Modifier.size(width = 400.dp, height = 60.dp),
                shadowElevation = 2.dp// Add a thin border
                // Add a slight shadow effect
            ) {
                TextField(
                    value = Otp,
                    onValueChange = { Otp = it },
                    label = { Text("Enter 6-DIGIT_OTP") },
                    singleLine = true,
                    shape = RoundedCornerShape(40.dp),  // Make the corner rounded
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number
                    ),
                    textStyle = LocalTextStyle.current.copy(color = Color.Black),
                    colors = TextFieldDefaults.textFieldColors(  // Use textFieldColors for filled style
                        containerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.Black

                    )
                )
            }

            Spacer(modifier = Modifier.padding(top = 30.dp))

            Button(
                onClick = {
                    verificationId?.let {
                        authViewModel.verifyOtp(it, Otp)
                        Log.d("OtpScreen", "Verification ID: $it, OTP: $Otp")
                    }

                },
                modifier = Modifier
                    .size(width = 150.dp, height = 40.dp)
                    .border(
                        width = 2.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(100.dp) // Adjust the corner radius as needed
                    )
                    .shadow(2.dp, RoundedCornerShape(100.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black

                )


            ) {
                Text(text = "SUBMIT", fontFamily = leelawad)


            }

            Spacer(modifier = Modifier.padding(top = 20.dp))

            if (loading) return
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )

        }
    }


    when (verificationState) {

        is AuthViewModel.VerificationState.CodeSent -> {
            loading = false
            Toast.makeText(context, "Code sent ", Toast.LENGTH_SHORT).show()



        }

        is AuthViewModel.VerificationState.Failed -> {
            Toast.makeText(context, "Wrong OTP ", Toast.LENGTH_SHORT).show()
            loading = false

        }

        is AuthViewModel.VerificationState.Success -> {
            navController.navigate(Screen.HomeScreen.route)
            loading = false


        }

        is AuthViewModel.VerificationState.VerificationCompleted -> {
            Toast.makeText(context, "Details saved", Toast.LENGTH_LONG).show()
            loading = false



        }

        is AuthViewModel.VerificationState.UserNotFound -> {
            loading = false


        }

        null -> Toast.makeText(context, "", Toast.LENGTH_LONG).show()


    }





}

