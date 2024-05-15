package kr.jm.salmal_android.service

import kr.jm.salmal_android.data.response.VotesListResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface VoteApiService {

    @GET("/api/votes")
    suspend fun votesList(
        @Header("Authorization") accessToken: String,
        @Query("cursorId") cursorId: String = "",
        @Query("cursorLikes") cursorLikes: String = "",
        @Query("size") size: Int,
        @Query("searchType") searchType: String,
    ) : VotesListResponse
}