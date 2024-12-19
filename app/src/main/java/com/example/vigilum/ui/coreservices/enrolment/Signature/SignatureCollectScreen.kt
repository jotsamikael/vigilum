package com.example.vigilum.ui.coreservices.enrolment.Signature

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.adsnetkyc.presentation.qouikscreens.common.Heading1
import com.example.adsnetkyc.presentation.qouikscreens.common.header.HeaderScreen
import com.example.vigilum.R

import com.example.vigilum.ui.components.HspacerComponent
import com.example.vigilum.ui.components.VspacerComponent
import com.example.vigilum.ui.components.generalComponents.RectagularButton
import com.example.vigilum.ui.theme.primaryLight

import io.ak1.drawbox.DrawBox
import io.ak1.drawbox.rememberDrawController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SignatureCollectScreen(
    signatureViewModel: SignatureViewModel = hiltViewModel(),
    selectedLanguage: String,
    navController: NavController
) {
    val context = LocalContext.current
    val painter: Painter =
        painterResource(id = R.drawable.paper_bg) // replace with your image resource
    val lines = remember {
        mutableStateListOf<Line>()
    }

    val controller = rememberDrawController()
    controller.setStrokeWidth(5f)
    controller.setStrokeColor(Color(0xff000000))

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
            ) {
                VspacerComponent(80)
                Row(horizontalArrangement = Arrangement.Center) {
                    Heading1(message = "Sign in the space provided ")
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.8f)
                ) {

                    //Sign Area
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {

                        Card(
                            modifier = Modifier
                                .fillMaxHeight(0.7f)
                                .aspectRatio(16f / 9f)
                                .border(2.dp, primaryLight, RoundedCornerShape(5.dp)),
                            shape = RoundedCornerShape(16.dp),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.CenterHorizontally)
                            ) {
                                Image(
                                    painter = painter,
                                    contentDescription = null, // decorative
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    DrawBox(
                                        drawController = controller,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .weight(1f, true)
                                    )
                                }
                            }
                        }

                        VspacerComponent(10)
                        Row(
                            horizontalArrangement = Arrangement.Center, modifier = Modifier
                                .fillMaxWidth(0.6f)
                                .fillMaxHeight(0.3f)
                        ) {
                            Column {
                                RectagularButton(
                                    160,
                                    50,
                                    "Reset",
                                    primaryLight,
                                    Icons.Rounded.Refresh
                                ) {
                                    controller.reset()
                                }
                            }
                            HspacerComponent(20)
                            if (signatureViewModel.isConfirmDialogShown) {
                                ConfirmDialog(
                                    onDismiss = {
                                        signatureViewModel.onDismissClick()
                                    },
                                    onConfirm = {
                                        // Capture content
                                        val bitmap = controller.getDrawBoxBitmap()
                                        val base64Stri = signatureViewModel.convertBitmapToBase64(
                                            bitmap!!
                                        )
                                        Log.i("base64Stri", base64Stri.toString())
                                        signatureViewModel.postSignatureData(base64String = base64Stri)

                                        Toast.makeText(context, "send", Toast.LENGTH_LONG).show()
                                        navController.navigate("to_success")
                                    },
                                    controller.getDrawBoxBitmap()!!

                                )
                            }


                            Column {
                                RectagularButton(
                                    160,
                                    50,
                                    "Submit",
                                    primaryLight,
                                    Icons.Rounded.Check
                                ) {
                                    signatureViewModel.onSubmitClick()

                                }

                            }
                        }

                    }

                }


            }

        }
    }
}
@Composable
fun ConfirmDialog(
    onDismiss:()->Unit,
    onConfirm:()->Unit,
    imageBitmap: Bitmap
){
    Dialog(
        onDismissRequest={
            onDismiss()
        },
        properties = DialogProperties(
            //usePlatformDefaultWidth = false
        )
    ){
        Card(
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .border(1.dp, color = primaryLight, shape = RoundedCornerShape(15.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                verticalArrangement =Arrangement.Center
            ){
                Text(text = "Are you sure you want to submit this signature?", textAlign = TextAlign.Center, fontSize = 20.sp)
                Column() {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Image(bitmap = imageBitmap.asImageBitmap(), contentDescription ="signature" )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(onClick = {
                            onDismiss()
                        },
                            colors = ButtonDefaults.buttonColors(
                                primaryLight,
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            shape = CircleShape
                        ) {
                            Text(text = "Cancel")

                        }
                        Spacer(modifier = Modifier.width(20.dp))
                        Button(onClick = {
                            onConfirm()
                        },
                            colors = ButtonDefaults.buttonColors(
                                primaryLight,
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            shape = CircleShape
                        ) {
                            Text(text = "Submit")

                        }
                    }
                }

            }
        }
    }
}