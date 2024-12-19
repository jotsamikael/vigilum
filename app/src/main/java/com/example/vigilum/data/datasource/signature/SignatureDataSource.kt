package com.example.vigilum.data.datasource.signature

import com.example.vigilum.data.remote.response.EmptyResponseDto
import com.example.vigilum.data.remote.response.SignatureData
import retrofit2.http.Body

interface SignatureDataSource {
    suspend fun postSignature(@Body signatureData: SignatureData): EmptyResponseDto

}