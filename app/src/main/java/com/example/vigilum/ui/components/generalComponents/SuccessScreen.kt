package com.example.vigilum.ui.components.generalComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vigilum.R
import com.example.vigilum.utils.Destinations
import kotlinx.coroutines.delay

@Composable
fun SuccessScreenComposable(navController: NavController){
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center){
        GifImage(modifier = Modifier
            .size(300.dp) // Set the size of the image
            .clip(RectangleShape) // Clip the image to a circle
            .align(Alignment.CenterHorizontally), // Center the CircularImage
            R.drawable.success
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Launch effect to delay navigation
        LaunchedEffect(Unit) {
            delay(2200) // Delay for 20 seconds
            navController.navigate(Destinations.HomeScreen.toString()) // Replace with your actual route
        }
    }
}