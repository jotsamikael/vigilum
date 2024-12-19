package com.example.vigilum.data.repository

import com.example.utilities.ResourceState
import com.example.vigilum.data.datasource.VigilumDataSource
import com.example.vigilum.data.remote.request.PersonalInfoRequest
import com.example.vigilum.data.remote.response.DocumentPicture
import com.example.vigilum.data.remote.response.FingerprintData
import com.example.vigilum.data.remote.response.GDZServiceResponse
import com.example.vigilum.data.remote.response.ProfilePictureData
import com.example.vigilum.data.remote.response.SignatureData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VigilumRepository @Inject
constructor(private val vigilumDataSource: VigilumDataSource) {

    suspend fun getGDZServices(): Flow<ResourceState<GDZServiceResponse>> {
        return flow {
            emit(ResourceState.Loading())
            val response = vigilumDataSource.getGDZServices()
            if (response.isSuccessful) {
                emit(ResourceState.Success(response.body()!!))

            } else {

                emit(ResourceState.Error("Error fetching data"))

            }
        }
    }

    suspend fun postDocumentPic(documentPicture: DocumentPicture){
       vigilumDataSource.postDocumentPic(documentPicture)
    }

    suspend fun postProfilePicture(profilePictureData: ProfilePictureData){
        vigilumDataSource.postProfilePicture(profilePictureData)
    }

    suspend fun postPersonalDetails(personalDetails: PersonalInfoRequest){
        vigilumDataSource.postPersonalDetails(personalDetails)
    }

    suspend fun postFingerprint(fingerprintData: FingerprintData){
        vigilumDataSource.postFingerprint(fingerprintData)
    }

    suspend fun postSignature(signatureData: SignatureData){
        vigilumDataSource.postSignature(signatureData)
    }
}