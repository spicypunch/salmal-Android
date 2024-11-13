package kr.jm.salmal_android.ui.screen.profile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kr.jm.salmal_android.data.request.SignUpRequest
import kr.jm.salmal_android.repository.RepositoryImpl
import kr.jm.salmal_android.utils.LoadingHandler
import kr.jm.salmal_android.utils.Utils.parseID
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SetProfileViewModel @Inject constructor(
     val repository: RepositoryImpl,
) : ViewModel(), LoadingHandler {

    private val _signUpSuccess = MutableSharedFlow<Boolean>()
    val signUpSuccess = _signUpSuccess.asSharedFlow()

    override val isLoading = mutableStateOf(false)

    override fun showIndicator() {
        isLoading.value = true
    }

    override fun hideIndicator() {
        isLoading.value = false
    }

    fun signUp(nickName: String) {
        viewModelScope.launch  {
            showIndicator()
            val providerId: String? = repository.readProviderId().firstOrNull()
            val marketingInformationConsent: Boolean? =
                repository.readMarketingInformationConsent().firstOrNull()
            if (providerId != null && marketingInformationConsent != null) {
                try {
                    val signUpResponse = repository.signUp(
                        SignUpRequest(
                            providerId = providerId,
                            marketingInformationConsent = marketingInformationConsent,
                            nickName = nickName
                        )
                    )
                    repository.saveAccessToken(signUpResponse.accessToken)
                    repository.saveRefreshToken(signUpResponse.refreshToken)
                    signUpResponse.accessToken.parseID()?.let { repository.saveMyMemberId(it) }
                    _signUpSuccess.emit(true)
                } catch (e: HttpException) {
                    val errorBody = e.response()?.errorBody()?.string()
                    if (errorBody != null) {
                        val jsonObject = JsonParser.parseString(errorBody).asJsonObject
                        val errorCode = jsonObject.get("code").asInt
                        when (errorCode) {
                            1005 -> {
                                hideIndicator()
                                _signUpSuccess.emit(false)
                            }
                        }
                    }
                }
            }
        }
    }
}