package com.example.vigilum.ui.coreservices.enrolment.Selfie

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.OptIn
import androidx.annotation.RequiresApi
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter


import com.example.adsnetkyc.presentation.qouikscreens.common.header.HeaderScreen
import com.example.adsnetkyc.presentation.qouikscreens.enrollSteps.Selfie.ProfilePictureViewModel
import com.example.vigilum.MainActivity
import com.example.vigilum.R
import com.example.vigilum.ui.components.HspacerComponent
import com.example.vigilum.ui.components.VspacerComponent
import com.example.vigilum.ui.components.generalComponents.GifImage
import com.example.vigilum.ui.components.generalComponents.RectagularButton
import com.example.vigilum.ui.theme.primaryDark
import com.example.vigilum.ui.theme.primaryLight
import com.example.vigilum.utils.Gradients
import com.google.mlkit.vision.common.InputImage


@OptIn(ExperimentalGetImage::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfilePictureScreen(
    profilePictureViewModel: ProfilePictureViewModel = hiltViewModel(),
    activity: Activity,
    selectedLanguage: String,
    navController: NavController
) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current


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
    val imageUri by profilePictureViewModel.imageUri.collectAsState()
    val isPictureTaken = profilePictureViewModel.isPictureTaken.collectAsState()
    val isFaceDetected = profilePictureViewModel.isFaceDetected.collectAsState()
    var isProcessing = profilePictureViewModel.processing.collectAsState()



    LaunchedEffect(key1 = Unit) {
        profilePictureViewModel.submittedSuccessfully.collect { isSuccess ->
            if (isSuccess) {
                navController.navigate("to_enrolment_form/${selectedLanguage}") // Replace with your destination route
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.8f)
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        VigilumSelfieCameraPreview(
                            controller,
                            profilePictureViewModel,
                            "Pace your face in the preview an take a picture"
                        )
                        VspacerComponent(10)
                        if (isPictureTaken.value == false && isFaceDetected.value == true) {
                            Log.d("FaceDetection", "Face detected, showing Take Photo button")
                            TakePhotoButtonSelfie(
                                activity,
                                context,
                                controller,
                                profilePictureViewModel
                            )
                        } else if (isPictureTaken.value == true) {
                            Row {
                                RectagularButton(
                                    width = 200,
                                    height = 50,
                                    text = "Cancel",
                                    color = primaryLight,
                                    icon = Icons.Rounded.Clear
                                ) {
                                    profilePictureViewModel.onCancel()
                                }
                                HspacerComponent(10)
                                RectagularButton(
                                    width = 200,
                                    height = 50,
                                    text = "Next",
                                    color = primaryDark,
                                    icon = Icons.Rounded.Check
                                ) {
                                    profilePictureViewModel.submitDocument(
                                        context,
                                        imageUri!!
                                    )
                                }
                            }
                        } else if (isFaceDetected.value == false) {
                            Log.d("FaceDetection", "No face detected, showing no buttons")

                            Text(
                                text = "No face detected. Please align your face with the preview.",
                                color = Color.Red,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(16.dp)
                            )
                        } else if (isPictureTaken.value && isProcessing.value) {

                            GifImage(Modifier.size(60.dp), R.drawable.loading)
                        }
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        Box(
                            modifier = Modifier
                                .width(350.dp)
                                .height(350.dp)
                                .clip(CircleShape) // This makes the Box circular
                                .background(Color.Transparent)
                        ) {
                            Image(
                                painter = if (imageUri != null) rememberAsyncImagePainter(
                                    imageUri
                                ) else painterResource(id = R.drawable.profile),
                                contentScale = ContentScale.Crop, // Use Crop to maintain aspect ratio
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize() // Make the image fill the entire Box
                            )
                        }

                    }
                }

            }
        }
    }

}


@OptIn(ExperimentalGetImage::class)
@Composable
fun VigilumSelfieCameraPreview(
    controller: LifecycleCameraController,
    profilePictureViewModel: ProfilePictureViewModel,
    text: String
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val faceContours = profilePictureViewModel.faceContours.collectAsState()
    val previewViewSize = remember { mutableStateOf(Size(1f, 1f)) }


    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .width(350.dp)
                .height(350.dp)
                .clip(CircleShape) // This makes the Box circular
                .background(Color.Transparent)
        ) {
            // AndroidView for preview
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { previewViewContext ->
                    PreviewView(previewViewContext).apply {
                        this.controller = controller
                        this.post {
                            previewViewSize.value = Size(this.width.toFloat(), this.height.toFloat())
                        }
                        controller.bindToLifecycle(lifecycleOwner)
                        controller.cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                        controller.setLinearZoom(1f)


                        // Obtain ProcessCameraProvider
                        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
                        cameraProviderFuture.addListener({
                            val cameraProvider = cameraProviderFuture.get()
                            val imageAnalysis = ImageAnalysis.Builder()
                                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                                .build()

                            imageAnalysis.setAnalyzer(
                                ContextCompat.getMainExecutor(context)
                            ) { imageProxy ->
                                // Delegate face detection to the ViewModel
                                val rotationDegrees = imageProxy.imageInfo.rotationDegrees
                                val inputImage = imageProxy.image?.let { image ->
                                    InputImage.fromMediaImage(image, rotationDegrees)
                                }
                                if (inputImage != null) {
                                    profilePictureViewModel.detectFaces(imageProxy)
                                } else {
                                    imageProxy.close() // Close if inputImage creation fails
                                }
                            }


                            try {
                                //cameraProvider.unbindAll()
                                cameraProvider.bindToLifecycle(
                                    lifecycleOwner,
                                    CameraSelector.DEFAULT_BACK_CAMERA,
                                    imageAnalysis
                                )
                            } catch (exc: Exception) {
                                Log.e("CameraBind", "Use case binding failed", exc)
                            }
                        }, ContextCompat.getMainExecutor(context))
                    }
                }
            )
            // Overlay for Face Contours
            CustomOverlay(
                faceContours = faceContours.value,
                previewViewSize =  previewViewSize.value
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = text)
    }
}



@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun TakePhotoButtonSelfie(
    activity: Activity,
    context: Context,
    controller: LifecycleCameraController,
    profilePictureViewModel: ProfilePictureViewModel = hiltViewModel(),
) {
    Box(
        modifier = Modifier
            .size(64.dp)
            .clip(CircleShape)
            .background(Gradients.cardGradientBlue)
            .clickable {
                if ((activity as MainActivity).arePermissionsGranted()) {
                    profilePictureViewModel.onTakePhoto(controller, context)


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
