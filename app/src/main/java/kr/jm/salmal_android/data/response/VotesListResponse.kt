package kr.jm.salmal_android.data.response

data class VotesListResponse(
    val hasNext: Boolean,
    val votes: List<Vote>
) {
    data class Vote(
        val bookmarked: Boolean,
        val commentCount: Int,
        val createdAt: String,
        val disLikeCount: Int,
        val disLikeRatio: Double,
        val id: Int,
        val imageUrl: String,
        val likeCount: Int,
        val likeRatio: Double,
        val memberId: Int,
        val memberImageUrl: String,
        val nickName: String,
        val status: String,
        val totalEvaluationCnt: Int
    )
}