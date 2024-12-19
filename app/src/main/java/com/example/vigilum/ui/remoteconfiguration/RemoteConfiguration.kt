package com.example.vigilum.ui.remoteconfiguration

import RemoteConfigurationFormEvent
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.AdminPanelSettings
import androidx.compose.material.icons.rounded.CloudUpload
import androidx.compose.material.icons.rounded.Dns
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.vigilum.ui.components.VspacerComponent
import com.example.vigilum.ui.components.generalComponents.PageHeading
import com.example.vigilum.ui.components.generalComponents.RectagularButton
import com.example.vigilum.ui.theme.outlineVariantDarkHighContrast
import com.example.vigilum.ui.theme.primaryContainerLightMediumContrast
import com.example.vigilum.ui.theme.primaryLight
import com.example.vigilum.utils.Destinations


@Composable
fun RemoteConfigurationScreen(
    remoteConfigurationViewModel: RemoteConfigurationViewModel = hiltViewModel(),
    navController: NavController
) {



    val state = remoteConfigurationViewModel.state

    LaunchedEffect(key1 = Unit) {
        remoteConfigurationViewModel.successfulSubmit.collect { isSuccess ->
            if (isSuccess) {
                Log.i("MYPARAMS","/${remoteConfigurationViewModel.state.serverIp}/${remoteConfigurationViewModel.state.merchantCode}/${remoteConfigurationViewModel.state.securityKey}")
                navController.navigate(Destinations.ProcessingRemoteConfigurationScreen.toString()+"/${remoteConfigurationViewModel.state.serverIp}/${remoteConfigurationViewModel.state.merchantCode}/${remoteConfigurationViewModel.state.securityKey}") // Replace with your destination route
            }
        }
    }

    Row ( modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(20.dp).fillMaxHeight()

        ) {
            VspacerComponent(22)
            PageHeading("Configuration")

            VspacerComponent(32)

            //serverIp name
            OutlinedTextField(
                value = state.serverIp,
                onValueChange = {
                    remoteConfigurationViewModel.onEvent(
                        RemoteConfigurationFormEvent.ServerIpChanged(it)
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedLabelColor = outlineVariantDarkHighContrast,
                    unfocusedBorderColor = outlineVariantDarkHighContrast,
                    focusedBorderColor = primaryContainerLightMediumContrast,
                    focusedLabelColor = primaryContainerLightMediumContrast,
                    cursorColor = primaryContainerLightMediumContrast,

                    ),
                leadingIcon = {
                    Icon(
                        Icons.Rounded.Dns,
                        tint = outlineVariantDarkHighContrast, contentDescription = "server ip"
                    )
                },
                label = {
                    Text(
                        text = "Server IP",
                        color = outlineVariantDarkHighContrast,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium
                    )
                },
                placeholder = {
                    Text(
                        text = "ex: 192.168.72.22",
                        color = outlineVariantDarkHighContrast,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium
                    )
                },

                isError = state.serverIpError != null,
                modifier = Modifier.fillMaxWidth(),

                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = primaryLight
                )
            )
            if (state.serverIpError != null) {
                androidx.compose.material3.Text(
                    text = state.serverIpError,
                    color = Color.Red
                )
            }
            VspacerComponent(22)

            //merchant code
            OutlinedTextField(
                value = state.merchantCode,
                onValueChange = {
                    remoteConfigurationViewModel.onEvent(
                        RemoteConfigurationFormEvent.MerchantCodeChanged(it)
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedLabelColor = outlineVariantDarkHighContrast,
                    unfocusedBorderColor = outlineVariantDarkHighContrast,
                    focusedBorderColor = primaryContainerLightMediumContrast,
                    focusedLabelColor = primaryContainerLightMediumContrast,
                    cursorColor = primaryContainerLightMediumContrast,

                    ),
                leadingIcon = {
                    Icon(
                        Icons.Rounded.AccountCircle,
                        tint = outlineVariantDarkHighContrast, contentDescription = "server ip"
                    )
                },
                label = {
                    Text(
                        text = "Merchant Code",
                        color = outlineVariantDarkHighContrast,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium
                    )
                },
                placeholder = {
                    Text(
                        text = "ex: LMT23345",
                        color = outlineVariantDarkHighContrast,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium
                    )
                },

                isError = state.serverIpError != null,
                modifier = Modifier.fillMaxWidth(),

                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = primaryLight
                )
            )
            if (state.merchantCodeError != null) {
                androidx.compose.material3.Text(
                    text = state.merchantCodeError,
                    color = Color.Red
                )
            }

            VspacerComponent(22)

            //security key
            OutlinedTextField(
                value = state.securityKey,
                onValueChange = {
                    remoteConfigurationViewModel.onEvent(
                        RemoteConfigurationFormEvent.SercurityKeyChanged(it)
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedLabelColor = outlineVariantDarkHighContrast,
                    unfocusedBorderColor = outlineVariantDarkHighContrast,
                    focusedBorderColor = primaryContainerLightMediumContrast,
                    focusedLabelColor = primaryContainerLightMediumContrast,
                    cursorColor = primaryContainerLightMediumContrast,

                    ),
                leadingIcon = {
                    Icon(
                        Icons.Rounded.AdminPanelSettings,
                        tint = outlineVariantDarkHighContrast, contentDescription = "security key"
                    )
                },
                label = {
                    Text(
                        text = "Security Key",
                        color = outlineVariantDarkHighContrast,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium
                    )
                },
                placeholder = {
                    Text(
                        text = "ex: 9045A45D0884XV72",
                        color = outlineVariantDarkHighContrast,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium
                    )
                },

                isError = state.serverIpError != null,
                modifier = Modifier.fillMaxWidth(),

                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = primaryLight
                )
            )
            if (state.securityKey != null) {
                state.securityKeyError?.let {
                    androidx.compose.material3.Text(
                        text = it,
                        color = Color.Red
                    )
                }
            }
            VspacerComponent(22)

            RectagularButton(
                150,
                50,
                "SAVE",
                primaryLight,
                Icons.Rounded.CloudUpload
            ){
                remoteConfigurationViewModel.onEvent(
                    RemoteConfigurationFormEvent.Submit
                )
            }

        }

    }

}


