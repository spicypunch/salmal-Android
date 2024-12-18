package kr.jm.salmal_android.ui.screen.login

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonParser
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kr.jm.salmal_android.data.request.LoginRequest
import kr.jm.salmal_android.repository.RepositoryImpl
import kr.jm.salmal_android.utils.LoadingHandler
import kr.jm.salmal_android.utils.Utils.parseID
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val repository: RepositoryImpl,
) : ViewModel(), LoadingHandler {

    private var _isMember = MutableSharedFlow<Boolean>()
    val isMember = _isMember.asSharedFlow()

    override val isLoading = mutableStateOf(false)

    override fun showIndicator() {
        isLoading.value = true
    }

    override fun hideIndicator() {
        isLoading.value = false
    }

    fun requestKakaoToken(context: Context) {
        showIndicator()
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e("Kakao", "카카오 웹 로그인 실패", error)
            } else if (token != null) {
                /**
                 * 카카오 웹 로그인 성공 시 사용자 정보 요청
                 */
                requestKakaoMyInfo()
            }
        }

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    Log.e("Kakao", "카카오톡으로 로그인 실패", error)

                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                } else if (token != null) {
                    /**
                     * 카카오 앱 로그인 성공 시 사용자 정보 요청
                     */
                    requestKakaoMyInfo()

                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }

    private fun requestKakaoMyInfo() {

            UserApiClient.instance.me { user, error ->
                if (error != null) {
                    Log.e("Kakao", "사용자 정보 요청 실패", error)
                } else if (user != null) {
                    viewModelScope.launch {
                        repository.saveProviderId(user.id!!.toString())
                        login(user.id!!.toString())
                    }
                }
            }
    }

    private fun login(providerId: String) {
        viewModelScope.launch {
            try {
                val tokenInfo = repository.login(LoginRequest(providerId = providerId))
                repository.saveAccessToken(tokenInfo.accessToken)
                repository.saveRefreshToken(tokenInfo.refreshToken)
                tokenInfo.accessToken.parseID()?.let { repository.saveMyMemberId(it) }
                _isMember.emit(true)
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                if (errorBody != null) {
                    val jsonObject = JsonParser.parseString(errorBody).asJsonObject
                    val errorCode = jsonObject.get("code").asInt
                    when (errorCode) {
                        1001 -> {
                            _isMember.emit(false)
                        }
                    }
                }
            } finally {
                hideIndicator()
            }
        }
    }


}