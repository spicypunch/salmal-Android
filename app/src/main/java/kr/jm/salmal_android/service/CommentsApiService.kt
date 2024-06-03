package kr.jm.salmal_android.service

import kr.jm.salmal_android.data.response.SubCommentsResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface CommentsApiService {

    @GET("comments/{commentId}/replies")
    suspend fun getSubCommentsList(
        @Header("Authorization") accessToken: String,
        @Path("commentId") commentId: Int,
        @Query("cursorId") cursorId: Int?,
        @Query("size") size: Int?
    ): SubCommentsResponse
}