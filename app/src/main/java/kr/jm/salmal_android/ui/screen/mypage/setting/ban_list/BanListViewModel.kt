package kr.jm.salmal_android.ui.screen.mypage.setting.ban_list

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kr.jm.salmal_android.BaseViewModel
import kr.jm.salmal_android.data.response.BanListResponse
import kr.jm.salmal_android.repository.RepositoryImpl
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class BanListViewModel @Inject constructor(
    override var repository: RepositoryImpl,
    override var dataStore: DataStore<Preferences>
) : BaseViewModel() {

    private val _banList = MutableStateFlow<BanListResponse?>(null)
    val banList = _banList.asStateFlow()
    fun getBanList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val accessToken = "Bearer ${readAccessToken().firstOrNull()}"
                val memberId = readMyMemberId().firstOrNull()
                _banList.emit(repository.getBanList(accessToken, memberId.toString()))
            } catch (e: HttpException) {
                Log.e("BanListViewModel", "getBanList: ${e.message}")
            }
        }
    }

    fun cancelUserBan(memberId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val accessToken = "Bearer ${readAccessToken().firstOrNull()}"
                val response = repository.cancelUserBan(accessToken, memberId)
                if (response.isSuccessful) {
                    getBanList()
                }
            } catch (e: HttpException) {
                Log.e("BanListViewModel", "cancelUserBan: ${e.message}")
            }
        }
    }
}