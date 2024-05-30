package kr.jm.salmal_android.ui.screen.home

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kr.jm.salmal_android.BaseViewModel
import kr.jm.salmal_android.data.response.VotesListResponse
import kr.jm.salmal_android.repository.RepositoryImpl
import retrofit2.HttpException
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: RepositoryImpl,
    override var dataStore: DataStore<Preferences>
) : BaseViewModel() {

    private val _votesList = MutableStateFlow<VotesListResponse?>(null)
    val votesList: StateFlow<VotesListResponse?> = _votesList

    private var _userBanSuccess = MutableSharedFlow<Boolean>()
    val userBanSuccess = _userBanSuccess.asSharedFlow()

    private var _userReportSuccess = MutableSharedFlow<Boolean>()
    val userReportSuccess = _userReportSuccess.asSharedFlow()

    fun getVotesList(
        cursorId: String = "",
        cursorLikes: String = "",
        size: Int,
        searchType: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val accessToken = "Bearer ${readAccessToken().firstOrNull()}"
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
                Log.e("HomeViewModel", "getVotesList: ${e.message}")
            }
        }
    }

    private fun getVote(
        voteId: String,
        currentPage: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val accessToken = "Bearer ${readAccessToken().firstOrNull()}"
                val voteData = repository.getVote(accessToken, voteId)
                _votesList.value?.let { currentVoteList ->
                    val updateVote = currentVoteList.votes.toMutableList().apply {
                        this[currentPage] = voteData
                    }
                    _votesList.value = currentVoteList.copy(votes = updateVote)
                }
            } catch (e: HttpException) {
                Log.e("HomeViewModel", "getVote: ${e.message}")
            }
        }
    }

    fun voteEvaluation(
        voteId: String,
        currentPage: Int,
        voteEvaluationType: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val accessToken = "Bearer ${readAccessToken().firstOrNull()}"
                val response = repository.voteEvaluation(accessToken, voteId, voteEvaluationType)
                if (response.isSuccessful) {
                    if (response.code() == 201) {
                        getVote(voteId, currentPage)
                    }
                } else {
                    Log.e("HomeViewModel", "voteEvaluation: Response was not successful")
                }
            } catch (e: HttpException) {
                Log.e("HomeViewModel", "voteEvaluation: ${e.message}")
            } catch (e: Exception) {
                Log.e("HomeViewModel", "voteEvaluation: ${e.message}")
            }
        }
    }

    fun voteEvaluationDelete(
        voteId: String,
        currentPage: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val accessToken = "Bearer ${readAccessToken().firstOrNull()}"
                val response = repository.voteEvaluationDelete(accessToken, voteId)
                if (response.isSuccessful) {
                    if (response.code() == 204) {
                        getVote(voteId, currentPage)
                    }
                } else {
                    Log.e("HomeViewModel", "voteEvaluationDelete: Response was not successful")
                }

            } catch (e: HttpException) {
                Log.e("HomeViewModel", "voteEvaluationDelete: ${e.message}")
            } catch (e: Exception) {
                Log.e("HomeViewModel", "voteEvaluationDelete: ${e.message}")
            }
        }
    }

    fun addBookmark(
        voteId: String,
        currentPage: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val accessToken = "Bearer ${readAccessToken().firstOrNull()}"
                val response = repository.addBookmark(accessToken, voteId)
                if (response.isSuccessful) {
                    if (response.code() == 201) {
                        getVote(voteId, currentPage)
                    }
                } else {
                    Log.e("HomeViewModel", "addBookmark: Response was not successful")
                }
            } catch (e: HttpException) {
                Log.e("HomeViewModel", "addBookmark: ${e.message}")
            } catch (e: Exception) {
                Log.e("HomeViewModel", "addBookmark: ${e.message}")
            }
        }
    }

    fun deleteBookmark(
        voteId: String,
        currentPage: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val accessToken = "Bearer ${readAccessToken().firstOrNull()}"
                val response = repository.deleteBookmark(accessToken, voteId)
                if (response.isSuccessful) {
                    if (response.code() == 204) {
                        getVote(voteId, currentPage)
                    }
                } else {
                    Log.e("HomeViewModel", "deleteBookmark: Response was not successful")
                }
            } catch (e: HttpException) {
                Log.e("HomeViewModel", "deleteBookmark: ${e.message}")
            } catch (e: Exception) {
                Log.e("HomeViewModel", "deleteBookmark: ${e.message}")
            }
        }
    }

    fun voteReport(voteId: String, reason: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val accessToken = "Bearer ${readAccessToken().firstOrNull()}"
                val response = repository.voteReport(accessToken, voteId, reason)
                if (response.isSuccessful) {
                    if (response.code() == 201) {
                        _userBanSuccess.emit(true)
                    }
                } else {
                    Log.e("HomeViewModel", "userReport: Response was not successful")
                }
            } catch (e: HttpException) {
                Log.e("HomeViewModel", "userReport: ${e.message}")
            } catch (e: Exception) {
                Log.e("HomeViewModel", "userReport: ${e.message}")
            }
        }
    }

    fun userBan(memberId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val accessToken = "Bearer ${readAccessToken().firstOrNull()}"
                val response = repository.userBan(accessToken, memberId)
                if (response.isSuccessful) {
                    if (response.code() == 201) {
                        _userBanSuccess.emit(true)
                    }
                } else {
                    Log.e("HomeViewModel", "userBan: Response was not successful")
                }
            } catch (e: HttpException) {
                Log.e("HomeViewModel", "userBan: ${e.message}")
            } catch (e: Exception) {
                Log.e("HomeViewModel", "userBan: ${e.message}")
            }
        }
    }
}