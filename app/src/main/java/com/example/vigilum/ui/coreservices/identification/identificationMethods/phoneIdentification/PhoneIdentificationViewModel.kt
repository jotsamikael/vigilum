package com.example.vigilum.ui.coreservices.identification.identificationMethods.phoneIdentification

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PhoneIdentificationViewModel @Inject constructor() :
    ViewModel() {

    private val _noVisitorAccountFound = MutableStateFlow(false)
    val noVisitorAccountFound = _noVisitorAccountFound.asStateFlow()

    /*
    suspend fun identifyVisitor(phoneNumber: String): VisitorProfileResponse {
        val identificationRequest =
            IdentificationRequest("PhoneNumber", phoneNumber)
        val response = visitorIdentificationRepository.identifyVisitor(identificationRequest)
        if (response.status != "200") {
            _noVisitorAccountFound.value = true

        }
        Log.i(TAG, response.status)
        Log.i(TAG, response.data.profilePhoto)
        return response
    }
    */
    companion object {
        const val TAG = "POST_IDENTIFICATION_REQUEST"
    }
}