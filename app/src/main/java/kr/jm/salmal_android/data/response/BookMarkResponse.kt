package kr.jm.salmal_android.data.response


import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class BookMarkResponse(
    val hasNext: Boolean, // true
    val votes: List<Vote>
) : Parcelable {
    @Parcelize
    data class Vote(
        val createdDate: String, // 2024-05-14T06:13:09.841148
        val id: Int, // 40
        val imageUrl: String // imageUrl
    ) : Parcelable
}