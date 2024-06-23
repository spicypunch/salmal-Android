package kr.jm.salmal_android.data.response

data class BanListResponse(
    val blockedMembers: List<BlockedMember>,
    val hasNext: Boolean
) {
    data class BlockedMember(
        val blockedDate: String,
        val id: Int,
        val imageUrl: String,
        val nickName: String
    )
}
