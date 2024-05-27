package kr.jm.salmal_android.ui.screen.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kr.jm.salmal_android.BaseViewModel
import kr.jm.salmal_android.data.response.VotesListResponse
import kr.jm.salmal_android.repository.RepositoryImpl
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: RepositoryImpl,
    override var dataStore: DataStore<Preferences>
) : BaseViewModel() {

    private val _votesList = mutableStateOf<VotesListResponse?>(null)
    val votesList: State<VotesListResponse?> = _votesList

    fun getVotesList(
        cursorId: String = "",
        cursorLikes: String = "",
        size: Int,
        searchType: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val accessToken = "Bearer ${readAccessToken().firstOrNull()}"
                _votesList.value =
                    repository.getVotesList(accessToken, cursorId, cursorLikes, size, searchType)
            } catch (e: Exception) {
                Log.e("HomeViewModel", "getVotesList: ${e.message}")
            }
        }
    }

    fun getVote(
        voteId: String,
        currentPage: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val accessToken = "Bearer ${readAccessToken().firstOrNull()}"
                val voteData = repository.getVote(accessToken, voteId)
                _votesList.value?.let {  currentVoteList ->
                    val updateVote = currentVoteList.votes.toMutableList().apply {
                        this[currentPage] = voteData
                    }

                    _votesList.value = currentVoteList.copy(votes = updateVote)
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "getVote: ${e.message}")
            }
        }
    }

    fun voteEvaluation(
        voteId: String,
        voteEvaluationType: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val accessToken = "Bearer ${readAccessToken().firstOrNull()}"
                repository.voteEvaluation(accessToken, voteId, voteEvaluationType)
            } catch (e: Exception) {
                Log.e("HomeViewModel", "voteEvaluation: ${e.message}")
            }
        }
    }

    fun voteEvaluationDelete(
        voteId: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val accessToken = "Bearer ${readAccessToken().firstOrNull()}"
                repository.voteEvaluationDelete(accessToken, voteId)
            } catch (e: Exception) {
                Log.e("HomeViewModel", "voteEvaluationDelete: ${e.message}")
            }
        }
    }

    fun replaceVoteDate() {

    }
}