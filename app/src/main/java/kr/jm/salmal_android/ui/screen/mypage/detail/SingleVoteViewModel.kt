package kr.jm.salmal_android.ui.screen.mypage.detail

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
class SingleVoteViewModel @Inject constructor(
    override var repository: RepositoryImpl,
    override var dataStore: DataStore<Preferences>
) : BaseViewModel() {
    private val _voteDetail = MutableStateFlow<VotesListResponse.Vote?>(null)
    val voteDetail = _voteDetail.asStateFlow()

    fun getDetailVote(
        voteId: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val accessToken = "Bearer ${readAccessToken().firstOrNull()}"
                _voteDetail.emit(repository.getVote(accessToken, voteId))
            } catch (e: HttpException) {
                Log.e("MyPageViewModel", "getDetailVote: ${e.message}")
            }
        }
    }

    fun addBookmark(
        voteId: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val accessToken = "Bearer ${readAccessToken().firstOrNull()}"
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
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val accessToken = "Bearer ${readAccessToken().firstOrNull()}"
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
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val accessToken = "Bearer ${readAccessToken().firstOrNull()}"
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
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val accessToken = "Bearer ${readAccessToken().firstOrNull()}"
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
}