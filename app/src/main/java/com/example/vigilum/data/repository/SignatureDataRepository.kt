package com.example.vigilum.data.repository

import com.example.vigilum.data.datasource.signature.SignatureDataSource
import com.example.vigilum.data.remote.response.EmptyResponseDto
import com.example.vigilum.data.remote.response.SignatureData
import javax.inject.Inject

class SignatureDataRepository @Inject constructor(private val signatureDataSource: SignatureDataSource){
     suspend fun postSignature(signatureData: SignatureData): EmptyResponseDto {
        return signatureDataSource.postSignature(signatureData)
    }
}