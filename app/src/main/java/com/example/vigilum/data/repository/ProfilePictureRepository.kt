package com.example.vigilum.data.repository

import android.content.Context
import android.net.Uri
import androidx.camera.view.LifecycleCameraController
import com.example.vigilum.data.datasource.camera.PostDocumentPic_RectoDataSource
import com.example.vigilum.data.datasource.camera.ProfilePictureDataSource
import com.example.vigilum.data.remote.response.EmptyResponseDto
import javax.inject.Inject

class ProfilePictureRepository @Inject constructor(val profilePictureDataSource: ProfilePictureDataSource) {
    val imageUri = profilePictureDataSource.imageUri
    var base64Img = profilePictureDataSource.base64Img

    suspend fun takePhoto(controller: LifecycleCameraController, context: Context) {
        profilePictureDataSource.takeSelfie(controller, context)
    }

    suspend fun submitSelfie(
        context: Context,
        imageUri: Uri
    ): EmptyResponseDto {
        val response = profilePictureDataSource.submitSelfie(context, imageUri)
        return response
    }

    }