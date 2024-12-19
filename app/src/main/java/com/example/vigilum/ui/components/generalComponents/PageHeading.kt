package com.example.vigilum.ui.components.generalComponents

import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun PageHeading(s: String) {
    Text(text = s, textAlign = TextAlign.Center,style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black))
}