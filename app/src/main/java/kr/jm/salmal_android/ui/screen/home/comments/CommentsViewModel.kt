package kr.jm.salmal_android.ui.screen.home.comments

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
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
        commentId: Int,
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
                    repository.disLikeComment(accessToken, commentId)
                } else {
                    repository.likeComment(accessToken, commentId)
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

    fun addSubComment(commentId: Int, content: String, index: Int ,voteId: String) {
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


    private fun CommentsItem.CommentsResponse.toggleLike(): CommentsItem.CommentsResponse =
        this.copy(liked = !liked, likeCount = if (liked) likeCount - 1 else likeCount + 1)

    private fun CommentsItem.SubCommentsResponse.Comment.toggleLike(): CommentsItem.SubCommentsResponse.Comment =
        this.copy(liked = !liked, likeCount = if (liked) likeCount - 1 else likeCount + 1)

}