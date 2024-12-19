package com.example.vigilum.data.repository

import com.example.vigilum.data.datasource.remoteconf.RemoteConfigDataSource
import com.example.vigilum.data.local.remoteConfig.RemoteConfigEntity
import com.example.vigilum.data.remote.request.RemoteConfigRequest
import com.example.vigilum.data.remote.response.EmptyResponseDto
import com.example.utilities.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * @author JOTSA MIKAEL
 * This class provides api and database functions
 * to viewModels
 */
class RemoteConfigurationRepository @Inject
constructor(private val remoteConfigDataSource: RemoteConfigDataSource) {

    suspend fun postRemoteConfig(remoteConfigRequest: RemoteConfigRequest): Flow<ResourceState<EmptyResponseDto>>{
        return flow{
            emit(ResourceState.Loading())
            val response = remoteConfigDataSource.postRemoteConfigDataSource(remoteConfigRequest)

            if(response.isSuccessful && response.body() != null){
                emit(ResourceState.Success(response.body()!!))

            } else{
                emit(ResourceState.Error("Error connecting to server"))
            }
        }.catch { e->
            emit(ResourceState.Error(e?.localizedMessage?:"Some error in flow"))
        }
    }

    suspend fun insertRemoteConfigToDB(remoteConfigEntity: RemoteConfigEntity){
        remoteConfigDataSource.insertRemoteConfigToDB(remoteConfigEntity)
    }

    suspend fun getRemoteConfigFromDB() : RemoteConfigEntity{
      return  remoteConfigDataSource.getRemoteConfigFromDB()
    }

}