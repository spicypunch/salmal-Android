package kr.jm.salmal_android.service

import kr.jm.salmal_android.data.response.MyEvaluations
import kr.jm.salmal_android.data.response.MyVotesResponse
import kr.jm.salmal_android.data.response.UserInfoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MemberApiService {

    @GET("v2/members/{memberId}")
    suspend fun getUserInfo(
        @Header("Authorization") accessToken: String,
        @Path("memberId") memberId: Int,
    ): UserInfoResponse

    @POST("members/{memberId}/blocks")
    suspend fun userBan(
        @Header("Authorization") accessToken: String,
        @Path("memberId") memberId: String,
    ): Response<Unit>

    @GET("members/{memberId}/votes")
    suspend fun getMyVotes(
        @Header("Authorization") accessToken: String,
        @Path("memberId") memberId: String,
        @Query("cursorId") cursorId: String?,
        @Query("size") size: Int?,
    ): MyVotesResponse

    @GET("members/{memberId}/evaluations")
    suspend fun getMyEvaluations(
        @Header("Authorization") accessToken: String,
        @Path("memberId") memberId: String,
        @Query("cursorId") cursorId: String?,
        @Query("size") size: Int?,
    ): MyEvaluations

}