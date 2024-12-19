package com.example.vigilum.ui.components.generalComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vigilum.ui.theme.primaryLight
import com.example.vigilum.utils.Gradients

@Composable
fun CardButton(
    modifier: Modifier,
    route: String,
    title: String,
    navController: NavController
) {
    val context = LocalContext.current


    Card(
        modifier = modifier
            .clickable {
                navController.navigate(route)
            }
            .padding(20.dp),
        elevation = CardDefaults.cardElevation(50.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Gradients.cardGradientWhite
                )

        ) {

            Column(
                modifier = modifier

                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,

                    style = MaterialTheme.typography.bodyMedium,
                    color = primaryLight,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 10.dp)
                )


            }
        }
    }
}