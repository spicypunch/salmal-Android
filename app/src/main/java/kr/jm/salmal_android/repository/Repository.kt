package kr.jm.salmal_android.repository

import kr.jm.salmal_android.data.request.LoginRequest
import kr.jm.salmal_android.data.response.LoginResponse
import kr.jm.salmal_android.data.request.SignUpRequest
import kr.jm.salmal_android.data.response.BookMarkResponse
import kr.jm.salmal_android.data.response.CommentsItem
import kr.jm.salmal_android.data.response.MyEvaluations
import kr.jm.salmal_android.data.response.MyVotesResponse
import kr.jm.salmal_android.data.response.SignUpResponse
import kr.jm.salmal_android.data.response.UserInfoResponse
import kr.jm.salmal_android.data.response.VotesListResponse
import okhttp3.MultipartBody
import retrofit2.Response
import java.io.File

interface Repository {

    suspend fun login(loginRequest: LoginRequest): LoginResponse

    suspend fun signUp(signUpRequest: SignUpRequest): SignUpResponse

    suspend fun getUserInfo(accessToken: String, memberId: Int): UserInfoResponse

    suspend fun getVote(
        accessToken: String,
        voteId: String,
    ): VotesListResponse.Vote

    suspend fun getVotesList(
        accessToken: String,
        cursorId: String,
        cursorLikes: String,
        size: Int,
        searchType: String
    ): VotesListResponse

    suspend fun voteEvaluation(
        accessToken: String,
        voteId: String,
        voteEvaluationType: String
    ): Response<Unit>

    suspend fun voteEvaluationDelete(
        accessToken: String,
        voteId: String,
    ): Response<Unit>

    suspend fun addBookmark(
        accessToken: String,
        voteId: String,
    ): Response<Unit>

    suspend fun deleteBookmark(
        accessToken: String,
        voteId: String,
    ): Response<Unit>

    suspend fun voteReport(
        accessToken: String,
        voteId: String,
        reason: String
    ): Response<Unit>

    suspend fun userBan(
        accessToken: String,
        memberId: String
    ): Response<Unit>

    suspend fun getCommentsList(
        accessToken: String,
        voteId: String,
    ): List<CommentsItem.CommentsResponse>

    suspend fun getSubCommentsList(
        accessToken: String,
        commentId: Int,
        cursorId: Int?,
        size: Int
    ): CommentsItem.SubCommentsResponse

    suspend fun likeComment(
        accessToken: String,
        commentId: Int,
    ): Response<Unit>

    suspend fun disLikeComment(
        accessToken: String,
        commentId: Int,
    ): Response<Unit>

    suspend fun addComment(
        accessToken: String,
        voteId: String,
        content: String
    ): Response<Unit>

    suspend fun addSubComment(
        accessToken: String,
        commentId: Int,
        content: String
    ): Response<Unit>

    suspend fun reportComment(
        accessToken: String,
        commentId: Int,
    ): Response<Unit>

    suspend fun updateComment(
        accessToken: String,
        commentId: Int,
        content: String
    ): Response<Unit>

    suspend fun deleteComment(
        accessToken: String,
        commentId: Int,
    ): Response<Unit>

    suspend fun registerVote(
        accessToken: String,
        imageFile: MultipartBody.Part
    ): Response<Unit>

    suspend fun getMyVotes(
        accessToken: String,
        memberId: String,
    ): MyVotesResponse

    suspend fun getMyEvaluations(
        accessToken: String,
        memberId: String,
    ): MyEvaluations

    suspend fun getMyBookMarks(
        accessToken: String,
        memberId: String,
    ): BookMarkResponse
}