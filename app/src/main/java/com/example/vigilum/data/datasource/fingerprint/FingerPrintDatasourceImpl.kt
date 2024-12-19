package com.example.vigilum.data.datasource.fingerprint

import com.example.vigilum.data.datasource.personaldetail.PersonalInfoDataSource
import com.example.vigilum.data.remote.VigilumApi
import com.example.vigilum.data.remote.response.EmptyResponseDto
import com.example.vigilum.data.remote.response.FingerprintData
import javax.inject.Inject

class FingerPrintDatasourceImpl @Inject constructor(private val vigilumApi: VigilumApi):
    FingerPrintDatasource {
    override suspend fun postFingerprint(fingerprintData: FingerprintData): EmptyResponseDto {
       return vigilumApi.postFingerprint(fingerprintData)
    }
}