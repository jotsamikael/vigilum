package com.example.vigilum.domain.model

data class RemoteConfig(
    val serverIp: String,
    val merchantCode: String,
    val securityKey: String,
    val deviceSerialNumber: String,
)