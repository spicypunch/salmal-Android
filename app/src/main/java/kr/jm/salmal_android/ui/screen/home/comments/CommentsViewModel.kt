package kr.jm.salmal_android.ui.screen.home.comments

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
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
import kr.jm.salmal_android.BaseViewModel
import kr.jm.salmal_android.data.response.CommentsItem
import kr.jm.salmal_android.repository.RepositoryImpl
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(
    private val repository: RepositoryImpl,
    override var dataStore: DataStore<Preferences>
) : BaseViewModel() {

    private val _commentsList = MutableStateFlow<List<CommentsItem.CommentsResponse>>(emptyList())
    val commentsList = _commentsList.asStateFlow()

    private var _commentReportSuccess = MutableSharedFlow<Boolean>()
    val commentReportSuccess = _commentReportSuccess.asSharedFlow()

    private var _alreadyReportDialog = MutableSharedFlow<Boolean>()
    val alreadyReportDialog = _alreadyReportDialog.asSharedFlow()

    fun getCommentsList(
        voteId: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val accessToken = "Bearer ${readAccessToken().firstOrNull()}"
                _commentsList.value = repository.getCommentsList(accessToken, voteId)
            } catch (e: HttpException) {
                Log.e("CommentsViewModel", "getCommentsList: ${e.message}")
            }
        }
    }

    fun getSubCommentsList(
        commentId: Int,
        index: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val accessToken = "Bearer ${readAccessToken().firstOrNull()}"
                val subComments = repository.getSubCommentsList(
                    accessToken = accessToken,
                    commentId = commentId,
                    cursorId = null,
                    size = 200
                ).comments
                val updatedCommentsList = _commentsList.value.toMutableList().apply {
                    this[index] = this[index].copy(subComments = subComments)
                }
                _commentsList.value = updatedCommentsList
            } catch (e: HttpException) {
                Log.e("CommentsViewModel", "getSubCommentsList: ${e.message}")
            }
        }
    }

    fun likeComment(commentId: Int, commentIndex: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val accessToken = "Bearer ${readAccessToken().firstOrNull()}"
                val currentComments = _commentsList.value
                val targetComment = currentComments.getOrNull(commentIndex) ?: return@launch

                val updatedComment = when (targetComment.liked) {
                    true -> {
                        repository.disLikeComment(accessToken, commentId)
                        targetComment.toggleLike()
                    }

                    false -> {
                        repository.likeComment(accessToken, commentId)
                        targetComment.toggleLike()
                    }
                }

                val updatedCommentsList = currentComments.mapIndexed { index, comment ->
                    if (index == commentIndex) updatedComment else comment
                }
                _commentsList.value = updatedCommentsList

            } catch (e: HttpException) {
                Log.e("CommentsViewModel", "likeComment: ${e.message}")
            } catch (e: Exception) {
                Log.e("CommentsViewModel", "likeComment: ${e.message}")
            }
        }
    }

    fun likeSubComment(
        subCommentId: Int,
        commentIndex: Int,
        subCommentIndex: Int,
        liked: Boolean,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val accessToken = "Bearer ${readAccessToken().firstOrNull()}"
                val currentComments = _commentsList.value.toMutableList()
                val targetComment =
                    currentComments.getOrNull(commentIndex) as? CommentsItem.CommentsResponse
                val targetSubComment =
                    targetComment?.subComments?.getOrNull(subCommentIndex) ?: return@launch

                if (liked) {
                    repository.disLikeComment(accessToken, subCommentId)
                } else {
                    repository.likeComment(accessToken, subCommentId)
                }

                // SubComment 업데이트
                val updatedSubComment = targetSubComment.toggleLike()

                // SubComments 리스트 업데이트
                val updatedSubComments = targetComment.subComments!!.toMutableList()
                updatedSubComments[subCommentIndex] = updatedSubComment

                // 전체 Comments 리스트 업데이트
                val updatedComment = targetComment.copy(subComments = updatedSubComments)
                currentComments[commentIndex] = updatedComment
                _commentsList.value = currentComments

            } catch (e: HttpException) {
                Log.e("CommentsViewModel", "likeSubComment: ${e.message}")
            } catch (e: Exception) {
                Log.e("CommentsViewModel", "likeSubComment: ${e.message}")
            }
        }
    }

    fun addComment(voteId: String, content: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val accessToken = "Bearer ${readAccessToken().firstOrNull()}"
                val response = repository.addComment(accessToken, voteId, content)
                if (response.isSuccessful) {
                    if (response.code() == 201) {
                        getCommentsList(voteId)
                    }
                }
            } catch (e: HttpException) {
                Log.e("CommentsViewModel", "addComment: ${e.message}")
            } catch (e: Exception) {
                Log.e("CommentsViewModel", "addComment: ${e.message}")
            }
        }
    }

    fun addSubComment(commentId: Int, content: String, index: Int, voteId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val accessToken = "Bearer ${readAccessToken().firstOrNull()}"
                val response = repository.addSubComment(accessToken, commentId, content)
                if (response.isSuccessful) {
                    if (response.code() == 201) {
                        getCommentsList(voteId)
                        getSubCommentsList(commentId, index)
                    }
                }
            } catch (e: HttpException) {
                Log.e("CommentsViewModel", "addSubComment: ${e.message}")
            } catch (e: Exception) {
                Log.e("CommentsViewModel", "addSubComment: ${e.message}")
            }
        }
    }

    fun reportComment(commentId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val accessToken = "Bearer ${readAccessToken().firstOrNull()}"
                val response = repository.reportComment(accessToken, commentId)
                if (response.isSuccessful) {
                    if (response.code() == 201) {
                        _commentReportSuccess.emit(true)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    if (errorBody != null) {
                        val jsonObject = JsonParser.parseString(errorBody).asJsonObject
                        val errorCode = jsonObject.get("code").asInt
                        when (errorCode) {
                            2201 -> {
                                _alreadyReportDialog.emit(true)
                            }
                        }
                    }
                }
            } catch (e: HttpException) {
                Log.e("CommentsViewModel", "reportComment: ${e.message}")
            } catch (e: Exception) {
                Log.e("CommentsViewModel", "addSubComment: ${e.message}")
            }
        }
    }

    fun updateComment(
        targetId: Int,
        content: String,
        voteId: String,
        subCommentUpdate: Boolean,
        index: Int,
        commentId: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val accessToken = "Bearer ${readAccessToken().firstOrNull()}"
                val response = repository.updateComment(accessToken, targetId, content)
                if (response.isSuccessful) {
                    if (response.code() == 200) {
                        if (!subCommentUpdate) {
                            getCommentsList(voteId)
                        } else {
                            getCommentsList(voteId)
                            getSubCommentsList(commentId, index)
                        }
                    }
                }
            } catch (e: HttpException) {
                Log.e("CommentsViewModel", "updateComment: ${e.message}")
            } catch (e: Exception) {
                Log.e("CommentsViewModel", "updateComment: ${e.message}")
            }
        }
    }

    fun deleteComment(
        targetId: Int,
        voteId: String,
        subCommentDelete: Boolean,
        index: Int = 0,
        commentId: Int = 0
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val accessToken = "Bearer ${readAccessToken().firstOrNull()}"
                val response = repository.deleteComment(accessToken, targetId)
                if (response.isSuccessful) {
                    if (response.code() == 204) {
                        if (!subCommentDelete) {
                            getCommentsList(voteId)
                        } else {
                            getCommentsList(voteId)
                            getSubCommentsList(commentId, index)
                        }
                    }
                }
            } catch (e: HttpException) {
                Log.e("CommentsViewModel", "deleteComment: ${e.message}")
            } catch (e: Exception) {
                Log.e("CommentsViewModel", "deleteComment: ${e.message}")
            }

        }
    }

    private fun CommentsItem.CommentsResponse.toggleLike(): CommentsItem.CommentsResponse =
        this.copy(liked = !liked, likeCount = if (liked) likeCount - 1 else likeCount + 1)

    private fun CommentsItem.SubCommentsResponse.Comment.toggleLike(): CommentsItem.SubCommentsResponse.Comment =
        this.copy(liked = !liked, likeCount = if (liked) likeCount - 1 else likeCount + 1)
}