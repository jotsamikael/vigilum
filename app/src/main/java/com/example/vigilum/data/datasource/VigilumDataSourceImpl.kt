package com.example.vigilum.data.datasource

import com.example.vigilum.data.remote.VigilumApi
import com.example.vigilum.data.remote.request.PersonalInfoRequest
import com.example.vigilum.data.remote.response.DocumentPicture
import com.example.vigilum.data.remote.response.EmptyResponseDto
import com.example.vigilum.data.remote.response.FingerprintData
import com.example.vigilum.data.remote.response.GDZServiceResponse
import com.example.vigilum.data.remote.response.ProfilePictureData
import com.example.vigilum.data.remote.response.SignatureData
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class VigilumDataSourceImpl @Inject constructor(
    private val vigilumApi: VigilumApi
):VigilumDataSource {
    override suspend fun getGDZServices(): Response<GDZServiceResponse> {
        return vigilumApi.getGDZServices()
    }

    override suspend fun postDocumentPic(documentPicture: DocumentPicture): EmptyResponseDto {
        return vigilumApi.postDocumentPic(documentPicture)
    }

    override suspend fun postProfilePicture(profilePictureData: ProfilePictureData): EmptyResponseDto {
        return vigilumApi.postProfilePicture(profilePictureData)
    }

    override suspend fun postPersonalDetails(personalDetails: PersonalInfoRequest): EmptyResponseDto {
        return vigilumApi.postPersonalDetails(personalDetails)
    }

    override suspend fun postSignature(signatureData: SignatureData): EmptyResponseDto {
        return vigilumApi.postSignature(signatureData)
    }

    override suspend fun postFingerprint(fingerprintData: FingerprintData): EmptyResponseDto {
        return vigilumApi.postFingerprint(fingerprintData)
    }

}