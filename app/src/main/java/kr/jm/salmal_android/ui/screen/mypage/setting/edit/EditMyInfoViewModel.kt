package kr.jm.salmal_android.ui.screen.mypage.setting.edit

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kr.jm.salmal_android.data.request.UpdateMyInfoRequest
import kr.jm.salmal_android.data.response.UserInfoResponse
import kr.jm.salmal_android.repository.RepositoryImpl
import kr.jm.salmal_android.utils.LoadingHandler
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class EditMyInfoViewModel @Inject constructor(
    val repository: RepositoryImpl,
) : ViewModel(), LoadingHandler {

    private val _myInfo = MutableStateFlow<UserInfoResponse?>(null)
    val myInfo = _myInfo.asStateFlow()

    private val _logoutResult = MutableSharedFlow<Boolean>()
    val logoutResult = _logoutResult.asSharedFlow()

    private val _withdrawalResult = MutableSharedFlow<Boolean>()
    val withdrawalResult = _withdrawalResult.asSharedFlow()

    private val _updateMyInfoResult = MutableSharedFlow<Boolean>()
    val updateMyInfoResult = _updateMyInfoResult.asSharedFlow()

    private val _duplicateNickname = MutableSharedFlow<Boolean>()
    val duplicateNickname = _duplicateNickname.asSharedFlow()

    private val _updateProfileImageResult = MutableSharedFlow<Boolean>()
    val updateProfileImageResult = _updateProfileImageResult.asSharedFlow()

    override val isLoading = mutableStateOf(false)

    override fun showIndicator() {
        isLoading.value = true
    }

    override fun hideIndicator() {
        isLoading.value = false
    }

    fun getMyInfo() {
        viewModelScope.launch {
            try {
                val accessToken = "Bearer ${repository.readAccessToken().firstOrNull()}"
                val memberId = repository.readMyMemberId().firstOrNull()
                if (memberId != null) {
                    _myInfo.emit(repository.getUserInfo(accessToken, memberId))
                    _myInfo.value?.let {
                        repository.saveMyNickName(it.nickName)
                        repository.saveMyImageUrl(it.imageUrl)
                    }
                }
            } catch (e: HttpException) {
                Log.e("BaseViewModel", "getMyInfo: ${e.message}")
            }
        }
    }

    fun logout() {
        viewModelScope.launch  {
            try {
                val accessToken = "Bearer ${repository.readAccessToken().firstOrNull()}"
                val refreshToken = repository.readRefreshToken().firstOrNull()
                if (refreshToken != null) {
                    val response = repository.logout(accessToken, refreshToken)
                    if (response.isSuccessful) {
                        if (response.code() == 200) {
                            repository.clearAllDataStoreKey()
                            _logoutResult.emit(true)
                        }
                    }
                }
            } catch (e: HttpException) {
                Log.e("EditMyInfoViewModel", "logout: ${e.message}")
            }

        }
    }

    fun withdrawal() {
        viewModelScope.launch  {
            try {
                val accessToken = "Bearer ${repository.readAccessToken().firstOrNull()}"
                val memberId = repository.readMyMemberId().firstOrNull()
                val response = repository.withdrawal(accessToken, memberId.toString())
                if (response.isSuccessful) {
                    if (response.code() == 204) {
                        repository.clearAllDataStoreKey()
                        _withdrawalResult.emit(true)
                    }
                }
            } catch (e: HttpException) {
                Log.e("EditMyInfoViewModel", "withdrawal: ${e.message}")
            }
        }
    }

    fun updateMyInfo(updateMyInfoRequest: UpdateMyInfoRequest) {
        viewModelScope.launch  {
            try {
                val accessToken = "Bearer ${repository.readAccessToken().firstOrNull()}"
                val memberId = repository.readMyMemberId().firstOrNull()
                val response =
                    repository.updateMyInfo(accessToken, memberId.toString(), updateMyInfoRequest)
                if (response.isSuccessful) {
                    _updateMyInfoResult.emit(true)
                } else {
                    val errorBody = response.errorBody()?.string()
                    if (errorBody != null) {
                        val jsonObject = JsonParser.parseString(errorBody).asJsonObject
                        val errorCode = jsonObject.get("code").asInt
                        when (errorCode) {
                            1005 -> {
                                _duplicateNickname.emit(true)
                                repository.getUserInfo(accessToken, memberId!!)
                            }
                        }
                    }
                }

            } catch (e: HttpException) {
                Log.e("EditMyInfoViewModel", "updateMyInfo: ${e.message}")
            }
        }
    }

    fun updateProfileImage(imageInfo: ByteArray) {
        viewModelScope.launch  {
            showIndicator()
            try {
                val accessToken = "Bearer ${repository.readAccessToken().firstOrNull()}"
                val memberId = repository.readMyMemberId().firstOrNull()

                val requestFile =
                    imageInfo.toRequestBody("image/jpeg".toMediaTypeOrNull())
                val imageFile =
                    MultipartBody.Part.createFormData("imageFile", "image.jpeg", requestFile)

                val response = repository.updateProfileImage(
                    accessToken,
                    memberId.toString(),
                    imageFile
                )
                if (response.isSuccessful) {
                    if (response.code() == 200) {
                        _updateProfileImageResult.emit(true)
                        repository.getUserInfo(accessToken, memberId!!)
                    }
                }
            } catch (e: HttpException) {
                Log.e("EditMyInfoViewModel", "updateProfileImage: ${e.message}")
            } finally {
                hideIndicator()
            }
        }
    }
}