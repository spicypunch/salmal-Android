package kr.jm.salmal_android.ui.screen.mypage.setting.delete

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kr.jm.salmal_android.data.response.MyVotesResponse
import kr.jm.salmal_android.repository.RepositoryImpl
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class DeleteMyVoteViewModel @Inject constructor(
    val repository: RepositoryImpl,
) : ViewModel() {

    private val _myVotes = MutableStateFlow<MyVotesResponse?>(null)
    val myVotes = _myVotes.asStateFlow()

    fun getMyVotes() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val accessToken = "Bearer ${repository.readAccessToken().firstOrNull()}"
                val memberId = repository.readMyMemberId().firstOrNull()
                if (memberId != null) {
                    _myVotes.emit(repository.getMyVotes(accessToken, memberId.toString()))
                }
            } catch (e: HttpException) {
                Log.e("MyPageViewModel", "getMyVotes: ${e.message}")
            }
        }
    }

    fun deleteVote(voteId: String) {
        viewModelScope.launch {
            try {
                val accessToken = "Bearer ${repository.readAccessToken().firstOrNull()}"
                val memberId = repository.readMyMemberId().firstOrNull()
                val response = repository.deleteVote(accessToken, voteId)
                if (response.isSuccessful) {
                    repository.getMyVotes(accessToken, memberId.toString())
                }
            } catch (e: HttpException) {
                Log.e("DeleteMyItemViewModel", "deleteVote: ${e.message}")
            }
        }
    }
}