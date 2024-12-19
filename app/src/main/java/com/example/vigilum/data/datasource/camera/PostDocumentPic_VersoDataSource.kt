package com.example.vigilum.data.datasource.camera

import android.content.Context
import android.net.Uri
import androidx.camera.view.LifecycleCameraController
import com.example.vigilum.data.remote.response.EmptyResponseDto
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface PostDocumentPic_VersoDataSource {
    val imageUri: StateFlow<Uri?>
    val base64Img: SharedFlow<String?>
    suspend fun submitDocument(context: Context, imageUri: Uri, documentType: String): EmptyResponseDto

    suspend fun takePhoto(
        controller: LifecycleCameraController,
        context: Context
    )
}