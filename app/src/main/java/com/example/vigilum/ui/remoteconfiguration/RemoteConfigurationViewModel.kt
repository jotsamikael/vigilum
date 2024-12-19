package com.example.vigilum.ui.remoteconfiguration

import RemoteConfigurationFormEvent
import RemoteConfigurationFormState
import ValidateMerchantCode
import ValidateSecurityKey
import ValidateServerIp
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RemoteConfigurationViewModel @Inject constructor() : ViewModel() {

    private val _showSaveBtn = MutableStateFlow(false)
    val showSaveBtn = _showSaveBtn.asStateFlow()

    private val _successfulSubmit = MutableStateFlow(false)
    val successfulSubmit = _successfulSubmit.asStateFlow()

    var state by mutableStateOf(RemoteConfigurationFormState())

    sealed class ValidationEvent {
        object Success : ValidationEvent()
    }

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvent = validationEventChannel.receiveAsFlow()

    fun onEvent(event: RemoteConfigurationFormEvent) {
        when (event) {
            is RemoteConfigurationFormEvent.ServerIpChanged -> {
                state = state.copy(serverIp = event.serverIp)
            }

            is RemoteConfigurationFormEvent.MerchantCodeChanged -> {
                state = state.copy(merchantCode = event.merchantCode)
            }

            is RemoteConfigurationFormEvent.SercurityKeyChanged -> {
                state = state.copy(securityKey = event.securityKey)
            }

            is RemoteConfigurationFormEvent.Submit -> {
                //sumit data
                submitData()
            }

            else -> {}
        }
    }

    private fun submitData() {
        Log.i("SUBMIT DATA CALLED","submit")
        val serverIpResult = ValidateServerIp().execute(state.serverIp)
        val merchantCodeResult = ValidateMerchantCode().execute(state.merchantCode)
        val securityKeyResult = ValidateSecurityKey().execute(state.securityKey)
        val hasError = listOf(
            serverIpResult,
            merchantCodeResult,
            securityKeyResult
        ).any {
            !it!!.successful
        }
        if (hasError) {
            state = state.copy(
                serverIpError =  serverIpResult.errorMessage,
                merchantCodeError = merchantCodeResult.errorMessage,
                securityKeyError = securityKeyResult.errorMessage,

            )
        } else {
            viewModelScope.launch {

                //send remote config params to processing remote config screen and navigate to it
                _successfulSubmit.value = true
                /*val visitor = VisitorDetail(
                    firstname = state.firstName,
                    lastname = state.lastName,
                    sex = selectedGender.value,
                    phone = state.telephone,
                    documentIdNumber = state.nationalIDNumber,
                    enterprise = state.enterprise,

                    )
                //send config dto to server
                postPersonalDetails(visitor)*/
            }

        }
    }

}