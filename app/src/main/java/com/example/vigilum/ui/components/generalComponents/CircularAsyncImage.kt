package com.example.adsnetkyc.presentation.qouikscreens.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.vigilum.ui.theme.baiJumreeFontFamily

@Composable
fun CircularAsyncImage(image: String, text: String) {
    val context = LocalContext.current

    Column() {

        AsyncImage(
            model = ImageRequest.Builder(context).data(image).build(),
            contentDescription = "circular image",
            modifier = Modifier
                .size(200.dp) // Set the size of the image
                .clip(CircleShape) // Clip the image to a circle
                .align(Alignment.CenterHorizontally) // Center the CircularImage
                .border(5.dp, Color.Gray, CircleShape), // Optional: Add a border

            contentScale = ContentScale.Crop // Crop the image to fill the size
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = text,
            textAlign = TextAlign.Center,
            fontFamily = baiJumreeFontFamily,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium

        )
    }
}