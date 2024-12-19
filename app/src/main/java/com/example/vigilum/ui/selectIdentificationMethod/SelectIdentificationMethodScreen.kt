package com.example.adsnetkyc.presentation.qouikscreens.selectIdentificationMethod

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.adsnetkyc.presentation.common.GDZServiceList
import com.example.adsnetkyc.presentation.qouikscreens.common.Heading1
import com.example.adsnetkyc.presentation.qouikscreens.common.Heading2
import com.example.adsnetkyc.presentation.qouikscreens.common.header.HeaderScreen
import com.example.utilities.ResourceState
import com.example.vigilum.R
import com.example.vigilum.ui.components.DIMENS
import com.example.vigilum.ui.components.generalComponents.EmptyStateComposable
import com.example.vigilum.ui.components.generalComponents.Loader

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SelectIdentificationMethodScreen(
    selectIdentificationMethodViewModel: SelectIdentificationMethodViewModel = hiltViewModel(),
    navController: NavController,
    selectedLanguage: String
) {
    //whenever there is a change on gdzService, we will have the updated value on receiptResponse
    val identificationMethodsResponse by selectIdentificationMethodViewModel.identificationMethods.collectAsState()

    val fr_heading2 = stringResource(R.string.fr_h2_select_a_method)
    val fr_heading1 = stringResource(R.string.fr_h1_select_a_method)

    val en_heading2 = stringResource(R.string.en_h2_select_a_method)
    val en_heading1 = stringResource(R.string.en_h1_select_a_method)


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
                Spacer(Modifier.height(80.dp))
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
                Spacer(Modifier.height(30.dp))

                //3.Selection items
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.6f)
                ) {
                    //managing api response state
                    when (identificationMethodsResponse) {

                        is ResourceState.Loading<*> -> {
                            Log.d("Inside_Loading", "Inside_Loading")
                            Loader(
                                modifier = Modifier
                                    .size(100.dp)
                                    .padding(10.dp)
                            )
                        }

                        is ResourceState.Success<*> -> {
                            val response =
                                (identificationMethodsResponse as ResourceState.Success).data
                            Log.d(
                                "Inside_Success",
                                "Inside_Success ${response.status} = ${response.totalResults}"
                            )

                            if (response.objectList.isNotEmpty()) {

                                GDZServiceList(
                                    modifier = Modifier.padding(horizontal = DIMENS.mediumPadding1),
                                    response.objectList,
                                    navController,
                                    selectedLanguage
                                )

                            } else {
                                //Response has no services
                                EmptyStateComposable("No Services Available for this device")
                            }
                        }

                        is ResourceState.Error<*> -> {
                            Log.d("Inside_Error", "Inside_Error")
                            EmptyStateComposable("Out of service")

                        }
                    }

                }
            }
        }
    }
}
