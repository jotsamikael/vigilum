package com.example.adsnetkyc.presentation.common

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

import com.example.vigilum.R
import com.example.vigilum.ui.theme.baiJumreeFontFamily

@Composable
fun NotAvailableScreen(navController: NavController) {
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
                    Icons.Rounded.ArrowBack, contentDescription = "back button",
                    tint = Color.White
                )

            }

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Sorry",
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
                Spacer(modifier = Modifier.height(64.dp))

                Text(
                    text = "THIS SERVICE IS NOT AVAILABLE",
                    textAlign = TextAlign.Center,
                    fontFamily = baiJumreeFontFamily,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.labelMedium,
                    color = Color(0xFF444444),
                    modifier = Modifier
                        .padding(top = 30.dp)

                    )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Contact support",
                    fontFamily = baiJumreeFontFamily,
                    fontSize = 24.sp,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color(0xFF444444),

                )

            }



        }
    }
}
