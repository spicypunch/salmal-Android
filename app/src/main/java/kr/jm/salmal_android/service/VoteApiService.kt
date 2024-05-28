package kr.jm.salmal_android.service

import kr.jm.salmal_android.data.request.VoteEvaluationRequest
import kr.jm.salmal_android.data.response.VotesListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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

}