package kr.jm.salmal_android.ui.screen.agreement

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.jm.salmal_android.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class AgreementViewModel @Inject constructor(
    override var dataStore: DataStore<Preferences>
) : BaseViewModel() {

}