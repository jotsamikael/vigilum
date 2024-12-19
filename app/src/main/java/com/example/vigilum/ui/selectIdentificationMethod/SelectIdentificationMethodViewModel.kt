package com.example.adsnetkyc.presentation.qouikscreens.selectIdentificationMethod

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.utilities.ResourceState
import com.example.vigilum.data.remote.response.GDZServiceResponse
import com.example.vigilum.data.repository.VigilumRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class SelectIdentificationMethodViewModel @Inject constructor(private val vigilumRepository: VigilumRepository): ViewModel() {
    private val _identificationMethods: MutableStateFlow<ResourceState<GDZServiceResponse>> = MutableStateFlow(
        ResourceState.Loading()
    )
    val identificationMethods: StateFlow<ResourceState<GDZServiceResponse>> =
        _identificationMethods //can not be changed from u.i

    //calling getReceipts when ever the view model is entered
    init {
        getIdentificationMethods()

    }


    /* In this function we open a view model scope,
    *on the background IO thread
    * we call the method from retrofit
    * we collect the latest response (receiptResponse)
    * we assign it to _receipt
    * and once _receipt changes, it is automaticcally assigned to receipt*/
    fun getIdentificationMethods() {
        //making sure our fun runs on a background thread i.e IO thread
        viewModelScope.launch(Dispatchers.IO) {
            try {
                vigilumRepository.getGDZServices()
                    .collectLatest{ response->
                        _identificationMethods.value = response
                    }
            } catch (e: SocketTimeoutException) {
                Log.i(TAG,"SocketTimeoutException $e")
                _identificationMethods.value = ResourceState.Error("Server was reachable but did not respond in time")
            }catch (e: ConnectException) {
                // Handling ConnectException specifically
                Log.i(TAG,"ConnectException $e")
                _identificationMethods.value = ResourceState.Error("Failed to connect to server")
            } catch (e: Exception) {
                // Catching any other exceptions
                Log.i(TAG,"unexpected $e")
                _identificationMethods.value = ResourceState.Error("An unexpected error occurred")
            }
        }
    }

    companion object {
        const val TAG = "SelectIdentificationMethodViewModel"
    }
}