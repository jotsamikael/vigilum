
data class RemoteConfigurationFormState(
    val serverIp:String = "",
    val serverIpError:String? = null,
    val merchantCode:String = "",
    val merchantCodeError:String? = null,
    val securityKey:String = "",
    val securityKeyError:String? = null
)