package com.example.vigilum.ui.coreservices.enrolment.PersonalInfo

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vigilum.ui.coreservices.enrolment.PersonalInfo.use_case.ValidateEnterprise
import com.example.vigilum.ui.coreservices.enrolment.PersonalInfo.use_case.ValidateNID
import com.example.vigilum.ui.coreservices.enrolment.PersonalInfo.use_case.ValidateName
import com.example.vigilum.ui.coreservices.enrolment.PersonalInfo.use_case.ValidateTelephone
import com.example.vigilum.data.remote.request.PersonalInfoRequest
import com.example.vigilum.data.repository.PersonalDetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonalInfoViewModel @Inject constructor(private val personalDetailsRepository: PersonalDetailsRepository): ViewModel(){

    //sex
    private val _selectedGender = MutableStateFlow("Male")
    val selectedGender: StateFlow<String> = _selectedGender

    fun setSelectedGender(gender: String) {
        _selectedGender.value = gender
    }

    private val _submissionStatus = MutableStateFlow(false)
    val submissionStatus: StateFlow<Boolean> get() = _submissionStatus


    sealed class ValidationEvent {
        object Success : ValidationEvent()
    }

    var state by mutableStateOf(PersonalInfoFormState())
    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvent = validationEventChannel.receiveAsFlow()

    // Add a method to initialize the form state with given values
    fun initializeWithInitialValues(firstName: String, lastName: String, sex: String, phone: String, documentIdNumber: String, enterprise: String) {
        state = PersonalInfoFormState(
            firstName = firstName,
            lastName = lastName,
            telephone = phone,
            nationalIDNumber = documentIdNumber,
            enterprise = enterprise
        )
    }


    fun onEvent(event: PersonalInfoFormEvent) {
        when (event) {
            is PersonalInfoFormEvent.FirstnameChanged -> {
                state = state.copy(firstName = event.firstName)
            }
            is PersonalInfoFormEvent.LastnameChanged -> {
                state = state.copy(lastName = event.lastName)
            }
            is PersonalInfoFormEvent.EnterpriseChanged -> {
                state = state.copy(enterprise = event.enterprise)
            }

            is PersonalInfoFormEvent.TelephoneChanged -> {
                state = state.copy(telephone = event.telephone)

            }

            is PersonalInfoFormEvent.NationalIDNumberChanged -> {
                state = state.copy(nationalIDNumber = event.nationalIDNumber)

            }

            is PersonalInfoFormEvent.Submit -> {
                //sumit data
                submitData()
            }
        }
    }

    private fun submitData() {
        val firstnameResult = ValidateName().execute(state.firstName)
        val lastnameResult = ValidateName().execute(state.lastName)
        val telephoneResult = ValidateTelephone().execute(state.telephone)
        val nationalIdNumberResult = ValidateNID().execute(state.nationalIDNumber)
        val enterpriseResult = ValidateEnterprise().execute(state.enterprise)
        val hasError = listOf(
            firstnameResult,
            lastnameResult,
            telephoneResult,
            nationalIdNumberResult,
            enterpriseResult
        ).any {
            !it!!.successful
        }
        if (hasError) {
            state = state.copy(
                firstNameError = firstnameResult.errorMessage,
                lastNameError = lastnameResult.errorMessage,
                telephoneError = telephoneResult.errorMessage,
                nationalIDNumberError = nationalIdNumberResult.errorMessage,
                enterpriseError = enterpriseResult.errorMessage
            )
        } else {
            Log.i(TAG, "No error")

            viewModelScope.launch {
                Log.i(TAG, "Enter corountine")

                //validationEventChannel.send(ValidationEvent.Success)
                val personalInfo = PersonalInfoRequest(
                    firstname = state.firstName,
                    lastname = state.lastName,
                    sex = selectedGender.value,
                    phone = state.telephone,
                    documentIdNumber = state.nationalIDNumber,
                    enterprise = state.enterprise,
                    )
                Log.i(TAG, "phone : ${personalInfo.phone}")

                var response =  personalDetailsRepository.postPersonalInfo(personalInfo)
                if (response.status == "200"){
                    _submissionStatus.value = true

                }
            }

        }

    }

    companion object {
        const val TAG = "POST_PERSONAL"
    }
}