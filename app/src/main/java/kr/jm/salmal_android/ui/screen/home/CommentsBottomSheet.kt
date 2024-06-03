package kr.jm.salmal_android.ui.screen.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import kr.jm.salmal_android.data.response.CommentsResponse
import kr.jm.salmal_android.ui.theme.Pretendard
import kr.jm.salmal_android.ui.theme.gray2
import kr.jm.salmal_android.ui.theme.gray4
import kr.jm.salmal_android.ui.theme.primaryGreen
import kr.jm.salmal_android.ui.theme.primaryRed
import kr.jm.salmal_android.ui.theme.primaryWhite
import kr.jm.salmal_android.ui.theme.white36
import kr.jm.salmal_android.utils.Utils
import kr.lifesemantics.salmal_android.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentsBottomSheet(
    voteId: String,
    commentsCount: Int,
    viewModel: VoteViewModel = hiltViewModel(),
    showCommentsBottomSheet: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    viewModel.getCommentsList(voteId)

    val commentsList = viewModel.commentsList.collectAsState()

    ModalBottomSheet(
        onDismissRequest = { showCommentsBottomSheet() },
        sheetState = sheetState,
        containerColor = gray4,
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Row(
            ) {
                Text(
                    text = "댓글",
                    fontFamily = Pretendard,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = primaryWhite,
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .align(Alignment.Bottom)
                )
                Text(
                    text = "$commentsCount",
                    fontFamily = Pretendard,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    color = gray2,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .align(Alignment.Bottom)
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = rememberAsyncImagePainter(model = R.drawable.cancel_icon),
                    modifier = Modifier
                        .align(Alignment.Bottom)
                        .padding(end = 8.dp)
                        .clickable { showCommentsBottomSheet() },
                    contentDescription = "cancel_icon"
                )
            }
            HorizontalDivider(
                modifier = Modifier
                    .height(1.dp)
                    .padding(top = 12.dp)
                    .fillMaxWidth(),
                color = white36
            )

            LazyColumn {
                items(commentsList.value) { item ->
                    CommentsList(item)
                }
            }
        }
    }
}

@Composable
fun CommentsList(item: CommentsResponse) {
    val duration = Utils.calculateRelativeTime(item.updatedAt)
    Row {
        Image(
            painter = rememberAsyncImagePainter(model = item.memberImageUrl),
            modifier = Modifier.size(36.dp).clip(CircleShape),
            contentScale = ContentScale.Crop,
            contentDescription = "member_image_url"
        )
        Column {
            Row {
                Text(
                    text = item.nickName,
                    fontFamily = Pretendard,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = primaryWhite
                )
                Text(
                    text = duration,
                    fontFamily = Pretendard,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    color = primaryWhite
                )
            }
            Text(
                text = item.content,
                fontFamily = Pretendard,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = primaryWhite
            )
            Row {
                Icon(
                    painter = rememberAsyncImagePainter(model = R.drawable.like_icon),
                    contentDescription = "like_icon",
                    tint = if (item.liked) primaryRed else primaryWhite
                )
                Text(
                    text = item.likeCount.toString(),
                    fontFamily = Pretendard,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    color = primaryWhite
                )
            }
            if (item.replyCount != 0) {
                Text(
                    text = "답글 ${item.replyCount}개 보기",
                    fontFamily = Pretendard,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    color = primaryGreen
                )
            }
        }
        Icon(
            painter = rememberAsyncImagePainter(model = R.drawable.meetball_icon),
            modifier = Modifier,
            tint = primaryWhite,
            contentDescription = "meetball_icon"
        )
    }
}