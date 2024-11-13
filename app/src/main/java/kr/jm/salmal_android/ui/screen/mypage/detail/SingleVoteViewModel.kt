package kr.jm.salmal_android.ui.screen.mypage.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kr.jm.salmal_android.data.response.VotesListResponse
import kr.jm.salmal_android.repository.RepositoryImpl
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SingleVoteViewModel @Inject constructor(
    val repository: RepositoryImpl,
) : ViewModel() {

    private val _voteDetail = MutableStateFlow<VotesListResponse.Vote?>(null)
    val voteDetail = _voteDetail.asStateFlow()

    private var _userBanSuccess = MutableSharedFlow<Boolean>()
    val userBanSuccess = _userBanSuccess.asSharedFlow()

    private var _voteReportSuccess = MutableSharedFlow<Boolean>()
    val voteReportSuccess = _voteReportSuccess.asSharedFlow()

    private var _myMemberId = MutableStateFlow<Int?>(null)
    val myMemberId = _myMemberId.asStateFlow()

    fun readMyMemberId() {
        viewModelScope.launch {
            _myMemberId.value = repository.readMyMemberId().firstOrNull()
        }
    }

    fun getDetailVote(
        voteId: String
    ) {
        viewModelScope.launch  {
            try {
                val accessToken = "Bearer ${repository.readAccessToken().firstOrNull()}"
                _voteDetail.emit(repository.getVote(accessToken, voteId))
            } catch (e: HttpException) {
                Log.e("MyPageViewModel", "getDetailVote: ${e.message}")
            }
        }
    }

    fun addBookmark(
        voteId: String,
    ) {
        viewModelScope.launch  {
            try {
                val accessToken = "Bearer ${repository.readAccessToken().firstOrNull()}"
                val response = repository.addBookmark(accessToken, voteId)
                if (response.isSuccessful) {
                    if (response.code() == 201) {
                        getDetailVote(voteId)
                    }
                } else {
                    Log.e("SingleVoteViewModel", "addBookmark: Response was not successful")
                }
            } catch (e: HttpException) {
                Log.e("SingleVoteViewModel", "addBookmark: ${e.message}")
            } catch (e: Exception) {
                Log.e("SingleVoteViewModel", "addBookmark: ${e.message}")
            }
        }
    }

    fun deleteBookmark(
        voteId: String,
    ) {
        viewModelScope.launch  {
            try {
                val accessToken = "Bearer ${repository.readAccessToken().firstOrNull()}"
                val response = repository.deleteBookmark(accessToken, voteId)
                if (response.isSuccessful) {
                    if (response.code() == 204) {
                        getDetailVote(voteId)
                    }
                } else {
                    Log.e("SingleVoteViewModel", "deleteBookmark: Response was not successful")
                }
            } catch (e: HttpException) {
                Log.e("SingleVoteViewModel", "deleteBookmark: ${e.message}")
            } catch (e: Exception) {
                Log.e("SingleVoteViewModel", "deleteBookmark: ${e.message}")
            }
        }
    }

    fun voteEvaluation(
        voteId: String,
        voteEvaluationType: String
    ) {
        viewModelScope.launch  {
            try {
                val accessToken = "Bearer ${repository.readAccessToken().firstOrNull()}"
                val response = repository.voteEvaluation(accessToken, voteId, voteEvaluationType)
                if (response.isSuccessful) {
                    if (response.code() == 201) {
                        getDetailVote(voteId)
                    }
                } else {
                    Log.e("SingleVoteViewModel", "voteEvaluation: Response was not successful")
                }
            } catch (e: HttpException) {
                Log.e("SingleVoteViewModel", "voteEvaluation: ${e.message}")
            } catch (e: Exception) {
                Log.e("SingleVoteViewModel", "voteEvaluation: ${e.message}")
            }
        }
    }

    fun voteEvaluationDelete(
        voteId: String,
    ) {
        viewModelScope.launch  {
            try {
                val accessToken = "Bearer ${repository.readAccessToken().firstOrNull()}"
                val response = repository.voteEvaluationDelete(accessToken, voteId)
                if (response.isSuccessful) {
                    if (response.code() == 204) {
                        getDetailVote(voteId)
                    }
                } else {
                    Log.e("SingleVoteViewModel", "voteEvaluationDelete: Response was not successful")
                }

            } catch (e: HttpException) {
                Log.e("SingleVoteViewModel", "voteEvaluationDelete: ${e.message}")
            } catch (e: Exception) {
                Log.e("SingleVoteViewModel", "voteEvaluationDelete: ${e.message}")
            }
        }
    }

    fun voteReport(voteId: String, reason: String) {
        viewModelScope.launch {
            try {
                val accessToken = "Bearer ${repository.readAccessToken().firstOrNull()}"
                val response = repository.voteReport(accessToken, voteId, reason)
                if (response.isSuccessful) {
                    if (response.code() == 201) {
                        _voteReportSuccess.emit(true)
                    }
                } else {
                    Log.e("BaseViewModel", "voteReport: Response was not successful")
                }
            } catch (e: HttpException) {
                Log.e("BaseViewModel", "voteReport: ${e.message}")
            } catch (e: Exception) {
                Log.e("BaseViewModel", "voteReport: ${e.message}")
            }
        }
    }

    fun userBan(memberId: String) {
        viewModelScope.launch {
            try {
                val accessToken = "Bearer ${repository.readAccessToken().firstOrNull()}"
                val response = repository.userBan(accessToken, memberId)
                if (response.isSuccessful) {
                    if (response.code() == 201) {
                        _userBanSuccess.emit(true)
                    }
                } else {
                    Log.e("BaseViewModel", "userBan: Response was not successful")
                }
            } catch (e: HttpException) {
                Log.e("BaseViewModel", "userBan: ${e.message}")
            } catch (e: Exception) {
                Log.e("BaseViewModel", "userBan: ${e.message}")
            }
        }
    }
}