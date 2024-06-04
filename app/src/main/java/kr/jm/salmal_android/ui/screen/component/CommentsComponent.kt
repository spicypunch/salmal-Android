package kr.jm.salmal_android.ui.screen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import kr.jm.salmal_android.data.response.CommentsItem
import kr.jm.salmal_android.ui.theme.Pretendard
import kr.jm.salmal_android.ui.theme.gray2
import kr.jm.salmal_android.ui.theme.primaryGreen
import kr.jm.salmal_android.ui.theme.primaryRed
import kr.jm.salmal_android.ui.theme.primaryWhite
import kr.lifesemantics.salmal_android.R

@Composable
fun CommentsComponent(
    memberImageUrl: String,
    nickName: String,
    duration: String,
    content: String,
    likeCount: Int,
    liked: Boolean,
    id: Int,
    replyCount: Int? = null,
    onExpanded: ((Int) -> Unit)? = null
) {
    Row(
        modifier = Modifier.padding(top = 24.dp, start = 15.dp, end = 15.dp),
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = memberImageUrl),
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            contentDescription = "member_image_url"
        )
        Column(
            modifier = Modifier.padding(start = 15.dp)
        ) {
            Row {
                Text(
                    text = nickName,
                    fontFamily = Pretendard,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = primaryWhite
                )
                Text(
                    text = duration,
                    fontFamily = Pretendard,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(start = 8.dp),
                    color = gray2
                )
            }
            Text(
                text = content,
                fontFamily = Pretendard,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 8.dp),
                color = primaryWhite
            )
            Row(
                modifier = Modifier.padding(top = 9.dp)
            ) {
                Icon(
                    painter = rememberAsyncImagePainter(model = R.drawable.like_icon),
                    contentDescription = "like_icon",
                    tint = if (liked) primaryRed else primaryWhite
                )
                Text(
                    text = likeCount.toString(),
                    fontFamily = Pretendard,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = primaryWhite
                )
                Text(
                    text = "댓글 달기",
                    fontFamily = Pretendard,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(start = 16.dp),
                    color = gray2
                )
            }
            if (replyCount != 0) {
                Text(
                    text = "답글 ${replyCount}개 보기",
                    fontFamily = Pretendard,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .padding(top = 14.dp)
                        .clickable {
                            if (onExpanded != null) {
                                onExpanded(id)
                            }
                        },
                    color = primaryGreen
                )

            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = rememberAsyncImagePainter(model = R.drawable.meetball_icon),
            modifier = Modifier,
            tint = primaryWhite,
            contentDescription = "meetball_icon"
        )
    }
}