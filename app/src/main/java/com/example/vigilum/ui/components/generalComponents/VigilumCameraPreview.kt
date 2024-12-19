package com.example.vigilum.ui.components.generalComponents

import androidx.camera.core.CameraSelector
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView


@Composable
fun VigilumCameraPreview(controller: LifecycleCameraController, text: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Box(
            modifier = Modifier
                .width(550.dp)
                .height(350.dp)
                .background(Color.Transparent)
        ) {
            val lifecycleOwner = LocalLifecycleOwner.current

            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = {
                    PreviewView(it).apply {
                        this.controller = controller
                        controller.bindToLifecycle(lifecycleOwner)
                        controller.cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
                        controller.setLinearZoom(1f)

                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = text)
    }
}

