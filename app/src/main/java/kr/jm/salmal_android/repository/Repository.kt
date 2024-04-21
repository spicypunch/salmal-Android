package kr.jm.salmal_android.repository

import kr.jm.salmal_android.data.LoginRequest
import kr.jm.salmal_android.data.LoginResponse

interface Repository {

    suspend fun login(loginRequest: LoginRequest): LoginResponse
}