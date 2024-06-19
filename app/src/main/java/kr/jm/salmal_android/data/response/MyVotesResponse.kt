package kr.jm.salmal_android.data.response


import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class MyVotesResponse(
    val hasNext: Boolean, // true
    val votes: List<Vote>
) : Parcelable {
    @Parcelize
    data class Vote(
        val createdDate: String, // 2024-05-14T06:13:09.907338
        val id: Int, // 9
        val imageUrl: String // imageUrl
    ) : Parcelable
}