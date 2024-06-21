package kr.jm.salmal_android.ui.screen.home

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kr.jm.salmal_android.BaseViewModel
import kr.jm.salmal_android.repository.RepositoryImpl
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    override var repository: RepositoryImpl,
    override var dataStore: DataStore<Preferences>
) : BaseViewModel() {

    fun getMyInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val accessToken = "Bearer ${readAccessToken().firstOrNull()}"
                val memberId = readMyMemberId().firstOrNull()
                if (memberId != null) {
                    val myInfo = repository.getUserInfo(accessToken, memberId)
                    saveMyNickName(myInfo.nickName)
                    saveMyImageUrl(myInfo.imageUrl)
                }
            } catch (e: HttpException) {
                Log.e("HomeViewModel", "getMyInfo: ${e.message}")
            }
        }
    }
}