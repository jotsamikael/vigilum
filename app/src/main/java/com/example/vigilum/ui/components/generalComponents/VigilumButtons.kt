package com.example.vigilum.ui.components.generalComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vigilum.ui.theme.baiJumreeFontFamily
import com.example.vigilum.ui.theme.primaryLight
import com.example.vigilum.utils.Gradients

@Composable
fun VigilumSubmitButton(onClick: () -> Unit, modifier: Modifier) {
    Button(
        onClick = onClick, modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            primaryLight
        )
    ) {
        Text("Submit", fontSize = 24.sp, color= Color.White)
    }
}

@Composable
fun VigilumEnrollButton(onClick: () -> Unit, modifier: Modifier) {
    Button(
        onClick = onClick, modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            primaryLight
        )
    ) {
        Text("Enroll", fontSize = 24.sp, color= Color.White)
    }
}

@Composable
fun NextButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .background(
                brush = Gradients.cardGradientWhite
            )
    ) {
        TextButton(
            onClick = onClick,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .align(Alignment.Center)
                .width(300.dp),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                Color.Transparent
            )
        ) {
            androidx.compose.material3.Text(
                text = "NEXT",
                fontFamily = baiJumreeFontFamily,
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),

                )
        }
    }
}
