package com.example.vigilum.ui.components.generalComponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.vigilum.ui.theme.FormFieldLabel
import com.example.vigilum.ui.theme.baiJumreeFontFamily
import com.example.vigilum.ui.theme.primaryDark
import com.example.vigilum.ui.theme.primaryLight


// Define your validation functions
fun validatePhoneNumber(value: String): Boolean {
    val phoneNumberPattern = "[0-9]+".toRegex()
    return value.length >= 9 && phoneNumberPattern.matches(value)
}

fun validateIdDocumentNumber(value: String): Boolean {
    return value.length >= 9
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInputBar(
    modifier: Modifier,
    text: String = "",

    placeholderText: String = "Enter text",
    placeholderSize: Int = 14,
    placeholderColor: Color = Color.Gray,

    keyboardType: KeyboardType = KeyboardType.Text,
    validationType: String = "", // New parameter for validation type
    onValueChanged: (String) -> Unit = {} // Callback for when the text changes
) {

    var isError by remember { mutableStateOf(false) }
    var textFieldState by remember { mutableStateOf(TextFieldValue(text)) }



    LaunchedEffect(textFieldState.text) {
        when (validationType) {
            "PhoneNumber" -> {
                if (!validatePhoneNumber(textFieldState.text)) {
                    // Handle invalid phone number
                    isError = !validatePhoneNumber(textFieldState.text)

                }
            }

            "IdDocumentNumber" -> {
                if (!validateIdDocumentNumber(textFieldState.text)) {
                    // Handle invalid ID document number
                    isError = !validateIdDocumentNumber(textFieldState.text)

                }
            }
        }
    }

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

        OutlinedTextField(
            value = textFieldState,
            onValueChange = { newValue ->
                textFieldState = newValue
                onValueChanged(newValue.text)
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = primaryDark,
                focusedLabelColor = primaryDark,
                cursorColor = FormFieldLabel,
            ),
            modifier = modifier,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),

            placeholder = {
                Text(
                    text = placeholderText,
                    color = placeholderColor,
                    fontSize = placeholderSize.sp,
                    fontFamily = baiJumreeFontFamily,
                    textAlign = TextAlign.Center // Center-align the placeholder text
                )
            },
            textStyle = LocalTextStyle.current.copy(
                fontSize = placeholderSize.sp,
                fontFamily = baiJumreeFontFamily,
                color = primaryLight,
                textAlign = TextAlign.Center // Attempt to center-align the input text, but note that this might not work as expected due to limitations in TextFieldValue styling

            )
        )

    }

}