package kr.lifesemantics.salmal_android.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.lifesemantics.salmal_android.service.CertifiedApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private const val BASE_URL = "https://api.sal-mal.com/api"

    @Singleton
    @Provides
    fun provideOpenApiService(): CertifiedApiService {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(CertifiedApiService::class.java)
    }
}