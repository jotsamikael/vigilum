package com.example.vigilum.ui.coreservices.enrolment.PersonalInfo.use_case

import com.example.vigilum.ui.remoteconfiguration.use_case.ValidationResult


class ValidateNID {
    fun execute(nid: String): ValidationResult {
        if(nid.isBlank()){
            return ValidationResult(
                false,
                "document number can not be blank"
            )
        }
        if(nid.length < 9){
            return ValidationResult(
                false,
                "document number too short"
            )
        }
        if(nid.length >10){
            return ValidationResult(
                false,
                "document number too long"
            )
        }
        return ValidationResult(
            true
        )
    }
}