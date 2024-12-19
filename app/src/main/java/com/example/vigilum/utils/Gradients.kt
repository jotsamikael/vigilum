package com.example.vigilum.utils

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.vigilum.ui.theme.Cyan
import com.example.vigilum.ui.theme.WhiteSmoke
import com.example.vigilum.ui.theme.primaryDark
import com.example.vigilum.ui.theme.primaryLight

object Gradients {

    val cardGradientWhite = Brush.verticalGradient(
        colors = listOf(WhiteSmoke, Color.White),
        startY = 0.5f,
        endY = Float.POSITIVE_INFINITY
    )

    val cardGradientBlue = Brush.verticalGradient(
        colors = listOf(primaryLight, Cyan),
        startY = 0.5f,
        endY = Float.POSITIVE_INFINITY
    )


}