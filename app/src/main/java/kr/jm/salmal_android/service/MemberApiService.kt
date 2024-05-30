package kr.jm.salmal_android.service

import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface MemberApiService {

    @POST("members/{memberId}/blocks")
    suspend fun userBan(
        @Header("Authorization") accessToken: String,
        @Path("memberId") memberId: String,
    ): Response<Unit>
}