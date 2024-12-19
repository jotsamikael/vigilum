package com.example.vigilum.data.repository

import android.content.Context
import android.net.Uri
import androidx.camera.view.LifecycleCameraController
import com.example.vigilum.data.datasource.camera.PostDocumentPic_RectoDataSource
import com.example.vigilum.data.remote.response.EmptyResponseDto
import javax.inject.Inject

class PostDocumentPic_RectoRepository @Inject constructor(val postDocumentPicRectoDataSource: PostDocumentPic_RectoDataSource) {
    val imageUri = postDocumentPicRectoDataSource.imageUri
    var base64Img = postDocumentPicRectoDataSource.base64Img

    suspend fun takePhoto(controller: LifecycleCameraController, context: Context) {
        postDocumentPicRectoDataSource.takePhoto(controller, context)
    }

    suspend fun submitDocument(
        context: Context,
        imageUri: Uri,
        documentType: String
    ):EmptyResponseDto {
        val response = postDocumentPicRectoDataSource.submitDocument(context, imageUri, documentType)
        return response
    }

}
