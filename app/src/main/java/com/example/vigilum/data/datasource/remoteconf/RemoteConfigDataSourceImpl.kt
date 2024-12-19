package com.example.vigilum.data.datasource.remoteconf

import com.example.vigilum.data.local.remoteConfig.RemoteConfigDatabase
import com.example.vigilum.data.local.remoteConfig.RemoteConfigEntity
import com.example.vigilum.data.remote.RemoteConfigApi
import com.example.vigilum.data.remote.request.RemoteConfigRequest
import com.example.vigilum.data.remote.response.EmptyResponseDto
import retrofit2.Response
import javax.inject.Inject

class RemoteConfigDataSourceImpl @Inject constructor(
    private val remoteConfigApi: RemoteConfigApi,
    private val remoteConfigDatabase: RemoteConfigDatabase
): RemoteConfigDataSource {
    override suspend fun postRemoteConfigDataSource(remoteConfigRequest: RemoteConfigRequest): Response<EmptyResponseDto> {
      return remoteConfigApi.submitRemoteConfig(remoteConfigRequest)
    }

    override suspend fun insertRemoteConfigToDB(remoteConfigEntity: RemoteConfigEntity) {
        remoteConfigDatabase.remoteConfigDao.upsertRemoteConfig(remoteConfigEntity)
    }

    override suspend fun getRemoteConfigFromDB():RemoteConfigEntity {
        //get remote config by id, by default is 1
        return remoteConfigDatabase.remoteConfigDao.getRemoteConfigById(1)
    }
}