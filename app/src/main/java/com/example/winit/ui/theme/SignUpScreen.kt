package com.example.winit.ui.theme
import android.app.Activity

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.winit.AuthViewModel
import com.example.winit.R
import com.example.winit.Screen
import com.example.winit.User



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(authViewModel: AuthViewModel, navController: NavController, user: User) {
    var phoneNumber by remember { mutableStateOf(user.PhoneNumber) }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }

    user.FirstName = firstName
    user.LastName = lastName

    val context = LocalContext.current as Activity
    val verificationState by authViewModel.verificationState.observeAsState()

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
                .padding(top = 470.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                shape = RoundedCornerShape(40.dp), // Make the corner rounded
                border = BorderStroke(1.dp, Color.Gray),
                modifier = Modifier.size(width = 400.dp, height = 60.dp),
                shadowElevation = 2.dp // Add a thin border
            ) {
                TextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = { Text("Enter your first name") },
                    singleLine = true,
                    shape = RoundedCornerShape(40.dp),  // Make the corner rounded
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Text
                    ),
                    textStyle = LocalTextStyle.current.copy(color = Color.Black),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.Black
                    )
                )
            }
            Spacer(modifier = Modifier.padding(top = 10.dp))
            Surface(
                shape = RoundedCornerShape(40.dp), // Make the corner rounded
                border = BorderStroke(1.dp, Color.Gray),
                modifier = Modifier.size(width = 400.dp, height = 60.dp),
                shadowElevation = 2.dp // Add a thin border
            ) {
                TextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = { Text("Enter your last name") },
                    singleLine = true,
                    shape = RoundedCornerShape(40.dp),  // Make the corner rounded
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Text
                    ),
                    textStyle = LocalTextStyle.current.copy(color = Color.Black),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.Black
                    )
                )
            }
            Spacer(modifier = Modifier.padding(top = 10.dp))
            Surface(
                shape = RoundedCornerShape(30.dp), // Make the corner rounded
                border = BorderStroke(1.dp, Color.Gray),
                modifier = Modifier.size(width = 400.dp, height = 60.dp),
                shadowElevation = 3.dp // Add a thin border
            ) {
                TextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    label = { Text("+91 ENTER YOUR 10-DIGIT PHONE NUMBER ") },
                    singleLine = true,
                    shape = RoundedCornerShape(40.dp),  // Make the corner rounded
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number
                    ),
                    textStyle = LocalTextStyle.current.copy(color = Color.Black),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.Black
                    )
                )
            }
            Spacer(modifier = Modifier.padding(top = 10.dp))
            Button(
                onClick = {
                    loading = true
                    authViewModel.verifyPhoneNumber(phoneNumber, context)
                },
                modifier = Modifier
                    .size(width = 200.dp, height = 50.dp)
                    .border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(100.dp) // Adjust the corner radius as needed
                    )
                    .shadow(2.dp, RoundedCornerShape(100.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                enabled = phoneNumber.length == 13 && firstName.length >= 2 && lastName.length >= 2
            ) {
                Text(text = "SEND OTP")
            }

            if (!loading) return
            CircularProgressIndicator(
                modifier = Modifier.width(50.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )

        }
        // Other UI elements for your signup screen
    }
    when (verificationState) {
        is AuthViewModel.VerificationState.CodeSent -> {
            navController.navigate(Screen.OtpScreen.route)
            loading = false
            Text(text = "OTP SENT")

        }
        is AuthViewModel.VerificationState.Failed -> {
            loading = false
            Text(text = "Verification Failed: ${(verificationState as AuthViewModel.VerificationState.Failed).error}")
        }
        is AuthViewModel.VerificationState.Success -> {
            loading = false
            Text(text = "")
        }
        is AuthViewModel.VerificationState.VerificationCompleted -> {
            loading = false
            Text(text = "")
        }
        is AuthViewModel.VerificationState.UserNotFound -> {
            loading = false
            Text(text = "")
        }

        null -> {

            Text(text = "")
        }
    }
}
