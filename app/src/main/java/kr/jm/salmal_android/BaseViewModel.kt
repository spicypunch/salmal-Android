package kr.jm.salmal_android

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

open class BaseViewModel() : ViewModel() {

    open lateinit var dataStore: DataStore<Preferences>

    val isLoading = mutableStateOf(false)

    fun saveProviderId(providerId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val providerIdKey = stringPreferencesKey("providerId")
            dataStore.edit { settings ->
                settings[providerIdKey] = providerId
            }
        }
    }

    fun saveAccessToken(accessToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val accessTokenKey = stringPreferencesKey("accessToken")
            dataStore.edit { settings ->
                settings[accessTokenKey] = accessToken
            }
        }
    }

    fun saveRefreshToken(refreshToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val refreshTokenKey = stringPreferencesKey("refreshToken")
            dataStore.edit { settings ->
                settings[refreshTokenKey] = refreshToken
            }
        }
    }

    fun saveMarketingInformationConsent(marketingInfo: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val marketingInfoKey = booleanPreferencesKey("marketingInfo")
            dataStore.edit { settings ->
                settings[marketingInfoKey] = marketingInfo
            }
        }
    }

    fun saveProfileImage(imageUri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            val imageUriKey = stringPreferencesKey("imageUri")
            dataStore.edit { settings ->
                settings[imageUriKey] = imageUri.toString()
            }
        }
    }

    fun readProviderId(): Flow<String?> {
        val providerIdKey = stringPreferencesKey("providerId")
        return dataStore.data
            .map { preferences ->
                preferences[providerIdKey]
            }
    }

    fun readAccessToken(): Flow<String?> {
        val readAccessKey = stringPreferencesKey("accessToken")
        return dataStore.data
            .map { preferences ->
                preferences[readAccessKey]
            }
    }

    fun readRefreshToken(): Flow<String?> {
        val readRefreshKey = stringPreferencesKey("refreshToken")
        return dataStore.data
            .map { preferences ->
                preferences[readRefreshKey]
            }
    }

    fun readMarketingInformationConsent(): Flow<Boolean?> {
        val marketingInfoKey = booleanPreferencesKey("marketingInfo")
        return dataStore.data
            .map { preferences ->
                preferences[marketingInfoKey]
            }
    }

    fun readProfileImage(): Flow<String?> {
        val imageUriKey = stringPreferencesKey("imageUri")
        return dataStore.data
            .map { preferences ->
                preferences[imageUriKey]
            }
    }

    fun showIndicator() {
        isLoading.value = true
    }

    fun hideIndicator() {
        isLoading.value = false
    }

}