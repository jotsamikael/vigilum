import com.example.vigilum.ui.remoteconfiguration.use_case.ValidationResult

class ValidateSecurityKey {
    fun execute(securityKey: String): ValidationResult {
        if(securityKey.isBlank()){
            return ValidationResult(
                false,
                "securityKey number can not be blank"
            )
        }
        if(securityKey.length != 16){
            return ValidationResult(
                false,
                "wrong securityKey"
            )
        }
        return ValidationResult(
            true
        )
    }
}