package com.example.vigilum.ui.coreservices.enrolment.idDocImage.SelectDocType

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.adsnetkyc.presentation.qouikscreens.common.Heading1
import com.example.adsnetkyc.presentation.qouikscreens.common.Heading2
import com.example.adsnetkyc.presentation.qouikscreens.common.header.HeaderScreen
import com.example.vigilum.R
import com.example.vigilum.ui.components.VspacerComponent
import com.example.vigilum.ui.components.homeComponents.LanguageCard

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SelectDocTypeScreen(selectDocTypeViewModel: SelectDocTypeViewModel = hiltViewModel(),
                        selectedLanguage:String,
                        navController: NavController
                        ) {

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
                Heading2(message = "Choose your | Choisir la ")
                VspacerComponent(5)
                Heading1(message = "language | langue")
                VspacerComponent(30)

                //3.Selection items
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f)
                ) {
                    Column() {
                        LanguageCard(
                            modifier = Modifier
                                .width(300.dp)
                                .height(200.dp),
                            route = "to_id_doc_pic_recto/$selectedLanguage/OLD_NATIONAL_ID",
                            title = "Old ID Card",
                            image = R.drawable.british,
                            navController = navController
                        )
                    }
                    Column() {
                        LanguageCard(
                            modifier = Modifier
                                .width(300.dp)
                                .height(200.dp),
                            route = "to_id_doc_pic_recto/$selectedLanguage/NEW_NATIONAL_ID",
                            title = "New ID Card",
                            image = R.drawable.french,
                            navController = navController
                        )

                    }
                    Column() {
                        LanguageCard(
                            modifier = Modifier
                                .width(300.dp)
                                .height(200.dp),
                            route = "to_id_doc_pic_recto/$selectedLanguage/RECEIPT",
                            title = "Receipt",
                            image = R.drawable.french,
                            navController = navController
                        )

                    }
                }


            }

        }
    }
}