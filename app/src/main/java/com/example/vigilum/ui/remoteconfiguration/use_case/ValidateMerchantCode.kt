
import com.example.vigilum.ui.remoteconfiguration.use_case.ValidationResult

class ValidateMerchantCode {
    fun execute(merchantCode: String): ValidationResult {
        if(merchantCode.isBlank()){
            return ValidationResult(
                false,
                "Can not be blank"
            )
        }
        if(merchantCode.length != 9){
            return ValidationResult(
                false,
                "wrong value"
            )
        }

        return ValidationResult(
            true
        )
    }
}