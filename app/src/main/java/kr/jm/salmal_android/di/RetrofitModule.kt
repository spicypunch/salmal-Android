package kr.jm.salmal_android.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.jm.salmal_android.service.CertifiedApiService
import kr.jm.salmal_android.service.CommentsApiService
import kr.jm.salmal_android.service.MemberApiService
import kr.jm.salmal_android.service.NotificationApiService
import kr.jm.salmal_android.service.VoteApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private const val BASE_URL = "https://api.sal-mal.com/api/"

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder().apply {
        addInterceptor(logging)
    }.build()

    @Singleton
    @Provides
    fun provideCertifiedApiService(): CertifiedApiService {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(CertifiedApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideCommentsApiService(): CommentsApiService {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(CommentsApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideMemberApiService(): MemberApiService {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(MemberApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideNotificationApiService(): NotificationApiService {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(NotificationApiService::class.java)
    }


    @Singleton
    @Provides
    fun provideVoteApiService(): VoteApiService {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(VoteApiService::class.java)
    }
}