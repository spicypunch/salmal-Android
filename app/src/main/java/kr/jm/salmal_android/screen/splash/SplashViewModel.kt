package kr.jm.salmal_android.screen.splash

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kr.jm.salmal_android.data.request.LoginRequest
import kr.jm.salmal_android.repository.RepositoryImpl
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: RepositoryImpl,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    fun attemptLogin() {
        viewModelScope.launch {
            val providerId = readProviderId().firstOrNull()
            if (providerId != null) {
                repository.login(LoginRequest(providerId))
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

    private fun readAccessToken(): Flow<String?> {
        val accessTokenKey = stringPreferencesKey("accessToken")
        return dataStore.data
            .map { preferences ->
                preferences[accessTokenKey]
            }
    }
}