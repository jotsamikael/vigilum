

sealed class RemoteConfigurationFormEvent {
    data class ServerIpChanged(val serverIp: String): RemoteConfigurationFormEvent()
    data class MerchantCodeChanged(val merchantCode: String): RemoteConfigurationFormEvent()
    data class SercurityKeyChanged(val securityKey: String): RemoteConfigurationFormEvent()

    object Submit: RemoteConfigurationFormEvent()
}