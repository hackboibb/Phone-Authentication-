package com.example.winit.ui.theme


import android.app.Activity
import android.provider.CalendarContract.Colors
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.winit.AuthViewModel
import com.example.winit.R
import com.example.winit.Screen
import com.example.winit.User


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirstScreen(navController: NavController, authViewModel: AuthViewModel , onCodeSent: () -> Unit, user: User) {

    val context = LocalContext.current as Activity
    val verificationState by authViewModel.verificationState.observeAsState()

    var phoneNumber by remember{ mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }

    user.PhoneNumber = "+91$phoneNumber"


    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.first_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {

            Image(
                painter = painterResource(id = R.drawable.logo_winit),
                contentDescription = null,
                modifier = Modifier.size(200.dp) // Adjust the size as necessary
            )

            Text(
                "WIN IT",
                fontFamily = rock,
                fontSize = 30.sp, // Adjust the size as necessary

                color = Color.White
            )
            Text(
                "Victory Starts Here",
                fontFamily = rock,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,

                color = Color.White
            )

            TextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("ENETR 10-DIGIT PHONE NUMBER") },
                singleLine = true,
                modifier = Modifier
                    .padding(top = 160.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 28.dp),
                shape = RoundedCornerShape(40.dp),  // Make the corner rounded
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                textStyle = LocalTextStyle.current.copy(color = Color.Black),
                colors = TextFieldDefaults.textFieldColors(  // Use textFieldColors for filled style
                   containerColor = Color.White, // Default text color
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,

                    cursorColor = Color.Black,  // Color of the cursor



                    ),

            )

            Button(
                onClick = {

                    loading = true

                    authViewModel.checkUserAndVerifyPhoneNumber(user.PhoneNumber,context)


                          },
                modifier = Modifier.padding(top = 10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                enabled = phoneNumber.length == 10
            ) {
                Text(
                    "LOG IN",
                    fontSize = 20.sp,
                    fontFamily = leelawad,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            if (!loading) return
            CircularProgressIndicator(
                modifier = Modifier.width(30.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )




        }


        }

    when (verificationState){
        is AuthViewModel.VerificationState.CodeSent ->{
            onCodeSent()


        }
        is AuthViewModel.VerificationState.Failed ->{
            Text(text = "Verification Failed: ${(verificationState as AuthViewModel.VerificationState.Failed).error}")
        }
        is AuthViewModel.VerificationState.Success ->{
            Text(text = "")
        }
        is AuthViewModel.VerificationState.VerificationCompleted ->{
            Text(text = "Verification Completed")
        }
        is AuthViewModel.VerificationState.UserNotFound ->{

            navController.navigate(Screen.SignUPScreen.route)
            Toast.makeText(context, "User not found", Toast.LENGTH_SHORT).show()

        }


        null -> {
            Text(text = "null")
        }
    }
}






