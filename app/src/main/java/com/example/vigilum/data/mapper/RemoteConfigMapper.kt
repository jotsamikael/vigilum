package com.example.vigilum.data.mapper

import com.example.vigilum.data.local.remoteConfig.RemoteConfigEntity
import com.example.vigilum.domain.model.RemoteConfig

fun RemoteConfigEntity.toRemoteConfig(): RemoteConfig {
 return RemoteConfig(
  serverIp = serverIp,
  merchantCode = merchantCode,
  securityKey = securityKey,
  deviceSerialNumber = deviceSerialNumber
 )
}