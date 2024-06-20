package kr.jm.salmal_android.ui.screen.mypage

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
import kr.jm.salmal_android.data.response.MyEvaluations
import kr.jm.salmal_android.data.response.MyVotesResponse
import kr.jm.salmal_android.data.response.UserInfoResponse
import kr.jm.salmal_android.data.response.VotesListResponse
import kr.jm.salmal_android.repository.RepositoryImpl
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val repository: RepositoryImpl,
    override var dataStore: DataStore<Preferences>
) : BaseViewModel() {

    private val _myInfo = MutableStateFlow<UserInfoResponse?>(null)
    val myInfo = _myInfo.asStateFlow()

    private val _myVotes = MutableStateFlow<MyVotesResponse?>(null)
    val myVotes = _myVotes.asStateFlow()

    private val _myEvaluations = MutableStateFlow<MyEvaluations?>(null)
    val myEvaluations = _myEvaluations.asStateFlow()

    private val _voteDetail = MutableStateFlow<VotesListResponse.Vote?>(null)
    val voteDetail = _voteDetail.asStateFlow()

    fun getMyInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val accessToken = "Bearer ${readAccessToken().firstOrNull()}"
                val memberId = readMyMemberId().firstOrNull()
                if (memberId != null) {
                    _myInfo.emit(repository.getUserInfo(accessToken, memberId))
                }
            } catch (e: HttpException) {
                Log.e("MyPageViewModel", "getMyInfo: ${e.message}")
            }
        }
    }

    fun getMyVotes() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val accessToken = "Bearer ${readAccessToken().firstOrNull()}"
                val memberId = readMyMemberId().firstOrNull()
                if (memberId != null) {
                    _myVotes.emit(repository.getMyVotes(accessToken, memberId.toString()))
                }
            } catch (e: HttpException) {
                Log.e("MyPageViewModel", "getMyVotes: ${e.message}")
            }
        }
    }

    fun getMyEvaluations() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val accessToken = "Bearer ${readAccessToken().firstOrNull()}"
                val memberId = readMyMemberId().firstOrNull()
                if (memberId != null) {
                    _myEvaluations.emit(
                        repository.getMyEvaluations(
                            accessToken,
                            memberId.toString()
                        )
                    )
                }
            } catch (e: HttpException) {
                Log.e("MyPageViewModel", "getMyVotes: ${e.message}")
            }
        }
    }
}