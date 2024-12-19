package com.example.vigilum.data.datasource.fingerprint

import com.example.vigilum.data.remote.request.PersonalInfoRequest
import com.example.vigilum.data.remote.response.EmptyResponseDto
import com.example.vigilum.data.remote.response.FingerprintData
import retrofit2.http.Body

interface FingerPrintDatasource {
    suspend fun postFingerprint(@Body fingerprintData: FingerprintData): EmptyResponseDto

}