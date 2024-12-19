package com.example.vigilum.data.remote

import com.example.vigilum.data.remote.request.RemoteConfigRequest
import com.example.vigilum.data.remote.response.EmptyResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @author JOTSA MIKAEL
 */
interface RemoteConfigApi {

    @POST("/submit-remote-config")
    suspend fun submitRemoteConfig(@Body remoteConfigRequest: RemoteConfigRequest): Response<EmptyResponseDto>

}