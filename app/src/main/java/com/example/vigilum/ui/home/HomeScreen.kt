package com.example.vigilum.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.adsnetkyc.presentation.qouikscreens.common.Heading1
import com.example.adsnetkyc.presentation.qouikscreens.common.Heading2
import com.example.vigilum.R
import com.example.vigilum.ui.components.VspacerComponent
import com.example.vigilum.ui.components.homeComponents.LanguageCard
import com.example.vigilum.ui.theme.primaryLight
import com.example.vigilum.utils.Destinations


@SuppressLint("SuspiciousIndentation")
@Composable
fun HomeScreen(homeViewModel: HomeViewModel = viewModel(), navController: NavController) {

    // Define your background image
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
        ) {

            VspacerComponent(80)
            //2.Message, Call to action
            Heading2(message = "Choose your | Choisir la ")
            VspacerComponent(5)
            Heading1(message = "language | langue")
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
                        route = "${Destinations.ChooseAnOperationScreen.toString()}/en",
                        title = "ENGLISH",
                        image = R.drawable.british,
                        navController = navController
                    )
                }
                Column() {
                    LanguageCard(
                        modifier = Modifier
                            .width(300.dp)
                            .height(200.dp),
                        route = "${Destinations.ChooseAnOperationScreen.toString()}/fr",
                        title = "FRANÇAIS",
                        image = R.drawable.french,
                        navController = navController
                    )

                }
            }


        }

    }

}
