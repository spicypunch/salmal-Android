package kr.jm.salmal_android.repository

import kr.jm.salmal_android.data.request.LoginRequest
import kr.jm.salmal_android.data.response.LoginResponse
import kr.jm.salmal_android.data.request.SignUpRequest
import kr.jm.salmal_android.data.response.SignUpResponse
import kr.jm.salmal_android.data.response.VotesListResponse

interface Repository {

    suspend fun login(loginRequest: LoginRequest): LoginResponse

    suspend fun signUp(signUpRequest: SignUpRequest): SignUpResponse

    suspend fun votesList(
        accessToken: String,
        cursorId: String,
        cursorLikes: String,
        size: Int,
        searchType: String
    ): VotesListResponse
}