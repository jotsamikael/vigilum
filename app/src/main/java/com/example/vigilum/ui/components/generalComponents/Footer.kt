package com.example.vigilum.ui.components.generalComponents

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.vigilum.ui.theme.baiJumreeFontFamily
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Footer() {
    var year by remember {
        mutableStateOf(LocalDate.now().year)
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        Text(
            text = "Built by ADSNET | © $year",
            fontFamily = baiJumreeFontFamily,
            color = Color.Gray,
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            letterSpacing = 1.sp
        )
    }
}

