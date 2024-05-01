package kr.jm.salmal_android.repository

import kr.jm.salmal_android.data.request.LoginRequest
import kr.jm.salmal_android.data.request.SignUpRequest
import kr.jm.salmal_android.data.response.LoginResponse
import kr.jm.salmal_android.data.response.SignUpResponse
import kr.jm.salmal_android.service.CertifiedApiService
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val certifiedApiService: CertifiedApiService
) : Repository {
    override suspend fun login(loginRequest: LoginRequest): LoginResponse {
        return certifiedApiService.login(loginRequest)
    }

    override suspend fun signUp(signUpRequest: SignUpRequest): SignUpResponse {
        return certifiedApiService.signUp(signUpRequest)
    }
}