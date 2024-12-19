package com.example.vigilum.ui.remoteconfiguration

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.vigilum.R
import com.example.vigilum.ui.components.generalComponents.EmptyStateComposable
import com.example.vigilum.ui.components.generalComponents.GifImage
import com.example.vigilum.ui.components.generalComponents.Loader
import com.example.vigilum.utils.Destinations
import com.example.utilities.ResourceState
import kotlinx.coroutines.delay

const val TAG = "ProcesingRemoteConfigurationViewModel"

@Composable
fun ProcesingRemoteConfigurationScreen(
    viewModel: ProcesingRemoteConfigurationViewModel = hiltViewModel(),
    navController: NavController,
    serverIp: String,
    merchantCode: String,
    securityKey: String
) {
    LaunchedEffect(Unit) {
        viewModel.submitRemoteConfigParams(serverIp, merchantCode, securityKey)
    }

    //whenever there is a change on response , we will have the updated value on receiptResponse
    val remoteConfigRes by viewModel.response.collectAsState()

    val dbInsertionComplete by viewModel.dbInsertionComplete.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        //managing api response state
        when (remoteConfigRes) {
            is ResourceState.Loading<*> -> {
                Log.d(TAG, "Inside_Loading")
                Loader(
                    modifier = Modifier
                        .size(100.dp)
                        .padding(10.dp)
                )
            }

            is ResourceState.Success<*> -> {
                val response = (remoteConfigRes as ResourceState.Success).data
                Log.d(TAG, "Inside_Success ${response.status} = ${response.message}")

                if (response.status == "200") {

                    // Call the database insertion function
                    LaunchedEffect(Unit) {
                        viewModel.insertRemoteConfigToDB(serverIp, merchantCode, securityKey)
                    }
                    ContactingServer(dbInsertionComplete, navController)

                } else if ((response.status == "400")) {
                     //configuration failed screen
                    RemoteConfigFailed("Device not known",navController)
                } else {
                    EmptyStateComposable("Internal error occured")

                }
            }

            is ResourceState.Error<*> -> {
                Log.d(TAG, "Inside_Error ${remoteConfigRes}")
                EmptyStateComposable("Out of service")

            }
        }

    }

}

@Composable
fun ContactingServer(dbInsertionComplete: Boolean, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        GifImage(
            modifier = Modifier
                .size(300.dp) // Set the size of the image
                .clip(RectangleShape) // Clip the image to a circle
                .align(Alignment.CenterHorizontally), // Center the CircularImage
            R.drawable.connecting_to_server
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Launch effect to delay navigation
        if (dbInsertionComplete) {
            LaunchedEffect(Unit) {
                delay(3000) // Delay for 3 seconds
                //TODO: call submitRemoteConfigParams  and move to sucess screen once response is ok.
                navController.navigate(Destinations.SuccessfulRemoteConfigurationScreen.toString()) // Replace with your actual route
            }
        }
    }


}

