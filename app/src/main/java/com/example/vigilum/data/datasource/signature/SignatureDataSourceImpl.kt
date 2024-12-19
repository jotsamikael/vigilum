package com.example.vigilum.data.datasource.signature

import com.example.vigilum.data.datasource.fingerprint.FingerPrintDatasource
import com.example.vigilum.data.remote.VigilumApi
import com.example.vigilum.data.remote.response.EmptyResponseDto
import com.example.vigilum.data.remote.response.FingerprintData
import com.example.vigilum.data.remote.response.SignatureData
import javax.inject.Inject

class SignatureDataSourceImpl  @Inject constructor(private val vigilumApi: VigilumApi):
    SignatureDataSource {
    override suspend fun postSignature(signature: SignatureData): EmptyResponseDto {
        return vigilumApi.postSignature(signature)

    }

}