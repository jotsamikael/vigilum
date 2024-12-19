package com.example.vigilum.ui.remoteconfiguration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cottage
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.vigilum.ui.components.generalComponents.CircularShape
import com.example.vigilum.ui.components.generalComponents.RectagularButton
import com.example.vigilum.ui.theme.primaryLight
import com.example.vigilum.utils.Destinations

@Composable
fun SuccessfulRemoteConfigurationScreen( navController: NavController) {
    Row ( modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(20.dp).fillMaxHeight()

        ) {
            CircularShape("Remote configuration successful", 300)
            RectagularButton(
                150,
                50,
                "CONTINUE",
                primaryLight,
                Icons.Rounded.Cottage
            ){

                //Navigate to home
                navController.navigate(Destinations.HomeScreen.toString())
            }

        }

    }

}