package kr.jm.salmal_android.ui.screen.splash

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
import kr.jm.salmal_android.data.request.LoginRequest
import kr.jm.salmal_android.repository.RepositoryImpl
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    override var repository: RepositoryImpl,
    override var dataStore: DataStore<Preferences>
) : BaseViewModel() {

    private val _loginResult = MutableSharedFlow<Boolean>()
    val loginResult = _loginResult.asSharedFlow()
    fun attemptLogin() {
        viewModelScope.launch(Dispatchers.IO) {
            val providerId = readProviderId().firstOrNull()
            if (providerId != null) {
                try {
                    val tokenInfo = repository.login(LoginRequest(providerId))
                    saveAccessToken(tokenInfo.accessToken)
                    saveRefreshToken(tokenInfo.refreshToken)
                    parseID(tokenInfo.accessToken)?.let { saveMyMemberId(it) }
                    _loginResult.emit(true)
                } catch (e: HttpException) {
                    val errorBody = e.response()?.errorBody()?.string()
                    if (errorBody != null) {
                        val jsonObject = JsonParser.parseString(errorBody).asJsonObject
                        val errorCode = jsonObject.get("code").asInt
                        when (errorCode) {
                            1001 -> {
                                _loginResult.emit(false)
                            }
                        }
                    }
                }
            } else {
                _loginResult.emit(false)
            }
        }
    }
}