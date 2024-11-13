package kr.jm.salmal_android.repository

import android.net.Uri
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kr.jm.salmal_android.data.request.LoginRequest
import kr.jm.salmal_android.data.request.SignUpRequest
import kr.jm.salmal_android.data.request.UpdateMyInfoRequest
import kr.jm.salmal_android.data.request.VoteEvaluationRequest
import kr.jm.salmal_android.data.response.BanListResponse
import kr.jm.salmal_android.data.response.BookMarkResponse
import kr.jm.salmal_android.data.response.CommentsItem
import kr.jm.salmal_android.data.response.LoginResponse
import kr.jm.salmal_android.data.response.MyEvaluations
import kr.jm.salmal_android.data.response.MyVotesResponse
import kr.jm.salmal_android.data.response.SignUpResponse
import kr.jm.salmal_android.data.response.UserInfoResponse
import kr.jm.salmal_android.data.response.VotesListResponse
import kr.jm.salmal_android.service.CertifiedApiService
import kr.jm.salmal_android.service.CommentsApiService
import kr.jm.salmal_android.service.MemberApiService
import kr.jm.salmal_android.service.NotificationApiService
import kr.jm.salmal_android.service.VoteApiService
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val certifiedApiService: CertifiedApiService,
    private val commentsApiService: CommentsApiService,
    private val memberApiService: MemberApiService,
    private val notificationApiService: NotificationApiService,
    private val voteApiService: VoteApiService,
    private val dataStore: DataStore<Preferences>
) : Repository {
    override suspend fun login(loginRequest: LoginRequest): LoginResponse {
        return certifiedApiService.login(loginRequest)
    }

    override suspend fun signUp(signUpRequest: SignUpRequest): SignUpResponse {
        return certifiedApiService.signUp(signUpRequest)
    }

    override suspend fun getUserInfo(accessToken: String, memberId: Int): UserInfoResponse {
        return memberApiService.getUserInfo(accessToken, memberId)
    }

    override suspend fun getVote(accessToken: String, voteId: String): VotesListResponse.Vote {
        return voteApiService.vote(accessToken, voteId)
    }

    override suspend fun getVotesList(
        accessToken: String,
        cursorId: String,
        cursorLikes: String,
        size: Int,
        searchType: String
    ): VotesListResponse {
        return voteApiService.votesList(accessToken, cursorId, cursorLikes, size, searchType)
    }

    override suspend fun voteEvaluation(
        accessToken: String,
        voteId: String,
        voteEvaluationType: String
    ): Response<Unit> {
        return voteApiService.voteEvaluation(
            accessToken,
            voteId,
            VoteEvaluationRequest(voteEvaluationType)
        )
    }

    override suspend fun voteEvaluationDelete(accessToken: String, voteId: String): Response<Unit> {
        return voteApiService.voteEvaluationDelete(accessToken, voteId)
    }

    override suspend fun addBookmark(
        accessToken: String,
        voteId: String,
    ): Response<Unit> {
        return voteApiService.addBookmark(accessToken, voteId)
    }

    override suspend fun deleteBookmark(accessToken: String, voteId: String): Response<Unit> {
        return voteApiService.deleteBookmark(accessToken, voteId)
    }

    override suspend fun voteReport(
        accessToken: String,
        voteId: String,
        reason: String
    ): Response<Unit> {
        return voteApiService.voteReport(accessToken, voteId, reason)
    }

    override suspend fun userBan(accessToken: String, memberId: String): Response<Unit> {
        return memberApiService.userBan(accessToken, memberId)
    }

    override suspend fun getCommentsList(
        accessToken: String,
        voteId: String,
    ): List<CommentsItem.CommentsResponse> {
        return voteApiService.getCommentsList(accessToken, voteId)
    }

    override suspend fun getSubCommentsList(
        accessToken: String,
        commentId: Int,
        cursorId: Int?,
        size: Int
    ): CommentsItem.SubCommentsResponse {
        return commentsApiService.getSubCommentsList(accessToken, commentId, cursorId, size)
    }

    override suspend fun likeComment(accessToken: String, commentId: Int): Response<Unit> {
        return commentsApiService.likeComment(accessToken, commentId)
    }

    override suspend fun disLikeComment(accessToken: String, commentId: Int): Response<Unit> {
        return commentsApiService.disLikeComment(accessToken, commentId)
    }

    override suspend fun addComment(
        accessToken: String,
        voteId: String,
        content: String
    ): Response<Unit> {
        return voteApiService.addComment(accessToken, voteId, content)
    }

    override suspend fun addSubComment(
        accessToken: String,
        commentId: Int,
        content: String
    ): Response<Unit> {
        return commentsApiService.addSubComment(accessToken, commentId, content)
    }

    override suspend fun reportComment(accessToken: String, commentId: Int): Response<Unit> {
        return commentsApiService.reportComment(accessToken, commentId)
    }

    override suspend fun updateComment(
        accessToken: String,
        commentId: Int,
        content: String
    ): Response<Unit> {
        return commentsApiService.updateComment(accessToken, commentId, content)
    }

    override suspend fun deleteComment(accessToken: String, commentId: Int): Response<Unit> {
        return commentsApiService.deleteComment(accessToken, commentId)
    }

    override suspend fun registerVote(
        accessToken: String,
        imageFile: MultipartBody.Part
    ): Response<Unit> {
        return voteApiService.registerVote(accessToken, imageFile)
    }

    override suspend fun getMyVotes(
        accessToken: String,
        memberId: String,
    ): MyVotesResponse {
        return memberApiService.getMyVotes(accessToken, memberId, null, 100)
    }

    override suspend fun getMyEvaluations(accessToken: String, memberId: String): MyEvaluations {
        return memberApiService.getMyEvaluations(accessToken, memberId, null, 100)
    }

    override suspend fun getMyBookMarks(accessToken: String, memberId: String): BookMarkResponse {
        return memberApiService.getMyBookmarks(accessToken, memberId, null, 100)
    }

    override suspend fun logout(accessToken: String, refreshToken: String): Response<Unit> {
        return certifiedApiService.logout(accessToken, refreshToken)
    }

    override suspend fun withdrawal(accessToken: String, memberId: String): Response<Unit> {
        return memberApiService.withdrawal(accessToken, memberId)
    }

    override suspend fun updateMyInfo(
        accessToken: String,
        memberId: String,
        updateMyInfoRequest: UpdateMyInfoRequest
    ): Response<Unit> {
        return memberApiService.updateMyInfo(accessToken, memberId, updateMyInfoRequest)
    }

    override suspend fun getBanList(
        accessToken: String,
        memberId: String,
    ): BanListResponse {
        return memberApiService.getBanList(accessToken, memberId, null, 100)
    }

    override suspend fun cancelUserBan(accessToken: String, memberId: String): Response<Unit> {
        return memberApiService.cancelUserBan(accessToken, memberId)
    }

    override suspend fun deleteVote(accessToken: String, voteId: String): Response<Unit> {
        return voteApiService.deleteVote(accessToken, voteId)
    }

    override suspend fun updateProfileImage(
        accessToken: String,
        memberId: String,
        imageFile: MultipartBody.Part
    ): Response<Unit> {
        return memberApiService.updateProfileImage(accessToken, memberId, imageFile)
    }

    override suspend fun saveProviderId(providerId: String) {
        val providerIdKey = stringPreferencesKey("providerId")
        dataStore.edit { settings ->
            settings[providerIdKey] = providerId
        }
    }

    override suspend fun saveAccessToken(accessToken: String) {
        val accessTokenKey = stringPreferencesKey("accessToken")
        dataStore.edit { settings ->
            settings[accessTokenKey] = accessToken
        }
    }

    override suspend fun saveRefreshToken(refreshToken: String) {
        val refreshTokenKey = stringPreferencesKey("refreshToken")
        dataStore.edit { settings ->
            settings[refreshTokenKey] = refreshToken
        }
    }

    override suspend fun saveMarketingInformationConsent(marketingInfo: Boolean) {
        val marketingInfoKey = booleanPreferencesKey("marketingInfo")
        dataStore.edit { settings ->
            settings[marketingInfoKey] = marketingInfo
        }
    }

    override suspend fun saveProfileImage(imageUri: Uri) {
        val imageUriKey = stringPreferencesKey("imageUri")
        dataStore.edit { settings ->
            settings[imageUriKey] = imageUri.toString()
        }
    }

    override suspend fun saveMyMemberId(memberId: Int) {
        val memberIdKey = intPreferencesKey("memberId")
        dataStore.edit { settings ->
            settings[memberIdKey] = memberId
        }
    }

    override suspend fun saveMyNickName(nickName: String) {
        val myNickNameKey = stringPreferencesKey("myNickName")
        dataStore.edit { settings ->
            settings[myNickNameKey] = nickName
        }
    }

    override suspend fun saveMyImageUrl(imageUrl: String) {
        val myImageUrlKey = stringPreferencesKey("myImageUrl")
        dataStore.edit { settings ->
            settings[myImageUrlKey] = imageUrl
        }
    }

    override suspend fun readProviderId(): Flow<String?> {
        val providerIdKey = stringPreferencesKey("providerId")
        return dataStore.data
            .map { preferences ->
                preferences[providerIdKey]
            }
    }

    override suspend fun readAccessToken(): Flow<String?> {
        val readAccessKey = stringPreferencesKey("accessToken")
        return dataStore.data
            .map { preferences ->
                preferences[readAccessKey]
            }
    }

    override suspend fun readRefreshToken(): Flow<String?> {
        val readRefreshKey = stringPreferencesKey("refreshToken")
        return dataStore.data
            .map { preferences ->
                preferences[readRefreshKey]
            }
    }

    override suspend fun readMarketingInformationConsent(): Flow<Boolean?> {
        val marketingInfoKey = booleanPreferencesKey("marketingInfo")
        return dataStore.data
            .map { preferences ->
                preferences[marketingInfoKey]
            }
    }

    override suspend fun readProfileImage(): Flow<String?> {
        val imageUriKey = stringPreferencesKey("imageUri")
        return dataStore.data
            .map { preferences ->
                preferences[imageUriKey]
            }
    }

    override suspend fun readMyMemberId(): Flow<Int?> {
        val memberIdKey = intPreferencesKey("memberId")
        return dataStore.data
            .map { preferences ->
                preferences[memberIdKey]
            }
    }

    override suspend fun readMyNickName(): Flow<String?> {
        val myNickNameKey = stringPreferencesKey("myNickName")
        return dataStore.data
            .map { preferences ->
                preferences[myNickNameKey]
            }
    }

    override suspend fun readMyImageUrl(): Flow<String?> {
        val myImageUrlKey = stringPreferencesKey("myImageUrl")
        return dataStore.data
            .map { preferences ->
                preferences[myImageUrlKey]
            }
    }

    override suspend fun clearAllDataStoreKey() {
        dataStore.edit { settings ->
            settings.clear()
        }
    }
}