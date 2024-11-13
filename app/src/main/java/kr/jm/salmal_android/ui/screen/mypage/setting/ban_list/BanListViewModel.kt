package kr.jm.salmal_android.ui.screen.mypage.setting.ban_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kr.jm.salmal_android.data.response.BanListResponse
import kr.jm.salmal_android.repository.RepositoryImpl
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class BanListViewModel @Inject constructor(
    val repository: RepositoryImpl,
) : ViewModel() {

    private val _banList = MutableStateFlow<BanListResponse?>(null)
    val banList = _banList.asStateFlow()
    fun getBanList() {
        viewModelScope.launch  {
            try {
                val accessToken = "Bearer ${repository.readAccessToken().firstOrNull()}"
                val memberId = repository.readMyMemberId().firstOrNull()
                _banList.emit(repository.getBanList(accessToken, memberId.toString()))
            } catch (e: HttpException) {
                Log.e("BanListViewModel", "getBanList: ${e.message}")
            }
        }
    }

    fun cancelUserBan(memberId: String) {
        viewModelScope.launch  {
            try {
                val accessToken = "Bearer ${repository.readAccessToken().firstOrNull()}"
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