package kr.jm.salmal_android.data.response

sealed class CommentsItem {
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
        val updatedAt: String,
        var subComments: List<SubCommentsResponse.Comment>? = null
    ) : CommentsItem()

    data class SubCommentsResponse(
        val comments: List<Comment>,
        val hasNext: Boolean
    ) {
        data class Comment(
            val content: String,
            val createdAt: String,
            val id: Int,
            val likeCount: Int,
            val liked: Boolean,
            val memberId: Int,
            val memberImageUrl: String,
            val nickName: String,
            val updatedAt: String
        ) : CommentsItem()
    }
}