package com.example.adsnetkyc.presentation.qouikscreens.selectIdentificationMethod

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

import com.example.adsnetkyc.presentation.qouikscreens.common.Heading1
import com.example.adsnetkyc.presentation.qouikscreens.common.header.HeaderScreen
import com.example.vigilum.R
import com.example.vigilum.ui.components.VspacerComponent
import com.example.vigilum.ui.components.generalComponents.Loader
import com.example.vigilum.ui.components.homeComponents.LanguageCard
import com.example.vigilum.ui.home.HomeViewModel
import com.example.vigilum.utils.Destinations

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChooseAnOperationScreen(
    chooseAnOperationViewModel: ChooseAnOperationViewModel = hiltViewModel(),
    navController: NavController,
    selectedLanguage: String
) {

    val fr_heading1 = stringResource(R.string.fr_h1_select_an_operation).uppercase()

    val en_heading1 = stringResource(R.string.en_h1_select_an_operation).uppercase()

    val fr_enrolment = stringResource(R.string.fr_enrolment).uppercase()

    val en_enrolment = stringResource(R.string.en_enrolment).uppercase()


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
            ) {

                VspacerComponent(80)
                //2.Message, Call to action

                VspacerComponent(5)
                if (selectedLanguage == "en") {
                    Heading1(message = "${en_heading1}")
                } else {
                    Heading1(message = "${fr_heading1}")
                }

                VspacerComponent(30)

                //3.Selection items
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f)
                ) {
                    Column() {
                        LanguageCard(
                            modifier = Modifier
                                .width(300.dp)
                                .height(200.dp),
                            route = "${Destinations.SelectIdentificationMethodScreen.toString()}/en",
                            title = "IDENTIFICATION",
                            image = R.drawable.british,
                            navController = navController
                        )
                    }
                    Column() {
                        LanguageCard(
                            modifier = Modifier
                                .width(300.dp)
                                .height(200.dp),
                            route = "${Destinations.AdviceBeforeEnrolment.toString()}/fr",

                            title = if (selectedLanguage == "en") {
                                "${en_enrolment}"
                            } else {
                                "${fr_enrolment}"
                            },
                            image = R.drawable.french,
                            navController = navController
                        )

                    }
                }


            }

        }
    }
}

