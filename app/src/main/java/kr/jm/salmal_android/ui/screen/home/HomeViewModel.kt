package kr.jm.salmal_android.ui.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kr.jm.salmal_android.data.response.UserInfoResponse
import kr.jm.salmal_android.data.response.VotesListResponse
import kr.jm.salmal_android.repository.RepositoryImpl
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: RepositoryImpl,
) : ViewModel() {

    private val _votesList = MutableStateFlow<VotesListResponse?>(null)
    val votesList = _votesList.asStateFlow()

    private var _userBanSuccess = MutableSharedFlow<Boolean>()
    val userBanSuccess = _userBanSuccess.asSharedFlow()

    private var _voteReportSuccess = MutableSharedFlow<Boolean>()
    val voteReportSuccess = _voteReportSuccess.asSharedFlow()

    private val _myInfo = MutableStateFlow<UserInfoResponse?>(null)
    val myInfo = _myInfo.asStateFlow()

    private var _myMemberId = MutableStateFlow<Int?>(null)
    val myMemberId = _myMemberId.asStateFlow()

    private var currentSearchType = ""

    init {
        readMyMemberId()
    }

    fun readMyMemberId() {
        viewModelScope.launch {
            _myMemberId.value = repository.readMyMemberId().firstOrNull()
        }
    }

    fun getMyInfo() {
        viewModelScope.launch {
            try {
                val accessToken = "Bearer ${repository.readAccessToken().firstOrNull()}"
                val memberId = repository.readMyMemberId().firstOrNull()
                if (memberId != null) {
                    _myInfo.emit(repository.getUserInfo(accessToken, memberId))
                    _myInfo.value?.let {
                        repository.saveMyNickName(it.nickName)
                        repository.saveMyImageUrl(it.imageUrl)
                    }
                }
            } catch (e: HttpException) {
                Log.e("BaseViewModel", "getMyInfo: ${e.message}")
            }
        }
    }

    fun getVotesList(
        cursorId: String = "",
        cursorLikes: String = "",
        size: Int,
        searchType: String
    ) {
        viewModelScope.launch {
            try {
                if (currentSearchType != searchType && currentSearchType.isNotEmpty()) {
                    _votesList.value = null
                }
                currentSearchType = searchType
                val accessToken = "Bearer ${repository.readAccessToken().firstOrNull()}"
                if (cursorId.isEmpty() && cursorLikes.isEmpty()) {
                    _votesList.value =
                        repository.getVotesList(
                            accessToken,
                            cursorId,
                            cursorLikes,
                            size,
                            searchType
                        )
                } else {
                    val currentList = _votesList.value?.votes ?: emptyList()
                    val response = repository.getVotesList(
                        accessToken,
                        cursorId,
                        cursorLikes,
                        size,
                        searchType
                    )
                    val updateList = currentList + response.votes
                    _votesList.emit(response.copy(votes = updateList, hasNext = response.hasNext))
                }
            } catch (e: HttpException) {
                Log.e("VoteViewModel", "getVotesList: ${e.message}")
            }
        }
    }

    private fun getSingleVote(
        voteId: String,
        currentPage: Int
    ) {
        viewModelScope.launch {
            try {
                val accessToken = "Bearer ${repository.readAccessToken().firstOrNull()}"
                val voteData = repository.getVote(accessToken, voteId)
                _votesList.value?.let { currentVoteList ->
                    val updateVote = currentVoteList.votes.toMutableList().apply {
                        this[currentPage] = voteData
                    }
                    _votesList.value = currentVoteList.copy(votes = updateVote)
                }
            } catch (e: HttpException) {
                Log.e("VoteViewModel", "getVote: ${e.message}")
            }
        }
    }

    fun voteEvaluation(
        voteId: String,
        currentPage: Int,
        voteEvaluationType: String
    ) {
        viewModelScope.launch {
            try {
                val accessToken = "Bearer ${repository.readAccessToken().firstOrNull()}"
                val response = repository.voteEvaluation(accessToken, voteId, voteEvaluationType)
                if (response.isSuccessful) {
                    if (response.code() == 201) {
                        getSingleVote(voteId, currentPage)
                    }
                } else {
                    Log.e("VoteViewModel", "voteEvaluation: Response was not successful")
                }
            } catch (e: HttpException) {
                Log.e("VoteViewModel", "voteEvaluation: ${e.message}")
            } catch (e: Exception) {
                Log.e("VoteViewModel", "voteEvaluation: ${e.message}")
            }
        }
    }

    fun voteEvaluationDelete(
        voteId: String,
        currentPage: Int
    ) {
        viewModelScope.launch {
            try {
                val accessToken = "Bearer ${repository.readAccessToken().firstOrNull()}"
                val response = repository.voteEvaluationDelete(accessToken, voteId)
                if (response.isSuccessful) {
                    if (response.code() == 204) {
                        getSingleVote(voteId, currentPage)
                    }
                } else {
                    Log.e("VoteViewModel", "voteEvaluationDelete: Response was not successful")
                }

            } catch (e: HttpException) {
                Log.e("VoteViewModel", "voteEvaluationDelete: ${e.message}")
            } catch (e: Exception) {
                Log.e("VoteViewModel", "voteEvaluationDelete: ${e.message}")
            }
        }
    }

    fun addBookmark(
        voteId: String,
        currentPage: Int
    ) {
        viewModelScope.launch {
            try {
                val accessToken = "Bearer ${repository.readAccessToken().firstOrNull()}"
                val response = repository.addBookmark(accessToken, voteId)
                if (response.isSuccessful) {
                    if (response.code() == 201) {
                        getSingleVote(voteId, currentPage)
                    }
                } else {
                    Log.e("VoteViewModel", "addBookmark: Response was not successful")
                }
            } catch (e: HttpException) {
                Log.e("VoteViewModel", "addBookmark: ${e.message}")
            } catch (e: Exception) {
                Log.e("VoteViewModel", "addBookmark: ${e.message}")
            }
        }
    }

    fun deleteBookmark(
        voteId: String,
        currentPage: Int
    ) {
        viewModelScope.launch {
            try {
                val accessToken = "Bearer ${repository.readAccessToken().firstOrNull()}"
                val response = repository.deleteBookmark(accessToken, voteId)
                if (response.isSuccessful) {
                    if (response.code() == 204) {
                        getSingleVote(voteId, currentPage)
                    }
                } else {
                    Log.e("VoteViewModel", "deleteBookmark: Response was not successful")
                }
            } catch (e: HttpException) {
                Log.e("VoteViewModel", "deleteBookmark: ${e.message}")
            } catch (e: Exception) {
                Log.e("VoteViewModel", "deleteBookmark: ${e.message}")
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