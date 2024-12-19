package com.example.vigilum.ui.coreservices.enrolment.Signature

import android.annotation.SuppressLint
import android.app.Application
import android.os.Environment
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vigilum.R
import com.example.vigilum.ui.components.HspacerComponent
import com.example.vigilum.ui.components.VspacerComponent
import com.example.vigilum.ui.components.generalComponents.RectagularButton
import com.example.vigilum.ui.theme.primaryLight

import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.VerticalPDFReader
import com.rizzi.bouquet.rememberVerticalPdfReaderState
import java.io.File


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ContractDisplayScreen(
    application: Application,
    selectedLanguage: String,
    navController: NavController
) {

    val appName = application.getString(R.string.app_name)
    val fileName = R.string.enroll_form_name

    val dcimDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
    val file = File(dcimDirectory, "$appName/$fileName")

    val uri = Uri.fromFile(file)
    val pdfState = rememberVerticalPdfReaderState(
        resource = ResourceType.Asset(R.raw.enroll),
        isZoomEnable = true
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
                .background(color = Color.Gray)
        ) {
            VerticalPDFReader(
                state = pdfState,
                modifier = Modifier
                    .padding(10.dp)

            )
        }
        Spacer(modifier = Modifier.height(20.dp))

    }

    val backgroundImage = painterResource(id = R.drawable.abstract_white_bg)

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
                VspacerComponent(20)
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.7f)
                ) {
                    //Contract display
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .border(2.dp, primaryLight, RoundedCornerShape(5.dp))
                    ) {
                        VerticalPDFReader(
                            state = pdfState,
                            modifier = Modifier
                                .padding(10.dp)

                        )

                    }

                }
                VspacerComponent(30)

                //Action buttons
                Row(horizontalArrangement = Arrangement.Center,modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .fillMaxHeight(0.3f)) {
                    Column {
                        RectagularButton(
                            160,
                            50,
                            "CANCEL",
                            primaryLight,
                            Icons.Rounded.Clear
                        ) {
                            navController.popBackStack()
                        }
                    }
                    HspacerComponent(20)

                    Column {
                        RectagularButton(
                            160,
                            50,
                            "I Agree",
                            primaryLight,
                            Icons.Rounded.Check
                        ) {
                            navController.navigate("to_signature_collect/${selectedLanguage}")
                        }

                    }
                }

            }
        }


}

