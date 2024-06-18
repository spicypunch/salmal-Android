package kr.jm.salmal_android.service

import kr.jm.salmal_android.data.request.VoteEvaluationRequest
import kr.jm.salmal_android.data.response.CommentsItem
import kr.jm.salmal_android.data.response.VotesListResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.File

interface VoteApiService {

    @GET("votes/{voteId}")
    suspend fun vote(
        @Header("Authorization") accessToken: String,
        @Path("voteId") voteId: String,
    ): VotesListResponse.Vote

    @GET("votes")
    suspend fun votesList(
        @Header("Authorization") accessToken: String,
        @Query("cursorId") cursorId: String,
        @Query("cursorLikes") cursorLikes: String,
        @Query("size") size: Int,
        @Query("searchType") searchType: String,
    ): VotesListResponse

    @POST("votes/{voteId}/evaluations")
    suspend fun voteEvaluation(
        @Header("Authorization") accessToken: String,
        @Path("voteId") voteId: String,
        @Body voteEvaluationRequest: VoteEvaluationRequest
    ): Response<Unit>

    @DELETE("votes/{voteId}/evaluations")
    suspend fun voteEvaluationDelete(
        @Header("Authorization") accessToken: String,
        @Path("voteId") voteId: String,
    ): Response<Unit>

    @POST("votes/{voteId}/bookmarks")
    suspend fun addBookmark(
        @Header("Authorization") accessToken: String,
        @Path("voteId") voteId: String,
    ): Response<Unit>

    @DELETE("votes/{voteId}/bookmarks")
    suspend fun deleteBookmark(
        @Header("Authorization") accessToken: String,
        @Path("voteId") voteId: String,
    ): Response<Unit>

    @POST("v2/votes/{voteId}/reports")
    suspend fun voteReport(
        @Header("Authorization") accessToken: String,
        @Path("voteId") voteId: String,
        @Body reason: String
    ): Response<Unit>

    @GET("votes/{voteId}/comments/all")
    suspend fun getCommentsList(
        @Header("Authorization") accessToken: String,
        @Path("voteId") voteId: String,
    ): List<CommentsItem.CommentsResponse>

    @POST("votes/{voteId}/comments")
    suspend fun addComment(
        @Header("Authorization") accessToken: String,
        @Path("voteId") voteId: String,
        @Body comment: String
    ): Response<Unit>

    @Multipart
    @POST("votes")
    suspend fun registerVote(
        @Header("Authorization") accessToken: String,
        @Part imageFile: MultipartBody.Part
    ): Response<Unit>
}