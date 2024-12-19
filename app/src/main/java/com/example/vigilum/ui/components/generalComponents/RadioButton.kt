package com.example.vigilum.ui.components.generalComponents

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vigilum.ui.theme.baiJumreeFontFamily
import com.example.vigilum.ui.theme.primaryLight


@Composable
fun RadioBtn(
    selectedOption: MutableState<String>,
    options: List<String>,
    onSelectionChanged: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(8.dp))
    ) {
        options.forEach { option ->
            Column(
                modifier = Modifier.selectable(
                    selected = selectedOption.value == option,
                    onClick = {
                        selectedOption.value = option
                        onSelectionChanged(option) // Notify the ViewModel about the change
                    }
                )
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {

                    RadioButton(
                        selected = selectedOption.value == option,
                        onClick = null,
                        modifier = Modifier.size(70.dp),
                        colors = RadioButtonDefaults.colors(primaryLight)
                    )
                    Text(
                        text = option,
                        color = Color.Black,
                        fontFamily = baiJumreeFontFamily,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
            }
        }
    }
}
