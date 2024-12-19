package com.example.vigilum.data.datasource.personaldetail

import com.example.vigilum.data.remote.request.PersonalInfoRequest
import com.example.vigilum.data.remote.response.EmptyResponseDto
import okhttp3.ResponseBody
import retrofit2.http.Body

interface PersonalInfoDataSource {
    suspend fun postPersonalInfo(@Body visitDetails: PersonalInfoRequest): EmptyResponseDto

}