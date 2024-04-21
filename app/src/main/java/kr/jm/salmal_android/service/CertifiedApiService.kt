package kr.jm.salmal_android.service

import kr.jm.salmal_android.data.LoginRequest
import kr.jm.salmal_android.data.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface CertifiedApiService {

    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse
}