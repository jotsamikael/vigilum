package com.example.vigilum.ui.components.generalComponents

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.vigilum.ui.components.HspacerComponent


@Composable
fun RectagularButton(width: Int, height:Int, text:String, color: Color, icon: ImageVector, onClick: () -> Unit,) {
    Button(
        onClick = onClick, modifier = Modifier
            .width(width.dp)
            .height(height.dp),
        colors = ButtonDefaults.buttonColors(
            color
        ),

    ) {
        Icon(imageVector = icon, tint = Color.White, contentDescription = null)
        HspacerComponent(8)
        Text(text, style = androidx.compose.material3.MaterialTheme.typography.bodyLarge, color= Color.White)
    }
}