package com.example.vigilum.data.datasource.remoteconf

import com.example.vigilum.data.local.remoteConfig.RemoteConfigEntity
import com.example.vigilum.data.remote.request.RemoteConfigRequest
import com.example.vigilum.data.remote.response.EmptyResponseDto
import retrofit2.Response

interface RemoteConfigDataSource {

    suspend fun postRemoteConfigDataSource(remoteConfigRequest: RemoteConfigRequest): Response<EmptyResponseDto>
    suspend fun insertRemoteConfigToDB(remoteConfigEntity: RemoteConfigEntity)
    suspend fun getRemoteConfigFromDB(): RemoteConfigEntity
}