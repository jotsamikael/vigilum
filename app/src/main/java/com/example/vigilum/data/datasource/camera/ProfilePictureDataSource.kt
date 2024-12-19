package com.example.vigilum.data.datasource.camera

import android.content.Context
import android.net.Uri
import androidx.camera.view.LifecycleCameraController
import com.example.vigilum.data.remote.response.EmptyResponseDto
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface ProfilePictureDataSource {

    val imageUri: StateFlow<Uri?>
    val base64Img: SharedFlow<String?>
    suspend fun submitSelfie(context: Context, imageUri: Uri): EmptyResponseDto

    suspend fun takeSelfie(
        controller: LifecycleCameraController,
        context: Context
    )
}