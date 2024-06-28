package com.example.winit.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.winit.R

@OptIn(ExperimentalFoundationApi::class)
@Composable

fun Home(){

    val categories = listOf(
        "Upcoming Tournaments" to listOf("t1","t2","t3"),
        "On Going Tournaments" to listOf("t5","t6","t7","t8"),
        "Recently Closed Tournaments" to listOf("t4","t9","t10")
    )

    LazyColumn {
        categories.forEach{(categories,items) ->
            stickyHeader {
                Text(
                    text = categories,
                    modifier = Modifier.padding(16.dp),
                    color = Color.Black,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = leelawad
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
}



