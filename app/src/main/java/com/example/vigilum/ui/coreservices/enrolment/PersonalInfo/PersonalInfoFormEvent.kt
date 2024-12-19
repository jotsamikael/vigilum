package com.example.vigilum.ui.coreservices.enrolment.PersonalInfo


sealed class PersonalInfoFormEvent {
    data class FirstnameChanged(val firstName: String): PersonalInfoFormEvent()
    data class LastnameChanged(val lastName: String): PersonalInfoFormEvent()
    data class TelephoneChanged(val telephone: String): PersonalInfoFormEvent()
    data class EnterpriseChanged(val enterprise: String): PersonalInfoFormEvent()

    data class NationalIDNumberChanged(val nationalIDNumber: String): PersonalInfoFormEvent()
    object Submit: PersonalInfoFormEvent()
}