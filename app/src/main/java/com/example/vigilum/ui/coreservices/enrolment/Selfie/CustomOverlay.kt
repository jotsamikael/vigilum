package com.example.vigilum.ui.coreservices.enrolment.Selfie

import android.graphics.PointF
import android.util.Log
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color

//This composable is used to draw face line contours over camera preview
@Composable
fun CustomOverlay(faceContours: List<List<PointF>>, previewViewSize: Size) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        // Get the scaling factors for the preview size and canvas size
        val scaleX = size.width / previewViewSize.width
        val scaleY = size.height / previewViewSize.height

        faceContours.forEach { contour ->
            contour.forEach { point ->
                val x = point.x * scaleX
                val y = point.y * scaleY

                // Log the transformed coordinates
                Log.d("CustomOverlay", "Point transformed: ($x, $y)")

                drawCircle(
                    color = Color.Red,
                    radius = 2f,
                    center = Offset(x-150, y-100)
                )
            }
        }
    }
}
