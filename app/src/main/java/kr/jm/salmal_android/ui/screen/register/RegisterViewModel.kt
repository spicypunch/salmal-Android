package kr.jm.salmal_android.ui.screen.register

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kr.jm.salmal_android.BaseViewModel
import kr.jm.salmal_android.repository.RepositoryImpl
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
    private val repository: RepositoryImpl,
    override var dataStore: DataStore<Preferences>
) : BaseViewModel() {
}