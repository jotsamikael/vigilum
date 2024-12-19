package com.example.vigilum.data.datasource.camera

import android.app.Application
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.core.content.ContextCompat
import com.example.vigilum.R
import com.example.vigilum.data.remote.VigilumApi
import com.example.vigilum.data.remote.response.DocumentPicture
import com.example.vigilum.data.remote.response.EmptyResponseDto
import com.example.vigilum.data.remote.response.ProfilePictureData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import javax.inject.Inject

class ProfilePictureDataSourceImpl @Inject constructor(
    private val application: Application,
    private val qouikApi: VigilumApi
) : ProfilePictureDataSource {


    private val _imageUri = MutableStateFlow<Uri?>(null)
    override val imageUri: StateFlow<Uri?> = _imageUri

    private val _base64Img = MutableSharedFlow<String?>(replay = 0)
    override var base64Img: SharedFlow<String?> = _base64Img

    @RequiresApi(Build.VERSION_CODES.Q)
    override suspend fun takeSelfie(
        controller: LifecycleCameraController,
        context: Context
    ) {

        controller.takePicture(
            ContextCompat.getMainExecutor(application),
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    super.onCaptureSuccess(image)

                    val matrix = Matrix().apply {
                        postRotate(
                            image.imageInfo.rotationDegrees.toFloat()
                        )
                    }
                    val imageBitmap = Bitmap.createBitmap(
                        image.toBitmap(),
                        0, 0,
                        image.width, image.height,
                        matrix, true
                    )
                    Log.i("STARTPHO","take photo")
                    CoroutineScope(Dispatchers.IO).launch {
                        _imageUri.value = savePhoto(imageBitmap)

                        Log.i("URIIS","${_imageUri.value}")

                        // Emit the Base64 string once it's ready
                        _base64Img.emit(convertImageToBase64(context = context, _imageUri.value!!))
                        //Log.i("BASE64IMG","${base64Im}")

                    }

                }
            }
        )
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private suspend fun savePhoto(bitmap: Bitmap): Uri? {
        return withContext(Dispatchers.IO) {
            val resolver: ContentResolver = application.contentResolver

            val imageCollection = MediaStore.Images.Media.getContentUri(
                MediaStore.VOLUME_EXTERNAL_PRIMARY
            )

            val appName = application.getString(R.string.app_name)
            val timeInMillis = System.currentTimeMillis()

            val imageContentValues: ContentValues = ContentValues().apply {
                put(
                    MediaStore.Images.Media.DISPLAY_NAME,
                    "${timeInMillis}_image" + ".jpg"
                )
                put(
                    MediaStore.MediaColumns.RELATIVE_PATH,
                    Environment.DIRECTORY_DCIM + "/$appName"
                )
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
                put(MediaStore.MediaColumns.DATE_TAKEN, timeInMillis)
                put(MediaStore.MediaColumns.IS_PENDING, 1)
            }

            val imageMediaStoreUri: Uri? = resolver.insert(
                imageCollection, imageContentValues
            )

            imageMediaStoreUri?.let { uri ->
                try {
                    resolver.openOutputStream(uri)?.let { outputStream ->
                        bitmap.compress(
                            Bitmap.CompressFormat.JPEG, 100, outputStream
                        )
                    }

                    imageContentValues.clear()
                    imageContentValues.put(
                        MediaStore.MediaColumns.IS_PENDING, 0
                    )
                    resolver.update(
                        uri, imageContentValues, null, null
                    )

                    return@withContext uri

                } catch (e: Exception) {
                    e.printStackTrace()
                    resolver.delete(uri, null, null)
                }
            }

            return@withContext null
        }
    }

    override suspend fun submitSelfie(context: Context, imageUri: Uri): EmptyResponseDto {
        val base64Image = convertImageToBase64(context, imageUri)

        if (base64Image != null) {
            Log.i("BASE64","$base64Image")

            val profilePictureData = ProfilePictureData(  base64Image)
            try {
                Log.i("BASE64","BEFORE UPLOAD")

                val response = qouikApi.postProfilePicture(profilePictureData)
                return response

            } catch (e: Exception) {
                println("Failed to post document: ${e.message}")
            }

        } else {
            println("Failed to convert image to Base64 string")
        }
        val emptyResponse = EmptyResponseDto("400","An error occured")
        return emptyResponse
    }

    fun convertImageToBase64(context: Context, imageUri: Uri): String? {
        context.contentResolver.openInputStream(imageUri)?.buffered()?.use { inputStream ->
            val bitmap = BitmapFactory.decodeStream(inputStream)
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            return Base64.encodeToString(byteArray, Base64.DEFAULT)
        }
        return null
    }



    @RequiresApi(Build.VERSION_CODES.Q)
    private suspend fun saveVideo(file: File) {
        withContext(Dispatchers.IO) {
            val resolver: ContentResolver = application.contentResolver

            val videoCollection = MediaStore.Video.Media.getContentUri(
                MediaStore.VOLUME_EXTERNAL_PRIMARY
            )

            val appName = application.getString(R.string.app_name)
            val timeInMillis = System.currentTimeMillis()

            val videoContentValues: ContentValues = ContentValues().apply {
                put(
                    MediaStore.Video.Media.DISPLAY_NAME,
                    file.name
                )
                put(
                    MediaStore.MediaColumns.RELATIVE_PATH,
                    Environment.DIRECTORY_DCIM + "/$appName"
                )
                put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
                put(MediaStore.MediaColumns.DATE_ADDED, timeInMillis / 1000)
                put(MediaStore.MediaColumns.DATE_MODIFIED, timeInMillis / 1000)
                put(MediaStore.MediaColumns.DATE_TAKEN, timeInMillis)
                put(MediaStore.Video.Media.IS_PENDING, 1)
            }

            val videoMediaStoreUri: Uri? = resolver.insert(
                videoCollection, videoContentValues
            )

            videoMediaStoreUri?.let { uri ->
                try {
                    resolver.openOutputStream(uri)?.use { outputStream ->
                        resolver.openInputStream(
                            Uri.fromFile(file)
                        )?.use { inputStream ->
                            inputStream.copyTo(outputStream)
                        }
                    }

                    videoContentValues.clear()
                    videoContentValues.put(MediaStore.MediaColumns.IS_PENDING, 0)
                    resolver.update(
                        uri, videoContentValues, null, null
                    )

                } catch (e: Exception) {
                    e.printStackTrace()
                    resolver.delete(uri, null, null)
                }
            }
        }
    }
}