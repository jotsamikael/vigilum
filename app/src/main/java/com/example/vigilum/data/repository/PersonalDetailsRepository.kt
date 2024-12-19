package com.example.vigilum.data.repository

import com.example.vigilum.data.datasource.personaldetail.PersonalInfoDataSource
import com.example.vigilum.data.remote.request.PersonalInfoRequest
import com.example.vigilum.data.remote.response.EmptyResponseDto
import javax.inject.Inject

class PersonalDetailsRepository @Inject constructor(private val personalInfoDataSource: PersonalInfoDataSource) {
    suspend fun postPersonalInfo(visitDetails: PersonalInfoRequest): EmptyResponseDto {
        return personalInfoDataSource.postPersonalInfo(visitDetails)
    }
}