package com.example.vigilum.ui.coreservices.identification.identificationMethods.phoneIdentification

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.adsnetkyc.presentation.qouikscreens.common.Heading1
import com.example.adsnetkyc.presentation.qouikscreens.common.Heading2
import com.example.vigilum.ui.components.generalComponents.TextInputBar
import com.example.adsnetkyc.presentation.qouikscreens.common.header.HeaderScreen
import com.example.vigilum.R
import com.example.vigilum.ui.components.HspacerComponent
import com.example.vigilum.ui.components.VspacerComponent
import com.example.vigilum.ui.components.generalComponents.VigilumEnrollButton
import com.example.vigilum.ui.components.generalComponents.VigilumSubmitButton
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PhoneIdentificationScreen(
    phoneIdentificationViewModel: PhoneIdentificationViewModel = hiltViewModel(),
    navController: NavController,
    selectedLanguage: String

) {
    val fr_heading2 = stringResource(R.string.fr_h2_enter_your_phone)
    val fr_heading1 = stringResource(R.string.fr_h1_number)

    val en_heading2 = stringResource(R.string.en_h2_enter_your_phone)
    val en_heading1 = stringResource(R.string.en_h1_number)
    val phoneNumber = remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf("") }
    val phoneNumberPattern = "[0-9]+".toRegex()
    val coroutineScope = rememberCoroutineScope()
    val noVisitorAccountFound = phoneIdentificationViewModel.noVisitorAccountFound.collectAsState()


    // Define your background image
    val backgroundImage = painterResource(id = R.drawable.abstract_white_bg)
    Scaffold(
        topBar = { HeaderScreen() } // Use your Header here
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Image(
                painter = backgroundImage,
                contentDescription = "Background Image",
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.FillBounds
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp)
            ) {
                Spacer(Modifier.height(70.dp))
                //2.Message, Call to action
                when (selectedLanguage) {
                    "fr" -> {
                        Heading2(message = fr_heading2)
                        Spacer(Modifier.height(5.dp))
                        Heading1(message = fr_heading1)
                    }

                    "en" -> {
                        Heading2(message = en_heading2)
                        Spacer(Modifier.height(5.dp))
                        Heading1(message = en_heading1)
                    }
                }
                Spacer(Modifier.height(30.dp))
                //Input Phone Number
                TextInputBar(
                    Modifier.fillMaxWidth(0.5f),
                    phoneNumber.value,
                    "          697895463",
                    54,
                    Color.Gray,
                    KeyboardType.Phone,
                    "PhoneNumber", // New parameter for validation type
                    onValueChanged = {
                        phoneNumber.value = it
                    } // Update the state when the text changes
                )
                VspacerComponent(10)

                if (showError) {
                    Text(
                        text = errorMessage.value,
                        fontSize = 18.sp,
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(start = 20.dp)
                    )
                }

                if (noVisitorAccountFound.value) {
                    VspacerComponent(30)
                    Row(horizontalArrangement = Arrangement.Center) {
                        VigilumSubmitButton(
                            onClick = {
                                if (phoneNumber.value.length != 9 || !phoneNumberPattern.matches(
                                        phoneNumber.value
                                    )
                                ) {
                                    showError = true
                                    errorMessage.value = "Incorrect phone number"
                                } else {
                                    // Proceed with submission
                                    showError = false
                                    errorMessage.value = ""
                                    // Launch a coroutine to call the suspend function
                                    coroutineScope.launch {
                                        /*try {
                                            val response =
                                                phoneIdentificationViewModel.identifyVisitor(
                                                    phoneNumber.value
                                                )
                                            // Check if the response indicates success
                                            if (response.status.equals("200")) {
                                                Log.i(
                                                    "VISITORIS",
                                                    "${response.data.lastname}"
                                                )
                                                // Prepare the data to pass to the next screen
                                                var visitor = response.data
                                                // Create a Bundle to hold visitor info
                                                val encodedProfileUrl = URLEncoder.encode(
                                                    response.data.profilePhoto,
                                                    StandardCharsets.UTF_8.toString()
                                                )
                                                val encodedSignatureUrl = URLEncoder.encode(
                                                    response.data.signature,
                                                    StandardCharsets.UTF_8.toString()
                                                )

                                                val firstname = response.data.firstname
                                                val lastname = response.data.lastname
                                                val sex = response.data.sex
                                                val phone = response.data.phone
                                                val documentNumber =
                                                    response.data.documentIdNumber
                                                val enterprise = response.data.enterprise

                                                navController.navigate("to_visitor_profile/${selectedLanguage}/${firstname}/${lastname}/${sex}/${phone}/${documentNumber}/${enterprise}/${encodedProfileUrl}/${encodedSignatureUrl}")

                                            } else {
                                                // Display an error message
                                                showError = true
                                                errorMessage.value = "Failed to identify visitor"
                                            }
                                        } catch (e: Exception) {
                                            // Handle exceptions
                                            showError = true
                                            errorMessage.value = "An error occurred"
                                            Log.i("EXCEPTION", "${e}")

                                        } */
                                    }
                                }

                            },
                            modifier = Modifier.fillMaxWidth(.1f)
                        )
                        HspacerComponent( 30)

                        VigilumEnrollButton({
                            //go to enrollment steps

                        }, Modifier.fillMaxWidth(0.1f))
                    }
                }

                VspacerComponent(30)

                if (!noVisitorAccountFound.value) {
                    VigilumSubmitButton(
                        onClick = {
                            if (phoneNumber.value.length != 9 || !phoneNumberPattern.matches(
                                    phoneNumber.value
                                )
                            ) {
                                showError = true
                                errorMessage.value = "Incorrect phone number"
                            } else {
                                // Proceed with submission
                                showError = false
                                errorMessage.value = ""
                                // Launch a coroutine to call the suspend function
                                coroutineScope.launch {
                                   /* try {
                                        val response =
                                            phoneIdentificationViewModel.identifyVisitor(phoneNumber.value)
                                        // Check if the response indicates success
                                        if (response.status.equals("200")) {
                                            Log.i(
                                                "VISITORIS",
                                                "${response.data.lastname}"
                                            )
                                            // Prepare the data to pass to the next screen
                                            var visitor = response.data
                                            // Create a Bundle to hold visitor info
                                            val encodedProfileUrl = URLEncoder.encode(
                                                response.data.profilePhoto,
                                                StandardCharsets.UTF_8.toString()
                                            )
                                            val encodedSignatureUrl = URLEncoder.encode(
                                                response.data.signature,
                                                StandardCharsets.UTF_8.toString()
                                            )

                                            val firstname = response.data.firstname
                                            val lastname = response.data.lastname
                                            val sex = response.data.sex
                                            val phone = response.data.phone
                                            val documentNumber =
                                                response.data.documentIdNumber
                                            val enterprise = response.data.enterprise

                                            navController.navigate("to_visitor_profile/${selectedLanguage}/${firstname}/${lastname}/${sex}/${phone}/${documentNumber}/${enterprise}/${encodedProfileUrl}/${encodedSignatureUrl}")

                                        } else {
                                            // Display an error message
                                            showError = true
                                            errorMessage.value =
                                                "Failed to identify visitor, please enroll"
                                        }
                                    } catch (e: Exception) {
                                        // Handle exceptions
                                        showError = true
                                        errorMessage.value = "An error occurred"
                                        Log.i("EXCEPTION", "${e}")

                                    }*/
                                }
                            }

                        },
                        modifier = Modifier.fillMaxWidth(.1f)
                    )
                }

            }
        }
    }
}




