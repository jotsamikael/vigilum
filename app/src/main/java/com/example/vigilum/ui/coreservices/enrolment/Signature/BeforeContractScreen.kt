package com.example.vigilum.ui.coreservices.enrolment.Signature

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.adsnetkyc.presentation.qouikscreens.common.header.HeaderScreen
import com.example.vigilum.R
import com.example.vigilum.ui.components.generalComponents.GifImage
import com.example.vigilum.ui.components.generalComponents.NextButton
import com.example.vigilum.ui.theme.baiJumreeFontFamily


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BeforeContractScreen(
    navController: NavController,
    selectedLanguage: String) {

    Scaffold(
        topBar = { HeaderScreen() } // Use your Header here
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.abstract_white_bg),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Row(horizontalArrangement = Arrangement.Start) {
                IconButton(
                    onClick = { navController.popBackStack() },
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "back button",
                        tint = Color.White
                    )

                }

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Contract Signing",
                        color = Color.White,
                        fontSize = 32.sp,
                        fontFamily = baiJumreeFontFamily,
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier
                            .padding(top = 50.dp)
                    )
                }
            }
            Card(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .fillMaxHeight(0.75f),
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                // Your content here
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {

                }

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    //TODO: GIF showing how to place document
                    SquareGifAdvice("Place document as shown in the gif above")

                    //Text Advices
                    BulletList(items = myList)

                    Spacer(modifier = Modifier.height(20.dp))

                    //Next Btn

                    NextButton {
                        navController.navigate(
                            "to_contract_display"
                        )
                    }

                }

            }
        }

    }
}


@Composable
fun BulletList(items: List<String>) {
    LazyColumn {
        items(items) { item ->
            Row(
                modifier = Modifier.padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Canvas(modifier = Modifier
                    .padding(start = 4.dp, end = 4.dp)
                    .size(6.dp)) {
                    drawCircle(Color.Black)
                }
                Text(text = item, fontSize = 16.sp, color = Color(0xFF07073D))
            }
        }
    }
}

@Composable
fun SquareGifAdvice(text: String) {
    Column() {

        GifImage(modifier = Modifier
            .size(300.dp) // Set the size of the image
            .clip(RectangleShape) // Clip the image to a circle
            .align(Alignment.CenterHorizontally), // Center the CircularImage
            R.drawable.signature
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


// Usage
val myList = listOf(
    "Read carefully the following contract",
    "Sign once you have fully read and understood"
)
