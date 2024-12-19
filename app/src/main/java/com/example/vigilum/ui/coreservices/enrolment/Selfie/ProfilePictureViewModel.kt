package com.example.adsnetkyc.presentation.qouikscreens.enrollSteps.Selfie

import android.content.Context
import android.graphics.PointF
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.OptIn
import androidx.annotation.RequiresApi
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vigilum.data.repository.ProfilePictureRepository
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceContour
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceDetectorOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfilePictureViewModel @Inject constructor(
    private val cameraRepo: ProfilePictureRepository
) : ViewModel() {

    val imageUri: StateFlow<Uri?> = cameraRepo.imageUri
    private val _isPictureTaken = MutableStateFlow(false)
    val isPictureTaken = _isPictureTaken.asStateFlow()

    //Mutable state flow to track face detection
    private val _isFaceDetected = MutableStateFlow(false)
    val isFaceDetected = _isFaceDetected.asStateFlow()


    private val _submittedSuccessfully = MutableStateFlow(false)
    val submittedSuccessfully = _submittedSuccessfully.asStateFlow()

    private val _processing = MutableStateFlow(false)
    val processing = _processing.asStateFlow()

    private val faceDetector: FaceDetector

    private val _faceContours = MutableStateFlow<List<List<PointF>>>(emptyList())
    val faceContours: StateFlow<List<List<PointF>>> = _faceContours



    fun observeImageUri() {
        viewModelScope.launch {
            imageUri
                .filterNotNull()
                .collect { uri ->
                    // Use the uri
                    Log.i("IMAGE_URI", "$uri")
                    _isPictureTaken.value = true
                }
        }
    }


    init {
        observeImageUri()
        // Initialize the ML Kit Face Detector with high-accuracy options
        val highAccuracyOpts = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
            .build()

        faceDetector = FaceDetection.getClient(highAccuracyOpts)
    }

    fun onCancel() {
        _isPictureTaken.value = false
    }
    private var lastFaceDetectionTime = System.currentTimeMillis()



    @RequiresApi(Build.VERSION_CODES.Q)
    fun onTakePhoto(
        controller: LifecycleCameraController,
        context: Context
    ) {
        viewModelScope.launch {
            cameraRepo.takePhoto(controller, context)

        }

    }

    fun submitDocument(context: Context, imageUri: Uri) {
        _processing.value = true

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = cameraRepo.submitSelfie(context, imageUri)
                if (response.status=="200"){
                    Log.i("GOOD","response")
                    _submittedSuccessfully.value = true
                    _processing.value = false

                }else{
                    Log.i("BAD","no response")
                    _processing.value = false

                }

            } catch (e: Exception) {
                // Handle any exceptions here
                Log.i("EXCEPTION OCCURED","Exception occured")
                _processing.value = false
            }
        }
    }

    @OptIn(ExperimentalGetImage::class)
    fun detectFaces(imageProxy: ImageProxy) {
        val rotationDegrees = imageProxy.imageInfo.rotationDegrees
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val inputImage = InputImage.fromMediaImage(mediaImage, rotationDegrees)

            // Start face detection
            faceDetector.process(inputImage)
                .addOnSuccessListener { faces ->
                    // Handle successful face detection
                    _isFaceDetected.value = faces.isNotEmpty()
                    Log.i("ViewModel", "Faces detected: ${faces.size}")
                    val contours = faces.map { face ->
                        face.getContour(FaceContour.FACE)?.points ?: emptyList()
                    }
                    Log.i("Contours", "Contours: ${contours}")

                    _faceContours.value = contours
                }
                .addOnFailureListener { exception ->
                    // Handle failure in face detection
                    _isFaceDetected.value = false
                    Log.e("ViewModel", "Face detection failed", exception)
                }
                .addOnCompleteListener {
                    // Always close the ImageProxy when processing is complete
                    imageProxy.close()
                }
        } else {
            // Close ImageProxy if mediaImage is null
            imageProxy.close()
        }
    }

    override fun onCleared() {
        super.onCleared()
        faceDetector.close() // Release the detector resources
    }

}