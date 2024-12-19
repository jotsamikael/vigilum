package com.example.vigilum.ui.coreservices.enrolment.idDocImage.verso

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.camera.view.LifecycleCameraController
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vigilum.data.repository.PostDocumentPic_VersoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class IdDocImageScanVersoViewModel @Inject constructor(
    private val cameraRepo: PostDocumentPic_VersoRepository
) : ViewModel() {

    val imageUri: StateFlow<Uri?> = cameraRepo.imageUri
    private val _isPictureTaken = MutableStateFlow(false)
    val isPictureTaken = _isPictureTaken.asStateFlow()

    private val _submittedSuccessfully = MutableStateFlow(false)
    val submittedSuccessfully = _submittedSuccessfully.asStateFlow()

    private val _processing = MutableStateFlow(false)
    val processing = _processing.asStateFlow()

    fun observeImageUri() {
        viewModelScope.launch {
            imageUri
                .filterNotNull()
                .collect { uri ->
                    // Use the uri
                    //Log.i("IMAGE_URI", "$uri")
                    _isPictureTaken.value = true
                }
        }
    }


    init {
        observeImageUri()
    }

    fun onCancel() {
        _isPictureTaken.value = false
    }


    fun onTakePhoto(
        controller: LifecycleCameraController,
        context: Context
    ) {
        viewModelScope.launch {
            cameraRepo.takePhoto(controller, context)

        }

    }

    fun submitDocument(context: Context, imageUri: Uri, documentType: String) {
        _processing.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = cameraRepo.submitDocument(context, imageUri, documentType)
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



}