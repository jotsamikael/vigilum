package com.example.vigilum.ui.components.generalComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.vigilum.data.remote.response.GDZService
import com.example.vigilum.ui.components.DIMENS.ServiceCardSize
import com.example.vigilum.ui.theme.baiJumreeFontFamily
import com.example.vigilum.ui.theme.primaryLight
import com.example.vigilum.utils.Gradients.cardGradientWhite


@Composable
fun ServiceCard(
    modifier: Modifier,
    gdzService: GDZService,
    navController: NavController,
    selectedLanguage:String
) {
    val context = LocalContext.current


    Card(
        modifier = modifier.width(300.dp)
            .clickable {
                navController.navigate(gdzService.navigation+"/"+selectedLanguage)
            }
            .padding(20.dp),
        elevation = CardDefaults.cardElevation(25.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = cardGradientWhite
                )

        ) {

            Column(
                modifier = modifier

                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                AsyncImage(
                    modifier = Modifier
                        .size(ServiceCardSize)
                        .clip(MaterialTheme.shapes.medium),

                    contentScale = ContentScale.Crop,
                    model = ImageRequest.Builder(context).data(gdzService.iconUrl).build(),
                    contentDescription = null

                )
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = gdzService.name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = baiJumreeFontFamily,
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

