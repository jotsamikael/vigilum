package com.example.vigilum.data.remote.request

data class RemoteConfigRequest(
    val serverIp: String,
    val merchantCode: String,
    val securityKey: String,
    val deviceSerialNumber: String,
)