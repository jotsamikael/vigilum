package com.example.vigilum.data.repository

import com.example.vigilum.data.datasource.fingerprint.FingerPrintDatasource
import com.example.vigilum.data.remote.response.EmptyResponseDto
import com.example.vigilum.data.remote.response.FingerprintData
import javax.inject.Inject

class FingerprintRepository @Inject constructor( private val fingerPrintDatasource: FingerPrintDatasource) {
     suspend fun postFingerprint(fingerprintData: FingerprintData): EmptyResponseDto {
       return fingerPrintDatasource.postFingerprint(fingerprintData)
    }
}