package kr.jm.salmal_android.ui.screen.agreement

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AgreementViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    fun saveMarketingInformationConsent(marketingInfo: Boolean) {
        viewModelScope.launch {
            val marketingInfoKey = booleanPreferencesKey("marketingInfo")
            dataStore.edit { settings ->
                settings[marketingInfoKey] = marketingInfo
            }
        }
    }
}