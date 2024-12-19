package com.example.vigilum.ui.coreservices.enrolment.Signature

import android.graphics.Bitmap
import android.util.Base64
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vigilum.data.remote.response.SignatureData
import com.example.vigilum.data.repository.SignatureDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@HiltViewModel
class SignatureViewModel @Inject constructor(private val signatureDataRepository: SignatureDataRepository): ViewModel(){

    var isConfirmDialogShown by mutableStateOf(false)
        private set
    fun onSubmitClick(){
        isConfirmDialogShown = true
    }
    fun onDismissClick(){
        isConfirmDialogShown = false

    }

    fun convertBitmapToBase64(bitmap: Bitmap): String {
        ByteArrayOutputStream().apply {

            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            return Base64.encodeToString(byteArray, Base64.DEFAULT)

        }
    }

    fun postSignatureData(base64String:String) {
        Log.i("postreq", "send")

        val signatureData = SignatureData(
            "1",
            base64String

        )
        //making sure our fun runs on a background thread i.e IO thread
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.i(TAG, "BEFORE POST")
                signatureDataRepository.postSignature(signatureData)
                Log.i(TAG, "AFTER POST")

            } catch (e: Exception) {
                Log.i(TAG, "Exception occurred: " + e)

            }
        }
    }




    companion object {
        const val TAG = "POST_SIGNATURE"
    }
}