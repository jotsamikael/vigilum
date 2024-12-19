package com.example.vigilum.ui.components.generalComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.vigilum.ui.theme.primaryLight

@Composable
fun CircularShape(
    text: String,
    circleSize: Int,
) {
    Box(
        modifier = Modifier
            .size(circleSize.dp) // Set the size of the circle
            .clip(CircleShape) // Clip to circle
            .background(Color.White) // Set background color
            .border(5.dp, primaryLight, CircleShape), // Add an orange border
        contentAlignment = Alignment.Center // Center the content inside the Box
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            color = primaryLight, // Set text color to orange
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold) // Use desired text style
        )
    }
}

