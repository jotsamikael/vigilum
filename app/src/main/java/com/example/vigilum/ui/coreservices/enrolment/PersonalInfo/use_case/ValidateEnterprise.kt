package com.example.vigilum.ui.coreservices.enrolment.PersonalInfo.use_case

import com.example.vigilum.ui.remoteconfiguration.use_case.ValidationResult


class ValidateEnterprise {
    fun execute(enterprise: String): ValidationResult {
        if(enterprise.isBlank()){
            return ValidationResult(
                false,
                "Enterprise name cannot be blank, if you don4t belong to an enterrise write NONE"
            )
        }
        if(enterprise.length < 2){
            return ValidationResult(
                false,
                "Enterprise name  too short"
            )
        }
        if(enterprise.length >50){
            return ValidationResult(
                false,
                "Enterprise name too long"
            )
        }
        return ValidationResult(
            true
        )
    }
}