package com.example.vigilum.ui.coreservices.enrolment.idDocImage.recto

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.adsnetkyc.presentation.qouikscreens.common.header.HeaderScreen
import com.example.vigilum.MainActivity
import com.example.vigilum.R
import com.example.vigilum.ui.components.HspacerComponent
import com.example.vigilum.ui.components.VspacerComponent
import com.example.vigilum.ui.components.generalComponents.GifImage
import com.example.vigilum.ui.components.generalComponents.RectagularButton
import com.example.vigilum.ui.components.generalComponents.VigilumCameraPreview
import com.example.vigilum.ui.theme.primaryLight
import com.example.vigilum.utils.Destinations
import com.example.vigilum.utils.Gradients


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun IdDocImageScanScreenRecto(
    idDocImageScanRectoViewModel: IdDocImageScanRectoViewModel = hiltViewModel(),
    activity: Activity,
    selectedLanguage: String,
    selectedDocType: String,
    navController: NavController
) {

    val context = LocalContext.current

    val controller = remember {
        LifecycleCameraController(
            activity.applicationContext
        ).apply {
            setEnabledUseCases(
                CameraController.IMAGE_CAPTURE or
                        CameraController.VIDEO_CAPTURE
            )
        }
    }


    val imageUri by idDocImageScanRectoViewModel.imageUri.collectAsState()
    val isPictureTaken = idDocImageScanRectoViewModel.isPictureTaken.collectAsState()
    var isProcessing = idDocImageScanRectoViewModel.processing.collectAsState()

    LaunchedEffect(key1 = Unit) {
        idDocImageScanRectoViewModel.submittedSuccessfully.collect { isSuccess ->
            if (isSuccess) {
                Log.i("selectedDocType","${selectedDocType}")
                navController.navigate("to_id_doc_pic_verso/${selectedLanguage}/${selectedDocType}") // Replace with your destination route
            }
        }
    }

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
                VspacerComponent(40)
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.8f)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        VigilumCameraPreview(
                            controller,
                            "Place your document in the camera preview recto"
                        )
                        VspacerComponent(10)
                        if (!isPictureTaken.value) {
                            TakePhotoButtonRecto(
                                activity,
                                context,
                                controller,
                                idDocImageScanRectoViewModel
                            )
                        } else if(isPictureTaken.value && !isProcessing.value){
                            Row {
                                RectagularButton(
                                    width = 200,
                                    height = 50,
                                    text = "Cancel",
                                    color = primaryLight,
                                    icon = Icons.Rounded.Clear
                                ) {
                                    idDocImageScanRectoViewModel.onCancel()
                                }
                                HspacerComponent(10)
                                RectagularButton(
                                    width = 200,
                                    height = 50,
                                    text = "Next",
                                    color = primaryLight,
                                    icon = Icons.Rounded.Check
                                ) {
                                    idDocImageScanRectoViewModel.submitDocument(
                                        context,
                                        imageUri!!,
                                        selectedDocType
                                    )
                                }
                            }
                        }
                        else if(isPictureTaken.value && isProcessing.value){
                            Log.i("SHOWLOADING", "show loading gif")
                            GifImage(Modifier.size(60.dp),R.drawable.loading)
                        }
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Card(
                            modifier =
                            Modifier
                                .width(550.dp)
                                .height(350.dp)
                        ) {
                            Image(
                                painter = if (imageUri != null) rememberAsyncImagePainter(imageUri) else painterResource(
                                    id = R.drawable.cni_recto
                                ),
                                contentScale = ContentScale.FillBounds,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TakePhotoButtonRecto(
    activity: Activity,
    context: Context,
    controller: LifecycleCameraController,
    idDocImageScanRectoViewModel: IdDocImageScanRectoViewModel = hiltViewModel(),
) {
    Box(
        modifier = Modifier
            .size(64.dp)
            .clip(CircleShape)
            .background(Gradients.cardGradientBlue)
            .clickable {
                if ((activity as MainActivity).arePermissionsGranted()) {
                    Log.i("TAKEPHOTO","picture")
                    idDocImageScanRectoViewModel.onTakePhoto(controller, context)


                }
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_photo_camera_24), // Replace with your camera icon
            contentDescription = "Camera Icon",
            tint = Color.White,
            modifier = Modifier.size(48.dp)
        )
    }
}