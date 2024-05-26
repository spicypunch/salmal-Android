package kr.jm.salmal_android.repository

import kr.jm.salmal_android.data.request.LoginRequest
import kr.jm.salmal_android.data.request.SignUpRequest
import kr.jm.salmal_android.data.request.VoteEvaluationRequest
import kr.jm.salmal_android.data.response.LoginResponse
import kr.jm.salmal_android.data.response.SignUpResponse
import kr.jm.salmal_android.data.response.VotesListResponse
import kr.jm.salmal_android.service.CertifiedApiService
import kr.jm.salmal_android.service.VoteApiService
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val certifiedApiService: CertifiedApiService,
    private val voteApiService: VoteApiService
) : Repository {
    override suspend fun login(loginRequest: LoginRequest): LoginResponse {
        return certifiedApiService.login(loginRequest)
    }

    override suspend fun signUp(signUpRequest: SignUpRequest): SignUpResponse {
        return certifiedApiService.signUp(signUpRequest)
    }

    override suspend fun votesList(
        accessToken: String,
        cursorId: String,
        cursorLikes: String,
        size: Int,
        searchType: String
    ): VotesListResponse {
        return voteApiService.votesList(accessToken, cursorId, cursorLikes, size, searchType)
    }

    override suspend fun voteEvaluation(accessToken: String, voteId: String, voteEvaluationType: String) {
        return voteApiService.voteEvaluation(accessToken, voteId, VoteEvaluationRequest(voteEvaluationType))
    }

    override suspend fun voteEvaluationDelete(accessToken: String, voteId: String) {
        return voteApiService.voteEvaluationDelete(accessToken, voteId)
    }
}