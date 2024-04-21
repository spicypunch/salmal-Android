package kr.jm.salmal_android.repository

import kr.jm.salmal_android.data.LoginRequest
import kr.jm.salmal_android.data.LoginResponse
import kr.jm.salmal_android.service.CertifiedApiService
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val certifiedApiService: CertifiedApiService
) : Repository {
    override suspend fun login(loginRequest: LoginRequest): LoginResponse {
        return certifiedApiService.login(loginRequest)
    }
}