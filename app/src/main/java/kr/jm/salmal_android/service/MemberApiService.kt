package kr.jm.salmal_android.service

import kr.jm.salmal_android.data.response.UserInfoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

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
}