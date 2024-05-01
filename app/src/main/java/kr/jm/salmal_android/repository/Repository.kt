package kr.jm.salmal_android.repository

import kr.jm.salmal_android.data.request.LoginRequest
import kr.jm.salmal_android.data.response.LoginResponse
import kr.jm.salmal_android.data.request.SignUpRequest
import kr.jm.salmal_android.data.response.SignUpResponse

interface Repository {

    suspend fun login(loginRequest: LoginRequest): LoginResponse

    suspend fun signUp(signUpRequest: SignUpRequest): SignUpResponse
}