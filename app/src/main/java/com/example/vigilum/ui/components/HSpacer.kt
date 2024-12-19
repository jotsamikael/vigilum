package com.example.vigilum.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HspacerComponent(value:Int){
    Spacer(modifier = Modifier.width(width = value.dp))

}