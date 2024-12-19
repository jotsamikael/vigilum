package com.example.vigilum.data.datasource

import com.example.vigilum.data.remote.request.PersonalInfoRequest
import com.example.vigilum.data.remote.response.DocumentPicture
import com.example.vigilum.data.remote.response.EmptyResponseDto
import com.example.vigilum.data.remote.response.FingerprintData
import com.example.vigilum.data.remote.response.GDZServiceResponse
import com.example.vigilum.data.remote.response.ProfilePictureData
import com.example.vigilum.data.remote.response.SignatureData
import okhttp3.ResponseBody
import retrofit2.Response


interface VigilumDataSource {

    suspend fun getGDZServices(): Response<GDZServiceResponse>

    suspend fun postDocumentPic(documentPicture: DocumentPicture): EmptyResponseDto

    suspend fun postProfilePicture(profilePictureData: ProfilePictureData): EmptyResponseDto

    suspend fun postPersonalDetails(personalDetails: PersonalInfoRequest): EmptyResponseDto

    suspend fun postSignature(signatureData: SignatureData): EmptyResponseDto

    suspend fun postFingerprint(fingerprintData: FingerprintData): EmptyResponseDto


}