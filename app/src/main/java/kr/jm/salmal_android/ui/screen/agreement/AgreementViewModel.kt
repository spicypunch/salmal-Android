package kr.jm.salmal_android.ui.screen.agreement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.jm.salmal_android.repository.RepositoryImpl
import javax.inject.Inject

@HiltViewModel
class AgreementViewModel @Inject constructor(
    private val repository: RepositoryImpl,
) : ViewModel() {
    fun saveMarketingInformationConsent(isCheckedMarketing: Boolean) {
        viewModelScope.launch {
            repository.saveMarketingInformationConsent(isCheckedMarketing)
        }
    }
}