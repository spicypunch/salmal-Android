package kr.jm.salmal_android.ui.screen.home.vote

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
import kr.jm.salmal_android.data.response.VotesListResponse
import kr.jm.salmal_android.repository.RepositoryImpl
import retrofit2.HttpException
import javax.inject.Inject


@HiltViewModel
class VoteViewModel @Inject constructor(
    override var repository: RepositoryImpl,
    override var dataStore: DataStore<Preferences>
) : BaseViewModel() {

    private val _votesList = MutableStateFlow<VotesListResponse?>(null)
    val votesList = _votesList.asStateFlow()

    private var currentSearchType = ""

    fun getVotesList(
        cursorId: String = "",
        cursorLikes: String = "",
        size: Int,
        searchType: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (currentSearchType != searchType && currentSearchType.isNotEmpty()) {
                    _votesList.value = null
                }
                currentSearchType = searchType
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
                Log.e("VoteViewModel", "getVotesList: ${e.message}")
            }
        }
    }

    private fun getSingleVote(
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
                Log.e("VoteViewModel", "getVote: ${e.message}")
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
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val accessToken = "Bearer ${readAccessToken().firstOrNull()}"
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
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val accessToken = "Bearer ${readAccessToken().firstOrNull()}"
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
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val accessToken = "Bearer ${readAccessToken().firstOrNull()}"
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
}