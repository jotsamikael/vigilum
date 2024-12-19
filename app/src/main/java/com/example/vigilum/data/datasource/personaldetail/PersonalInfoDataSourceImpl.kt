package com.example.vigilum.data.datasource.personaldetail

import com.example.vigilum.data.remote.VigilumApi
import com.example.vigilum.data.remote.request.PersonalInfoRequest
import com.example.vigilum.data.remote.response.EmptyResponseDto
import javax.inject.Inject

class PersonalInfoDataSourceImpl @Inject constructor(private val vigilumApi: VigilumApi): PersonalInfoDataSource {

    override suspend fun postPersonalInfo(personalInfoReq: PersonalInfoRequest): EmptyResponseDto {
        return vigilumApi.postPersonalDetails(personalInfoReq)

    }

}