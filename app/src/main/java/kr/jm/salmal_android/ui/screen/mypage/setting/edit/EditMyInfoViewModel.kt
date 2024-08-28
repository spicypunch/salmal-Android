package kr.jm.salmal_android.ui.screen.mypage.setting.edit

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kr.jm.salmal_android.BaseViewModel
import kr.jm.salmal_android.data.request.UpdateMyInfoRequest
import kr.jm.salmal_android.repository.RepositoryImpl
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class EditMyInfoViewModel @Inject constructor(
    override var repository: RepositoryImpl,
    override var dataStore: DataStore<Preferences>
) : BaseViewModel() {

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

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val accessToken = "Bearer ${readAccessToken().firstOrNull()}"
                val refreshToken = readRefreshToken().firstOrNull()
                if (refreshToken != null) {
                    val response = repository.logout(accessToken, refreshToken)
                    if (response.isSuccessful) {
                        if (response.code() == 200) {
                            clearAllDataStoreKey()
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
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val accessToken = "Bearer ${readAccessToken().firstOrNull()}"
                val memberId = readMyMemberId().firstOrNull()
                val response = repository.withdrawal(accessToken, memberId.toString())
                if (response.isSuccessful) {
                    if (response.code() == 204) {
                        clearAllDataStoreKey()
                        _withdrawalResult.emit(true)
                    }
                }
            } catch (e: HttpException) {
                Log.e("EditMyInfoViewModel", "withdrawal: ${e.message}")
            }
        }
    }

    fun updateMyInfo(updateMyInfoRequest: UpdateMyInfoRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val accessToken = "Bearer ${readAccessToken().firstOrNull()}"
                val memberId = readMyMemberId().firstOrNull()
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
                                getMyInfo()
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
        viewModelScope.launch(Dispatchers.IO) {
            showIndicator()
            try {
                val accessToken = "Bearer ${readAccessToken().firstOrNull()}"
                val memberId = readMyMemberId().firstOrNull()

                val requestFile =
                    imageInfo.toRequestBody("image/jpeg".toMediaTypeOrNull())
                val imageFile = MultipartBody.Part.createFormData("imageFile", "image.jpeg", requestFile)

                val response = repository.updateProfileImage(
                    accessToken,
                    memberId.toString(),
                    imageFile
                )
                if (response.isSuccessful) {
                    if (response.code() == 200) {
                        _updateProfileImageResult.emit(true)
                        getMyInfo()
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