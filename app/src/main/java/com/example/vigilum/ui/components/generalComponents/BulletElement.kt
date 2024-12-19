package com.example.adsnetkyc.presentation.qouikscreens.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.vigilum.ui.theme.baiJumreeFontFamily

@Composable
fun BulletElement(bulletType: String, text:String) {
    Column() {
        Row {
            Text(
                text = bulletType,
                fontFamily = baiJumreeFontFamily,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray,
                fontSize = 18.sp
            )
            Text(
                text = text,
                fontFamily = baiJumreeFontFamily,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray,
                fontSize = 18.sp
            )
        }
    }
}