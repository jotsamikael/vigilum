package com.example.vigilum.data.repository

import android.content.Context
import android.net.Uri
import androidx.camera.view.LifecycleCameraController
import com.example.vigilum.data.datasource.camera.PostDocumentPic_VersoDataSource
import com.example.vigilum.data.remote.response.EmptyResponseDto
import javax.inject.Inject

class PostDocumentPic_VersoRepository @Inject constructor(val postDocumentPicVersoDataSource: PostDocumentPic_VersoDataSource) {
    val imageUri = postDocumentPicVersoDataSource.imageUri
    var base64Img = postDocumentPicVersoDataSource.base64Img

    suspend fun takePhoto(controller: LifecycleCameraController, context: Context) {
        postDocumentPicVersoDataSource.takePhoto(controller, context)
    }

    suspend fun submitDocument(
        context: Context,
        imageUri: Uri,
        documentType: String
    ): EmptyResponseDto {
        val response =
            postDocumentPicVersoDataSource.submitDocument(context, imageUri, documentType)
        return response
    }

}