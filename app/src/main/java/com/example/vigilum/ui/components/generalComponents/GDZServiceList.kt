package com.example.adsnetkyc.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.vigilum.data.remote.response.GDZService
import com.example.vigilum.ui.components.DIMENS.mediumPadding1
import com.example.vigilum.ui.components.generalComponents.ServiceCard


@Composable
fun GDZServiceList(
    modifier: Modifier = Modifier,
    gdzServices: List<GDZService>,
    navController: NavController,
    selectedLanguage:String
) {
    Box(modifier = Modifier.fillMaxSize()) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = modifier

                .align(Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(mediumPadding1),
            //contentPadding = PaddingValues(all = extraSmallPadding)
        ) {
            items(
                count = gdzServices.size,
            ) {
                gdzServices[it]?.let { gdzService ->
                    Row(horizontalArrangement = Arrangement.Center,modifier = Modifier.width(100.dp) ) {
                        ServiceCard(
                            modifier,
                            gdzService,
                            navController = navController,
                            selectedLanguage
                        )
                    }
                }
            }
        }
    }
}

