package com.example.vigilum.ui.coreservices.enrolment.PersonalInfo.use_case

import android.util.Patterns
import com.example.vigilum.ui.remoteconfiguration.use_case.ValidationResult

class ValidateName {
    fun execute(firstName: String): ValidationResult {
        if(firstName.isBlank()){
            return ValidationResult(
                false,
                "Can not be blank"
            )
        }
        if(firstName.length < 2){
            return ValidationResult(
                false,
                "Too short"
            )
        }
        if(firstName.length > 50){
            return ValidationResult(
                false,
                "Too long"
            )
        }
        if(Patterns.PHONE.matcher(firstName).matches() || Patterns.EMAIL_ADDRESS.matcher(firstName).matches() ){
            return ValidationResult(
                false,
                "Enter a valid name"
            )
        }
        return ValidationResult(
            true
        )
    }
}