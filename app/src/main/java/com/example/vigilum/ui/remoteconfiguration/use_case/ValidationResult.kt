package com.example.vigilum.ui.remoteconfiguration.use_case

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null,

)
