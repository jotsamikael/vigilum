package com.example.vigilum.di


import android.app.Application
import androidx.room.Room
import com.example.vigilum.data.datasource.remoteconf.RemoteConfigDataSource
import com.example.vigilum.data.datasource.remoteconf.RemoteConfigDataSourceImpl
import com.example.vigilum.data.datasource.VigilumDataSource
import com.example.vigilum.data.datasource.VigilumDataSourceImpl
import com.example.vigilum.data.datasource.camera.PostDocumentPicRectoImpl
import com.example.vigilum.data.datasource.camera.PostDocumentPicVersoImpl
import com.example.vigilum.data.datasource.camera.PostDocumentPic_RectoDataSource
import com.example.vigilum.data.datasource.camera.PostDocumentPic_VersoDataSource
import com.example.vigilum.data.datasource.camera.ProfilePictureDataSource
import com.example.vigilum.data.datasource.camera.ProfilePictureDataSourceImpl
import com.example.vigilum.data.datasource.fingerprint.FingerPrintDatasource
import com.example.vigilum.data.datasource.fingerprint.FingerPrintDatasourceImpl
import com.example.vigilum.data.datasource.personaldetail.PersonalInfoDataSource
import com.example.vigilum.data.datasource.personaldetail.PersonalInfoDataSourceImpl
import com.example.vigilum.data.datasource.signature.SignatureDataSource
import com.example.vigilum.data.datasource.signature.SignatureDataSourceImpl
import com.example.vigilum.data.local.remoteConfig.RemoteConfigDatabase
import com.example.vigilum.data.remote.RemoteConfigApi
import com.example.vigilum.data.remote.VigilumApi
import com.example.vigilum.data.repository.FingerprintRepository
import com.example.vigilum.data.repository.PersonalDetailsRepository
import com.example.vigilum.data.repository.PostDocumentPic_RectoRepository
import com.example.vigilum.data.repository.PostDocumentPic_VersoRepository
import com.example.vigilum.data.repository.ProfilePictureRepository
import com.example.vigilum.data.repository.RemoteConfigurationRepository
import com.example.vigilum.data.repository.SignatureDataRepository
import com.example.vigilum.data.repository.VigilumRepository
import com.example.vigilum.utils.AppConstant.APP_BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @author JOTSA MIKAEL
 * Perform dependency injections here
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //create a retrofit object we can use anywhere inside our app
    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {

        //see the network logs of app
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC //shows endpoint and status
        }

        // Add a custom interceptor to set the content type header to application/json for POST requests
        val jsonContentTypeInterceptor = Interceptor { chain ->
            var request = chain.request()
            if (request.method == "POST") {
                request = request.newBuilder()
                    .header("Content-Type", "application/json")
                    .build()
            }
            chain.proceed(request)
        }

        val httpClient = OkHttpClient().newBuilder().apply {
            addInterceptor(httpLoggingInterceptor)
            addInterceptor(jsonContentTypeInterceptor) // Add the custom interceptor here
        }
        //All retofit request will timeout after 60s
        httpClient.apply {
            readTimeout(60, TimeUnit.SECONDS)
        }

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory()).build()
        return Retrofit.Builder()
            .baseUrl(APP_BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    //provide Remote cofig api
    @Provides
    @Singleton
    fun providesRemoteConfigApi(retrofit: Retrofit):RemoteConfigApi{
        return retrofit.create(RemoteConfigApi::class.java)
    }

    //provide Remote cofig api
    @Provides
    @Singleton
    fun providesVigilumApi(retrofit: Retrofit):VigilumApi{
        return retrofit.create(VigilumApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieDatabase(app: Application):RemoteConfigDatabase{
        return Room.databaseBuilder(
            app,
            RemoteConfigDatabase::class.java,
            "remoteconfig.db"
        ).build()
    }

    //create
    @Provides
    @Singleton
    fun providesRemoteConfigDatasource(remoteConfigApi: RemoteConfigApi, remoteConfigDatabase: RemoteConfigDatabase): RemoteConfigDataSource {
        return RemoteConfigDataSourceImpl(remoteConfigApi, remoteConfigDatabase)
    }
    @Provides
    @Singleton
    fun providesRemoteConfigurationRepository(remoteConfigDataSource: RemoteConfigDataSource): RemoteConfigurationRepository{
        return RemoteConfigurationRepository(remoteConfigDataSource)
    }


    @Provides
    @Singleton
    fun providesVigilumDatasource(vigilumApi: VigilumApi): VigilumDataSource {
        return VigilumDataSourceImpl(vigilumApi)
    }
    @Provides
    @Singleton
    fun providesVigilumRepository(vigilumDataSource: VigilumDataSource): VigilumRepository{
        return VigilumRepository(vigilumDataSource)
    }


    //inject application and VigilumApi in PostDocumentPicRectoImpl
    @Provides
    @Singleton
    fun providesPostDocumentPicRectoDataSource(application: Application, vigilumApi: VigilumApi): PostDocumentPic_RectoDataSource {
        return PostDocumentPicRectoImpl(application,vigilumApi)
    }

    //inject application and VigilumApi in PostDocumentPicVersoImpl
    @Provides
    @Singleton
    fun providesPostDocumentPicVersoDataSource(application: Application, vigilumApi: VigilumApi): PostDocumentPic_VersoDataSource {
        return PostDocumentPicVersoImpl(application,vigilumApi)
    }

    //inject application and VigilumApi in ProfilePictureImpl
    @Provides
    @Singleton
    fun providesPostProfilePicDataSource(application: Application, vigilumApi: VigilumApi): ProfilePictureDataSource {
        return ProfilePictureDataSourceImpl(application,vigilumApi)
    }

    //inject VigilumApi in SignatureImpl
    @Provides
    @Singleton
    fun providesSignatureDataSource(vigilumApi: VigilumApi): SignatureDataSource {
        return SignatureDataSourceImpl(vigilumApi)
    }

    //inject VigilumApi in SignatureImpl
    @Provides
    @Singleton
    fun providesFingerprintDataSource(vigilumApi: VigilumApi): FingerPrintDatasource {
        return FingerPrintDatasourceImpl(vigilumApi)
    }

    //inject VigilumApi in PersonalInfoImpl
    @Provides
    @Singleton
    fun providesPersonalInfoDataSource(vigilumApi: VigilumApi): PersonalInfoDataSource {
        return PersonalInfoDataSourceImpl(vigilumApi)
    }

    /////////////////////////////////////////////////////////////////////

    //inject personalDetailsDataSource in personalDetailsRepo
    @Provides
    @Singleton
    fun providesPersonalDetails(personalInfoDataSource: PersonalInfoDataSource): PersonalDetailsRepository {
        return PersonalDetailsRepository(personalInfoDataSource)
    }

    //inject signatureDataSource in signatureDataRepo
    @Provides
    @Singleton
    fun providesSignatureData(signatureDataSource: SignatureDataSource): SignatureDataRepository {
        return SignatureDataRepository(signatureDataSource)
    }

    //inject fingerprintDataSource in fingerprintRepo
    @Provides
    @Singleton
    fun providesFingerPrintData(fingerPrintDatasource: FingerPrintDatasource): FingerprintRepository {
        return FingerprintRepository(fingerPrintDatasource)
    }

    //inject PostDocumentPic_RectoDataSource in PostDocumentPic_RectoRepository
    @Provides
    @Singleton
    fun providesPostDocumentPicRectoRepository(postDocumentPicRectoDataSource:PostDocumentPic_RectoDataSource): PostDocumentPic_RectoRepository {
        return PostDocumentPic_RectoRepository(postDocumentPicRectoDataSource)
    }

    //inject PostDocumentPic_VersoDataSource in PostDocumentPic_VersoRepository
    @Provides
    @Singleton
    fun providesPostDocumentPicVersoRepository(postDocumentPicVersoDataSource: PostDocumentPic_VersoDataSource): PostDocumentPic_VersoRepository {
        return PostDocumentPic_VersoRepository(postDocumentPicVersoDataSource)
    }

    //inject ProfilePictureDataSource in ProfilePicRepository
    @Provides
    @Singleton
    fun providesProfilePicRepository(profilePictureDataSource: ProfilePictureDataSource): ProfilePictureRepository {
        return ProfilePictureRepository(profilePictureDataSource)
    }


}