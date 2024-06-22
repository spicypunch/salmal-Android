package kr.jm.salmal_android.service

import kr.jm.salmal_android.data.request.LoginRequest
import kr.jm.salmal_android.data.request.SignUpRequest
import kr.jm.salmal_android.data.response.LoginResponse
import kr.jm.salmal_android.data.response.SignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface CertifiedApiService {

    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @POST("auth/signup/kakao")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): SignUpResponse

    @POST("auth/logout")
    suspend fun logout(
        @Header("Authorization") accessToken: String,
        @Body refreshToken: String
    ): Response<Unit>
}