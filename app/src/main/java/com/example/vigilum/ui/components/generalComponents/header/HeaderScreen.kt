package com.example.adsnetkyc.presentation.qouikscreens.common.header

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.vigilum.R
import com.example.vigilum.ui.theme.Grey
import com.example.vigilum.ui.theme.baiJumreeFontFamily

import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HeaderScreen(viewModel: HeaderScreenViewModel = hiltViewModel()) {
    var dateString by remember { mutableStateOf("") }
    var timeString by remember { mutableStateOf("") }

    LaunchedEffect(key1 = viewModel.dateTime.collectAsState()) {
        viewModel.dateTime.collect { dateTime ->
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
            val formattedDateTime = dateTime.format(formatter)
            dateString = formattedDateTime.dropLast(8)
            timeString = formattedDateTime.drop(10)
        }
    }

    Header(dateString, timeString)
}

@Composable
fun Header(dateString: String, timeString: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top,
        modifier = Modifier
            .fillMaxWidth()
            .height(85.dp)
            .padding(top = 10.dp)
    ) {
        Column(modifier = Modifier.width(IntrinsicSize.Min)) {
            // Adsnet
            Image(painter = painterResource(id = R.drawable.adsnet), modifier = Modifier.size(90.dp),contentDescription = null)
        }
        Column() {
            // App logo
            Image(painter = painterResource(id = R.drawable.vigilumlogo),
                modifier = Modifier.size(250.dp)
                ,contentDescription = null)
        }
        Column(modifier = Modifier.width(IntrinsicSize.Min).padding(top = 20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                // Date
                Text(
                    text = dateString,
                    fontFamily = baiJumreeFontFamily,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    letterSpacing = 1.sp,
                    color = Grey,
                )

                Text(
                    text = timeString,
                    fontFamily = baiJumreeFontFamily,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    letterSpacing = 1.sp,
                    color = Grey
                )

        }
    }
}