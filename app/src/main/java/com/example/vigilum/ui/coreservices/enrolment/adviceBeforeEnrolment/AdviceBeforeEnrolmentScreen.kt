package com.example.adsnetkyc.presentation.qouikscreens.adviceBeforeEnrolment

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.vigilum.R
import com.example.adsnetkyc.presentation.qouikscreens.common.BulletElement
import com.example.adsnetkyc.presentation.qouikscreens.common.Heading1
import com.example.adsnetkyc.presentation.qouikscreens.common.Heading2
import com.example.adsnetkyc.presentation.qouikscreens.common.header.HeaderScreen
import com.example.vigilum.ui.components.VspacerComponent
import com.example.vigilum.ui.components.generalComponents.RectagularButton
import com.example.vigilum.ui.coreservices.enrolment.adviceBeforeEnrolment.AdviceBeforeEnrolmentViewModel
import com.example.vigilum.ui.theme.baiJumreeFontFamily
import com.example.vigilum.ui.theme.primaryLight

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@Composable
fun AdviceBeforeEnrolmentScreen(
    adviceBeforeEnrolmentViewModel: AdviceBeforeEnrolmentViewModel = hiltViewModel(),
    navController: NavController,
    selectedLanguage: String

) {

    val fr_heading2 = stringResource(R.string.fr_h2_identity_verification)
    val fr_heading1 = stringResource(R.string.fr_h1_we_want_to_know_you_better)

    val en_heading2 = stringResource(R.string.en_h2_identity_verification)
    val en_heading1 = stringResource(R.string.en_h1_we_want_to_know_you_better)

    val fr_guidelines = stringResource(R.string.fr_h2_guidelines)
    val en_guidelines = stringResource(R.string.en_h2_guidelines)


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
                Spacer(Modifier.height(40.dp))
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
                Spacer(Modifier.height(20.dp))

                //3. Circular image
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.3f)
                ) {
                    //Circular image
                    CircularImage(
                        image = R.drawable.kyc_thumb,
                        text = "We will verify your identity to protect out premises. It will only take a few minutes"
                    )
                }

                //Advices
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxHeight(0.6f)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        when (selectedLanguage) {
                            "fr" -> {
                                Heading1(message = fr_guidelines)
                            }

                            "en" -> {
                                Heading1(message = en_guidelines)
                            }
                        }
                        VspacerComponent(10)
                        Column {
                            BulletElement(
                                bulletType = "\u2022",
                                text = "Prepare your national id card or other id document"
                            )
                            BulletElement(
                                bulletType = "\u2022",
                                text = "Remove any thing covering your face"
                            )
                            BulletElement(
                                bulletType = "\u2022",
                                text = "Make sure your fingers are clean and dry"
                            )

                        }
                        VspacerComponent(10)
                        RectagularButton(
                            200,
                            60,
                            "Next",
                            primaryLight,
                            Icons.Rounded.KeyboardArrowRight
                        ) {
                            navController.navigate("to_select_doc_type/${selectedLanguage}")
                        }
                    }

                }

            }
        }

    }
}

@Composable
fun CircularImage(image: Int, text: String) {
    Column() {

        Image(
            painterResource(id = image),
            contentDescription = "circular image",
            modifier = Modifier
                .size(200.dp) // Set the size of the image
                .clip(CircleShape) // Clip the image to a circle
                .align(Alignment.CenterHorizontally) // Center the CircularImage
                .border(2.dp, Color.Gray, CircleShape), // Optional: Add a border

            contentScale = ContentScale.Crop // Crop the image to fill the size
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = text,
            textAlign = TextAlign.Center,
            fontFamily = baiJumreeFontFamily,
            fontSize = 16.sp
        )
    }
}

