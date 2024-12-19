package com.example.vigilum.ui.coreservices.enrolment.PersonalInfo.use_case

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null,

)
