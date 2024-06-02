package kr.jm.salmal_android.data.response

data class CommentsResponse(
    val content: String,
    val createdAt: String,
    val id: Int,
    val likeCount: Int,
    val liked: Boolean,
    val memberId: Int,
    val memberImageUrl: String,
    val nickName: String,
    val replyCount: Int,
    val updatedAt: String
)