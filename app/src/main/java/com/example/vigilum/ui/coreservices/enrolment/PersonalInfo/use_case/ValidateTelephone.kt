package com.example.vigilum.ui.coreservices.enrolment.PersonalInfo.use_case

import com.example.vigilum.ui.remoteconfiguration.use_case.ValidationResult


class ValidateTelephone {
    fun execute(telephone: String): ValidationResult {
        if(telephone.isBlank()){
            return ValidationResult(
                false,
                "Telephone number can not be blank"
            )
        }
        if(telephone.length < 9){
            return ValidationResult(
                false,
                "Telephone number too short"
            )
        }
        if(telephone.length > 10){
            return ValidationResult(
                false,
                "Telephone number too long"
            )
        }
        return ValidationResult(
            true
        )
    }
}