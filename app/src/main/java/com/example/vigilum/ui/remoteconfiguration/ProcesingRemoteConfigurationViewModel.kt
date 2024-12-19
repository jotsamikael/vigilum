package com.example.vigilum.ui.remoteconfiguration

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vigilum.data.local.remoteConfig.RemoteConfigEntity
import com.example.vigilum.data.remote.request.RemoteConfigRequest
import com.example.vigilum.data.remote.response.EmptyResponseDto
import com.example.vigilum.data.repository.RemoteConfigurationRepository
import com.example.utilities.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProcesingRemoteConfigurationViewModel @Inject constructor(
    private val remoteConfigurationRepository: RemoteConfigurationRepository
) : ViewModel() {

    private val _response: MutableStateFlow<ResourceState<EmptyResponseDto>> =
        MutableStateFlow(ResourceState.Loading())
    val response: StateFlow<ResourceState<EmptyResponseDto>> = _response

    private val _dbInsertionComplete = MutableStateFlow(false)
    val dbInsertionComplete: StateFlow<Boolean> = _dbInsertionComplete.asStateFlow()


    suspend fun submitRemoteConfigParams(
        serverIp: String,
        merchantCode: String,
        securityKey: String,

        ) {
        Log.i(API_TAG, "submitRemoteConfigParams called")
        var remoteConfigRequest =
            RemoteConfigRequest(serverIp, merchantCode, securityKey, "SER1111111")
        viewModelScope.launch(Dispatchers.IO) {
            Log.i(API_TAG, "entered viewModel scope")

            remoteConfigurationRepository.postRemoteConfig(remoteConfigRequest)
                .collectLatest { response ->
                    _response.value = response
                    Log.i(API_TAG, " response is ${_response.value}")

                }
        }

    }

    suspend fun insertRemoteConfigToDB(
        serverIp: String,
        merchantCode: String,
        securityKey: String,
    ) {
        val remoteConfigEntity =
            RemoteConfigEntity(1, serverIp, merchantCode, securityKey, "SER1111111")
        remoteConfigurationRepository.insertRemoteConfigToDB(remoteConfigEntity)
        Log.i(DB_TAG, "DONE")
        _dbInsertionComplete.value = true // Notify completion

    }

    companion object {
        const val API_TAG = "API_SUBMITTED"
        const val DB_TAG = "DB_SUBMITTED"
    }
}