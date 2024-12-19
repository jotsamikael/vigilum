package com.example.vigilum.data.remote


import com.example.vigilum.data.remote.request.PersonalInfoRequest
import com.example.vigilum.data.remote.response.DocumentPicture
import com.example.vigilum.data.remote.response.EmptyResponseDto
import com.example.vigilum.data.remote.response.FingerprintData
import com.example.vigilum.data.remote.response.GDZServiceResponse
import com.example.vigilum.data.remote.response.ProfilePictureData
import com.example.vigilum.data.remote.response.SignatureData
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface VigilumApi {

    @GET("identification-methods/1")
    suspend fun getGDZServices(): Response<GDZServiceResponse>

    @POST("/post-document-pic")
    suspend fun postDocumentPic(@Body documentPicture: DocumentPicture): EmptyResponseDto

    @POST("/post-profile-pic")
    suspend fun postProfilePicture(@Body profilePictureData: ProfilePictureData): EmptyResponseDto

    @POST("personal-details")
    suspend fun postPersonalDetails(@Body personalInfo: PersonalInfoRequest): EmptyResponseDto

    @POST("fingerprint")
    suspend fun postFingerprint(@Body signatureData: FingerprintData): EmptyResponseDto


    @POST("signature")
    suspend fun postSignature(@Body signatureData: SignatureData): EmptyResponseDto


}