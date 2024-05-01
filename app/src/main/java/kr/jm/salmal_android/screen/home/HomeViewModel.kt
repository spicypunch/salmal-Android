package kr.jm.salmal_android.screen.home

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kr.jm.salmal_android.repository.RepositoryImpl
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: RepositoryImpl,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    fun test() {
        viewModelScope.launch {
            val proviederid: String? = readProviderId().firstOrNull()
            val marketingInformationConsent: Boolean? =
                readMarketingInformationConsent().firstOrNull()
            val accessToken: String? = readAccessToken().firstOrNull()
            val refreshToken: String? = readRefreshToken().firstOrNull()
            val image: Uri? = readProfileImage().firstOrNull()?.toUri()
            Log.e("proviederid", "$proviederid")
            Log.e("marketingInformationConsent", "$marketingInformationConsent")
            Log.e("accessToken", "$accessToken")
            Log.e("refreshToken", "$refreshToken")
            Log.e("image", "$image")

        }
    }

    private fun readProviderId(): Flow<String?> {
        val providerIdKey = stringPreferencesKey("providerId")
        return dataStore.data
            .map { preferences ->
                preferences[providerIdKey]
            }
    }

    private fun readMarketingInformationConsent(): Flow<Boolean?> {
        val marketingInfoKey = booleanPreferencesKey("marketingInfo")
        return dataStore.data
            .map { preferences ->
                preferences[marketingInfoKey]
            }
    }

    private fun readAccessToken(): Flow<String?> {
        val accessTokenKey = stringPreferencesKey("accessToken")
        return dataStore.data
            .map { preferences ->
                preferences[accessTokenKey]
            }
    }

    private fun readRefreshToken() = dataStore.data
        .map { preferences ->
            preferences[stringPreferencesKey("refreshToken")]
        }

    private fun readProfileImage() = dataStore.data
        .map { preferences ->
            preferences[stringPreferencesKey("imageUri")]
        }

}