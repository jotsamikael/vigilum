package com.example.adsnetkyc.presentation.qouikscreens.common

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.vigilum.ui.theme.primaryLight
import com.example.vigilum.ui.theme.baiJumreeFontFamily

@Composable
fun Heading1(message: String) {
    Text(
        text = message,
        fontFamily = baiJumreeFontFamily,
        fontSize = 46.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        letterSpacing = 1.sp,
        color= primaryLight
    )

}