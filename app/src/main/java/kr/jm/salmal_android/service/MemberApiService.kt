package kr.jm.salmal_android.service

import kr.jm.salmal_android.data.request.UpdateMyInfoRequest
import kr.jm.salmal_android.data.response.BanListResponse
import kr.jm.salmal_android.data.response.BookMarkResponse
import kr.jm.salmal_android.data.response.MyEvaluations
import kr.jm.salmal_android.data.response.MyVotesResponse
import kr.jm.salmal_android.data.response.UserInfoResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
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

    @GET("members/{memberId}/bookmarks")
    suspend fun getMyBookmarks(
        @Header("Authorization") accessToken: String,
        @Path("memberId") memberId: String,
        @Query("cursorId") cursorId: String?,
        @Query("size") size: Int?,
    ): BookMarkResponse

    @DELETE("members/{memberId}")
    suspend fun withdrawal(
        @Header("Authorization") accessToken: String,
        @Path("memberId") memberId: String,
    ): Response<Unit>

    /**
     * Todo 파일 업로드 하는 법?
     */
    @Multipart
    @POST("members/{memberId}/images")
    suspend fun updateProfileImage(
        @Header("Authorization") accessToken: String,
        @Path("memberId") memberId: String,
        @Part imageFile: MultipartBody.Part
    ): Response<Unit>

    @PUT("members/{memberId}")
    suspend fun updateMyInfo(
        @Header("Authorization") accessToken: String,
        @Path("memberId") memberId: String,
        @Body updateMyInfoRequest: UpdateMyInfoRequest
    ): Response<Unit>

    @GET("members/{memberId}/blocks")
    suspend fun getBanList(
        @Header("Authorization") accessToken: String,
        @Path("memberId") memberId: String,
        @Query("cursorId") cursorId: String?,
        @Query("size") size: Int?,
    ): BanListResponse

    @DELETE("members/{memberId}/blocks")
    suspend fun cancelUserBan(
        @Header("Authorization") accessToken: String,
        @Path("memberId") memberId: String,
    ): Response<Unit>
}