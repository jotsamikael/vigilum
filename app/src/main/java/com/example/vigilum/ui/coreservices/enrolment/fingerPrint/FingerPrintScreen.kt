package com.example.vigilum.ui.coreservices.enrolment.fingerPrint

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.vigilum.R
import com.example.adsnetkyc.presentation.qouikscreens.common.FingerPrintAdvice
import com.example.adsnetkyc.presentation.qouikscreens.common.Heading2

import com.example.adsnetkyc.presentation.qouikscreens.common.header.HeaderScreen
import com.example.vigilum.ui.components.VspacerComponent
import com.example.vigilum.ui.components.generalComponents.RectagularButton
import com.example.vigilum.ui.theme.baiJumreeFontFamily
import com.example.vigilum.ui.theme.primaryLight
import com.example.vigilum.utils.Destinations


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun FingerPrintScreen(
    fingerPrintViewModel: FingerPrintViewModel = hiltViewModel(),
    selectedLanguage: String,
    navController: NavController
) {
    // Collecting the latest value from the StateFlow
    val score = fingerPrintViewModel.scoreQuality.collectAsState().value
    val numImgTaken = fingerPrintViewModel.numImgTaken.collectAsState().value
    val context = LocalContext.current

    // Observing the LiveData from ViewModel
    val isStartTriggered by fingerPrintViewModel.isStartTriggered.collectAsState()
    val scoreQual1 by fingerPrintViewModel.scoreQual1.collectAsState()
    val changeFingerCounter by fingerPrintViewModel.changeFingerCounter.collectAsState()
    val fpImage by fingerPrintViewModel.fpImage.collectAsState()

    //when save is pressed
    LaunchedEffect(key1 = Unit) {
        fingerPrintViewModel.submissionStatus.collect { isSuccess ->
            if (isSuccess) {
                //navController.navigate("to_contract_display/${selectedLanguage}")
                navController.navigate("${Destinations.ContractDisplayScreen.toString()}/${selectedLanguage}")
            }
        }
    }


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
                Spacer(Modifier.height(80.dp))
                //Main Start

                Row(horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.8f)) {


                    Column(horizontalAlignment = Alignment.Start) {
                        FingerPrintAdvice(
                            modifier = Modifier.size(260.dp),
                            image = R.drawable.fingerprintpulse
                        )
                    }
                }
                Row() {
                    if (isStartTriggered) {
                        AlertDialog(
                            properties = DialogProperties(usePlatformDefaultWidth = false),
                            modifier = Modifier.width(600.dp), // Set custom size here
                            onDismissRequest = { /* TODO: Handle dismiss */ },
                            confirmButton = {
                                if (numImgTaken >= 3) {
                                    RectagularButton(
                                        160,
                                        50,
                                        "Submit",
                                        primaryLight,
                                        Icons.Rounded.Check
                                    ) {
                                        fingerPrintViewModel.onSubmitClick()
                                        navController.navigate("${Destinations.ContractDisplayScreen.toString()}/${selectedLanguage}")
                                        //navController.navigate("to_contract_display/${selectedLanguage}")
                                    }
                                }

                            },
                            dismissButton = {
                                RectagularButton(
                                    160,
                                    50,
                                    "CANCEL",
                                    primaryLight,
                                    Icons.Rounded.Clear,
                                    {
                                        fingerPrintViewModel.cancelObtainThreeFPImages()
                                    })
                            },
                            title = {
                                Heading2(message = "Scanning Fingerprint...")

                            },
                            text = {
                                Column(horizontalAlignment = Alignment.Start) {
                                    Text(text="Place your finger on the fingerprint scanner", fontFamily = baiJumreeFontFamily,fontSize = 20.sp, lineHeight = 22.sp)
                                    VspacerComponent(12)
                                    Text(text = "Choose one finger and take three images")

                                    Spacer(modifier = Modifier.height(20.dp))
                                    Row(horizontalArrangement = Arrangement.Center) {

                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            if (numImgTaken < 1) {
                                                FingerPrintAdvice(
                                                    modifier = Modifier.size(250.dp),
                                                    image = R.drawable.fpplace
                                                )

                                            } else {
                                                DisplayImage(fpImage, Modifier.size(250.dp))

                                            }

                                        }
                                        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                                           VspacerComponent(30)
                                            Text(
                                                text = "Remaining images",
                                                lineHeight = 16.sp,
                                                fontWeight = FontWeight.Black,
                                                fontSize = 20.sp,
                                                textAlign = TextAlign.Left,
                                                fontFamily = FontFamily.Monospace
                                            )
                                            VspacerComponent( 16)
                                            Text(
                                                text = "${3 - numImgTaken}",
                                                lineHeight = 16.sp,
                                                fontWeight = FontWeight.Black,
                                                fontSize = 96.sp,
                                                textAlign = TextAlign.Left,
                                                fontFamily = FontFamily.Monospace,
                                                color = primaryLight

                                            )
                                            /*scoreQual1.forEachIndexed { index, score ->
                                                Text(
                                                    text = "Img:${index + 1}, score: $score",
                                                    fontWeight = FontWeight.Black,
                                                    fontSize = 12.sp,
                                                    lineHeight = 14.sp,
                                                    textAlign = TextAlign.Left,
                                                    fontFamily = FontFamily.Monospace
                                                )
                                            }*/

                                        }

                                    }

                                }


                            }


                        )
                    }

                }

                Row() {
                    TextButton(
                        onClick = {
                            fingerPrintViewModel.obtainThreeFPImages()
                        }
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Click to start fingerprint scan",
                                fontSize = 24.sp,
                                fontFamily = baiJumreeFontFamily,
                                letterSpacing = 1.5.sp,
                                color = primaryLight,
                                textDecoration = TextDecoration.Underline
                            )
                            VspacerComponent(5)
                            Box(
                                modifier = Modifier
                                    .size(100.dp) // Set the size of the circular box
                                    .clip(CircleShape) // Clip the box to a circle shape
                                    .background(primaryLight) // Set the background color to yellow
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.user),
                                    contentDescription = "Circular Image", // Provide a content description for accessibility
                                    modifier = Modifier.matchParentSize(), // Make the image fill the parent box
                                    contentScale = ContentScale.Fit // Scale the image to maintain aspect ratio
                                )
                            }
                        }
                    }
                }

            }
        }
    }

}

@Composable
fun DisplayImage(bitmap: Bitmap?, modifier: Modifier) {
    bitmap?.asImageBitmap()?.let {
        Image(bitmap = it, contentDescription = "description", modifier)
    }
}

@Composable
fun CircularLoader(modifier: Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .size(75.dp)
            .padding(8.dp)
    ) {
        CircularProgressIndicator(
            modifier = modifier,
            color = Color.DarkGray
        )
    }
}
