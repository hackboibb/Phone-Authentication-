package com.example.winit.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.autoSaver
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.winit.AuthViewModel
import com.example.winit.R
import com.example.winit.Screen


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(navController: NavController ,authViewModel: AuthViewModel){
    var showDialogSignOut by remember { mutableStateOf(false) }
    val categories = listOf(
        "Upcoming Tournaments" to listOf("t1","t2","t3"),
        "On Going Tournaments" to listOf("t5","t6","t7","t8"),
        "Recently Closed Tournaments" to listOf("t4","t9","t10")
    )

    if (showDialogSignOut) {
        AlertDialog(
            onDismissRequest = { showDialogSignOut = false },
            title = { Text(text = "Sign Out") },
            text = { Text(text = "Are you sure you want to sign out?") },
            confirmButton = {
                TextButton(onClick = {
                    authViewModel.signout(result = true)
                    navController.navigate(Screen.FirstScreen.route)


                    showDialogSignOut = false
                }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialogSignOut = false }) {
                    Text("No")
                }
            }
        )
    }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ){
        LazyColumn {
            categories.forEach{(categories,items) ->
                stickyHeader {
                    Text(
                        text = categories,
                        modifier = Modifier.padding(16.dp),
                        color = Color.White,

                    )
                }

                item {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {

                        items(items){ item ->
                            BrowseScreen(cat = item, drawable = getDrawableForCategory(item) )



                        }
                    }
                }

            }
        }

        Button(
            onClick = { showDialogSignOut = true },
            modifier = Modifier,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White, // Button background color
                contentColor = Color.Black // Text color
            )

        ) {
            Text(text = "Sign out")
        }

    }
}

@Composable
fun BrowseScreen(cat: String, drawable: Int) {
    Card(
        modifier = Modifier
            .size(width = 300.dp, height = 180.dp)
            .clickable { /* Perform click operation */ }
            .padding(4.dp),
        border = BorderStroke(1.dp, Color.White)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = drawable),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )
            Text(
                text = cat,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

 fun getDrawableForCategory(category: String): Int{

    return when (category){
        "t1" -> R.drawable.t1
        "t2" -> R.drawable.t2
        "t3" -> R.drawable.t3
        "t4" -> R.drawable.t4
        "t5" -> R.drawable.t5
        "t6" -> R.drawable.t8
        "t7" -> R.drawable.t9
        "t8" -> R.drawable.t10
        "t9" -> R.drawable.t6
        else -> R.drawable.logo_winit
    }
}

