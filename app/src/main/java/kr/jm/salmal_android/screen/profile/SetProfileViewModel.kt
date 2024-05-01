package kr.jm.salmal_android.screen.profile

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kr.jm.salmal_android.data.request.SignUpRequest
import kr.jm.salmal_android.repository.RepositoryImpl
import javax.inject.Inject

class SetProfileViewModel @Inject constructor(
    private val repository: RepositoryImpl,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    fun signUp(nickName:String) {
        viewModelScope.launch {
            val providerId: String? = readProviderId().firstOrNull()
            val marketingInformationConsent: Boolean? = readMarketingInformationConsent().firstOrNull()
            if (providerId != null && marketingInformationConsent != null) {
                repository.signUp(SignUpRequest(providerId = providerId, marketingInformationConsent = marketingInformationConsent, nickName = nickName))
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

    private fun readMarketingInformationConsent(): Flow<Boolean?> {
        val marketingInfoKey = booleanPreferencesKey("marketingInfo")
        return dataStore.data
            .map { preferences ->
                preferences[marketingInfoKey]
            }
    }
}