package kr.jm.salmal_android.service

import kr.jm.salmal_android.data.response.CommentsItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface CommentsApiService {

    @GET("comments/{commentId}/replies")
    suspend fun getSubCommentsList(
        @Header("Authorization") accessToken: String,
        @Path("commentId") commentId: Int,
        @Query("cursorId") cursorId: Int?,
        @Query("size") size: Int?
    ): CommentsItem.SubCommentsResponse

    @POST("comments/{commentId}/likes")
    suspend fun likeComment(
        @Header("Authorization") accessToken: String,
        @Path("commentId") commentId: Int,
    ): Response<Unit>

    @DELETE("comments/{commentId}/likes")
    suspend fun disLikeComment(
        @Header("Authorization") accessToken: String,
        @Path("commentId") commentId: Int,
    ): Response<Unit>

    @POST("comments/{commentId}/replies")
    suspend fun addSubComment(
        @Header("Authorization") accessToken: String,
        @Path("commentId") commentId: Int,
        @Body content: String
    ): Response<Unit>

    @POST("comments/{commentId}/reports")
    suspend fun reportComment(
        @Header("Authorization") accessToken: String,
        @Path("commentId") commentId: Int,
    ): Response<Unit>

    @PUT("comments/{commentId}")
    suspend fun updateComment(
        @Header("Authorization") accessToken: String,
        @Path("commentId") commentId: Int,
        @Body content: String
    ): Response<Unit>

    @DELETE("comments/{commentId}")
    suspend fun deleteComment(
        @Header("Authorization") accessToken: String,
        @Path("commentId") commentId: Int,
    ): Response<Unit>

}