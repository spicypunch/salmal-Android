package kr.jm.salmal_android.ui.screen.profile

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kr.jm.salmal_android.BaseViewModel
import kr.jm.salmal_android.data.request.SignUpRequest
import kr.jm.salmal_android.data.response.SignUpResponse
import kr.jm.salmal_android.repository.RepositoryImpl
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SetProfileViewModel @Inject constructor(
    private val repository: RepositoryImpl,
    override var dataStore: DataStore<Preferences>
) : BaseViewModel() {

    private val _signUpSuccess = MutableSharedFlow<Boolean>()
    val signUpSuccess = _signUpSuccess.asSharedFlow()

    fun signUp(nickName:String) {
        viewModelScope.launch(Dispatchers.IO) {
            showIndicator()
            val providerId: String? = readProviderId().firstOrNull()
            val marketingInformationConsent: Boolean? = readMarketingInformationConsent().firstOrNull()
            if (providerId != null && marketingInformationConsent != null) {
                try {
                    val signUpResponse = repository.signUp(SignUpRequest(providerId = providerId, marketingInformationConsent = marketingInformationConsent, nickName = nickName))
                    saveAccessToken(signUpResponse.accessToken)
                    saveRefreshToken(signUpResponse.refreshToken)
                    _signUpSuccess.emit(true)
                } catch (e: HttpException) {
                    val errorBody = e.response()?.errorBody()?.string()
                    if (errorBody != null) {
                        val jsonObject = JsonParser.parseString(errorBody).asJsonObject
                        val errorCode = jsonObject.get("code").asInt
                        when(errorCode) {
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