import com.example.vigilum.ui.remoteconfiguration.use_case.ValidationResult


class ValidateServerIp {
    fun execute(serverIp: String): ValidationResult {
        if(serverIp.isBlank()){
            return ValidationResult(
                false,
                "ServerIp can not be blank, if you don' have it, contact admin"
            )
        }
        if(serverIp.length < 11 || serverIp.length > 15){
            return ValidationResult(
                false,
                "Wrong ServerIp"
            )
        }

        return ValidationResult(
            true
        )
    }
}