package com.example.vigilum.ui.coreservices.enrolment.PersonalInfo

data class PersonalInfoFormState(
    val firstName:String = "",
    val firstNameError:String? = null,
    val lastName:String = "",
    val lastNameError:String? = null,
    val enterprise:String = "",
    val enterpriseError:String? = null,
    val telephone:String = "",
    val telephoneError:String? = null,
    val nationalIDNumber:String = "",
    val nationalIDNumberError:String? = null,
)