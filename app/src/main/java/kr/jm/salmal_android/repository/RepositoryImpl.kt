package kr.jm.salmal_android.repository

import kr.jm.salmal_android.data.request.LoginRequest
import kr.jm.salmal_android.data.request.SignUpRequest
import kr.jm.salmal_android.data.request.VoteEvaluationRequest
import kr.jm.salmal_android.data.response.LoginResponse
import kr.jm.salmal_android.data.response.SignUpResponse
import kr.jm.salmal_android.data.response.VotesListResponse
import kr.jm.salmal_android.service.CertifiedApiService
import kr.jm.salmal_android.service.VoteApiService
import retrofit2.Response
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

    override suspend fun getVote(accessToken: String, voteId: String): VotesListResponse.Vote {
        return voteApiService.vote(accessToken, voteId)
    }


    override suspend fun getVotesList(
        accessToken: String,
        cursorId: String,
        cursorLikes: String,
        size: Int,
        searchType: String
    ): VotesListResponse {
        return voteApiService.votesList(accessToken, cursorId, cursorLikes, size, searchType)
    }

    override suspend fun voteEvaluation(accessToken: String, voteId: String, voteEvaluationType: String): Response<Unit> {
        return voteApiService.voteEvaluation(accessToken, voteId, VoteEvaluationRequest(voteEvaluationType))
    }

    override suspend fun voteEvaluationDelete(accessToken: String, voteId: String): Response<Unit> {
        return voteApiService.voteEvaluationDelete(accessToken, voteId)
    }

    override suspend fun addBookmark(
        accessToken: String,
        voteId: String,
    ): Response<Unit> {
        return voteApiService.addBookmark(accessToken, voteId)
    }

    override suspend fun deleteBookmark(accessToken: String, voteId: String): Response<Unit> {
        return voteApiService.deleteBookmark(accessToken, voteId)
    }
}