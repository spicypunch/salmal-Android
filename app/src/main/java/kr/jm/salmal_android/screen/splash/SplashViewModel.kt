package kr.jm.salmal_android.screen.splash

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kr.jm.salmal_android.data.request.LoginRequest
import kr.jm.salmal_android.repository.RepositoryImpl
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: RepositoryImpl,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    private val _loginResult = MutableSharedFlow<Boolean>()
    val loginResult = _loginResult.asSharedFlow()
    fun attemptLogin() {
        viewModelScope.launch {
            val providerId = readProviderId().firstOrNull()
            if (providerId != null) {
                try {
                    val tokenInfo = repository.login(LoginRequest(providerId))
                    saveAccessToken(tokenInfo.accessToken)
                    saveRefreshToken(tokenInfo.refreshToken)
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
    private fun readProviderId(): Flow<String?> {
        val providerIdKey = stringPreferencesKey("providerId")
        return dataStore.data
            .map { preferences ->
                preferences[providerIdKey]
            }
    }

    private fun saveAccessToken(accessToken: String) {
        viewModelScope.launch {
            val accessTokenKey = stringPreferencesKey("accessToken")
            dataStore.edit { settings ->
                settings[accessTokenKey] = accessToken
            }
        }
    }

    private fun saveRefreshToken(refreshToken: String) {
        viewModelScope.launch {
            val refreshTokenKey = stringPreferencesKey("refreshToken")
            dataStore.edit { settings ->
                settings[refreshTokenKey] = refreshToken
            }
        }
    }
}