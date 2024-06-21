package kr.jm.salmal_android

import android.net.Uri
import android.util.Base64
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kr.jm.salmal_android.repository.RepositoryImpl
import org.json.JSONObject
import retrofit2.HttpException

open class BaseViewModel() : ViewModel() {

    open lateinit var repository: RepositoryImpl
    open lateinit var dataStore: DataStore<Preferences>

    private var _userBanSuccess = MutableSharedFlow<Boolean>()
    val userBanSuccess = _userBanSuccess.asSharedFlow()

    private var _voteReportSuccess = MutableSharedFlow<Boolean>()
    val voteReportSuccess = _voteReportSuccess.asSharedFlow()

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

    fun saveMyMemberId(memberId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val memberIdKey = intPreferencesKey("memberId")
            dataStore.edit { settings ->
                settings[memberIdKey] = memberId
            }
        }
    }

    fun saveMyNickName(nickName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val myNickNameKey = stringPreferencesKey("myNickName")
            dataStore.edit { settings ->
                settings[myNickNameKey] = nickName
            }
        }
    }

    fun saveMyImageUrl(imageUrl: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val myImageUrlKey = stringPreferencesKey("myImageUrl")
            dataStore.edit { settings ->
                settings[myImageUrlKey] = imageUrl
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

    fun readMyMemberId(): Flow<Int?> {
        val memberIdKey = intPreferencesKey("memberId")
        return dataStore.data
            .map { preferences ->
                preferences[memberIdKey]
            }
    }

    fun readMyNickName(): Flow<String?> {
        val myNickNameKey = stringPreferencesKey("myNickName")
        return dataStore.data
            .map { preferences ->
                preferences[myNickNameKey]
            }
    }

    fun readMyImageUrl(): Flow<String?> {
        val myImageUrlKey = stringPreferencesKey("myImageUrl")
        return dataStore.data
            .map { preferences ->
                preferences[myImageUrlKey]
            }
    }

    fun voteReport(voteId: String, reason: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val accessToken = "Bearer ${readAccessToken().firstOrNull()}"
                val response = repository.voteReport(accessToken, voteId, reason)
                if (response.isSuccessful) {
                    if (response.code() == 201) {
                        _voteReportSuccess.emit(true)
                    }
                } else {
                    Log.e("VoteViewModel", "voteReport: Response was not successful")
                }
            } catch (e: HttpException) {
                Log.e("VoteViewModel", "voteReport: ${e.message}")
            } catch (e: Exception) {
                Log.e("VoteViewModel", "voteReport: ${e.message}")
            }
        }
    }

    fun userBan(memberId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val accessToken = "Bearer ${readAccessToken().firstOrNull()}"
                val response = repository.userBan(accessToken, memberId)
                if (response.isSuccessful) {
                    if (response.code() == 201) {
                        _userBanSuccess.emit(true)
                    }
                } else {
                    Log.e("VoteViewModel", "userBan: Response was not successful")
                }
            } catch (e: HttpException) {
                Log.e("VoteViewModel", "userBan: ${e.message}")
            } catch (e: Exception) {
                Log.e("VoteViewModel", "userBan: ${e.message}")
            }
        }
    }

    fun showIndicator() {
        isLoading.value = true
    }

    fun hideIndicator() {
        isLoading.value = false
    }

    /**
     * accessToken에 담겨져 있는 memberId 값을 가져오는 함수
     */
    fun parseID(jwtToken: String): Int? {

        val value = jwtToken.split(".")[1]

        val replacedValue = value
            .replace("-", "+")
            .replace("_", "/")

        val finalValue = replacedValue.padEnd((replacedValue.length + 3) / 4 * 4, '=')

        val data = try {
            Base64.decode(finalValue, Base64.DEFAULT)
        } catch (e: IllegalArgumentException) {
            return null
        }

        val payload = try {
            JSONObject(String(data))
        } catch (e: Exception) {
            return null
        }

        return payload.optInt("id", -1).takeIf { it != -1 }
    }

}