package kr.jm.salmal_android.data.response


import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class UserInfoResponse(
    val blocked: Boolean, // false
    val disLikeCount: Int, // 8
    val id: Int, // 1
    val imageUrl: String, // imageUrl
    val introduction: String, // 안녕하세요!
    val likeCount: Int, // 11
    val nickName: String, // 사과나무
    val totalVoteCount: Int // 5
) : Parcelable