package com.example.vigilum.data.local.remoteConfig

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author JOTSA MIKAEL
 */
@Entity
data class RemoteConfigEntity(
    @PrimaryKey
    val id: Int?,

    val serverIp: String,
    val merchantCode: String,
    val securityKey: String,
    val deviceSerialNumber: String,
)