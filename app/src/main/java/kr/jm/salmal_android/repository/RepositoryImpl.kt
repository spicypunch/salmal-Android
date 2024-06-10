package kr.jm.salmal_android.repository

import kr.jm.salmal_android.data.request.LoginRequest
import kr.jm.salmal_android.data.request.SignUpRequest
import kr.jm.salmal_android.data.request.VoteEvaluationRequest
import kr.jm.salmal_android.data.response.CommentsItem
import kr.jm.salmal_android.data.response.LoginResponse
import kr.jm.salmal_android.data.response.SignUpResponse
import kr.jm.salmal_android.data.response.UserInfoResponse
import kr.jm.salmal_android.data.response.VotesListResponse
import kr.jm.salmal_android.service.CertifiedApiService
import kr.jm.salmal_android.service.CommentsApiService
import kr.jm.salmal_android.service.MemberApiService
import kr.jm.salmal_android.service.NotificationApiService
import kr.jm.salmal_android.service.VoteApiService
import retrofit2.Response
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val certifiedApiService: CertifiedApiService,
    private val commentsApiService: CommentsApiService,
    private val memberApiService: MemberApiService,
    private val notificationApiService: NotificationApiService,
    private val voteApiService: VoteApiService
) : Repository {
    override suspend fun login(loginRequest: LoginRequest): LoginResponse {
        return certifiedApiService.login(loginRequest)
    }

    override suspend fun signUp(signUpRequest: SignUpRequest): SignUpResponse {
        return certifiedApiService.signUp(signUpRequest)
    }

    override suspend fun getUserInfo(accessToken: String, memberId: Int): UserInfoResponse {
        return memberApiService.getUserInfo(accessToken, memberId)
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

    override suspend fun voteEvaluation(
        accessToken: String,
        voteId: String,
        voteEvaluationType: String
    ): Response<Unit> {
        return voteApiService.voteEvaluation(
            accessToken,
            voteId,
            VoteEvaluationRequest(voteEvaluationType)
        )
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

    override suspend fun voteReport(
        accessToken: String,
        voteId: String,
        reason: String
    ): Response<Unit> {
        return voteApiService.voteReport(accessToken, voteId, reason)
    }

    override suspend fun userBan(accessToken: String, memberId: String): Response<Unit> {
        return memberApiService.userBan(accessToken, memberId)
    }

    override suspend fun getCommentsList(
        accessToken: String,
        voteId: String,
    ): List<CommentsItem.CommentsResponse> {
        return voteApiService.getCommentsList(accessToken, voteId)
    }

    override suspend fun getSubCommentsList(
        accessToken: String,
        commentId: Int,
        cursorId: Int?,
        size: Int
    ): CommentsItem.SubCommentsResponse {
        return commentsApiService.getSubCommentsList(accessToken, commentId, cursorId, size)
    }

    override suspend fun likeComment(accessToken: String, commentId: Int): Response<Unit> {
        return commentsApiService.likeComment(accessToken, commentId)
    }

    override suspend fun disLikeComment(accessToken: String, commentId: Int): Response<Unit> {
        return commentsApiService.disLikeComment(accessToken, commentId)
    }

    override suspend fun addComment(
        accessToken: String,
        voteId: String,
        content: String
    ): Response<Unit> {
        return voteApiService.addComment(accessToken, voteId, content)
    }

    override suspend fun addSubComment(
        accessToken: String,
        commentId: Int,
        content: String
    ): Response<Unit> {
        return commentsApiService.addSubComment(accessToken, commentId, content)
    }

    override suspend fun reportComment(accessToken: String, commentId: Int): Response<Unit> {
        return commentsApiService.reportComment(accessToken, commentId)
    }

    override suspend fun updateComment(
        accessToken: String,
        commentId: Int,
        content: String
    ): Response<Unit> {
        return commentsApiService.updateComment(accessToken, commentId, content)
    }

    override suspend fun deleteComment(accessToken: String, commentId: Int): Response<Unit> {
        return commentsApiService.deleteComment(accessToken, commentId)
    }


}