package kr.jm.salmal_android.ui.screen.home.comments

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.flow.collectLatest
import kr.jm.salmal_android.data.response.CommentsItem
import kr.jm.salmal_android.ui.screen.component.BasicDialog
import kr.jm.salmal_android.ui.screen.home.comments.bottomsheet.CommentsReportBottomSheet
import kr.jm.salmal_android.ui.screen.home.comments.bottomsheet.CommentsUpdateBottomSheet
import kr.jm.salmal_android.ui.theme.Pretendard
import kr.jm.salmal_android.ui.theme.gray2
import kr.jm.salmal_android.ui.theme.primaryGreen
import kr.jm.salmal_android.ui.theme.primaryRed
import kr.jm.salmal_android.ui.theme.primaryWhite
import kr.jm.salmal_android.utils.Utils
import kr.lifesemantics.salmal_android.R

@Composable
fun CommentsList(
    item: CommentsItem.CommentsResponse,
    commentsIndex: Int,
    myMemberId: Int,
    voteId: String,
    viewModel: CommentsViewModel = hiltViewModel(),
    onShowReplyClick: () -> Unit,
    onAddSubComment: () -> Unit,
    onClickUpdate: () -> Unit,
    updateSubComment: (subCommentId: Int) -> Unit,
) {
    val duration = Utils.calculateRelativeTime(item.updatedAt)
    var showReportCommentBottomSheet by remember {
        mutableStateOf(false)
    }
    var showCommentUpdateBottomSheet by remember {
        mutableStateOf(false)
    }
    var showReportResultDialog by remember {
        mutableStateOf(false)
    }
    var showAlreadyReportDialog by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(viewModel.commentReportSuccess) {
        viewModel.commentReportSuccess.collectLatest {
            showReportResultDialog = it
        }
    }

    LaunchedEffect(viewModel.alreadyReportDialog) {
        viewModel.alreadyReportDialog.collectLatest {
            showAlreadyReportDialog = it
        }
    }

    Row(
        modifier = Modifier.padding(top = 24.dp, start = 15.dp, end = 15.dp),
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = item.memberImageUrl),
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            contentDescription = "member_image_url"
        )
        Column(
            modifier = Modifier
                .padding(start = 15.dp)
                .weight(1f)
        ) {
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
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(start = 8.dp),
                    color = gray2
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = rememberAsyncImagePainter(model = R.drawable.meetball_icon),
                    modifier = Modifier.clickable {
                        if (myMemberId != item.memberId) {
                            showReportCommentBottomSheet = true
                        } else {
                            showCommentUpdateBottomSheet = true
                        }
                    },
                    tint = primaryWhite,
                    contentDescription = "meetball_icon"
                )
            }
            Text(
                text = item.content,
                fontFamily = Pretendard,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 4.dp),
                color = primaryWhite
            )
            Row(
                modifier = Modifier.padding(top = 4.dp)
            ) {
                Icon(
                    painter = rememberAsyncImagePainter(model = R.drawable.like_icon),
                    contentDescription = "like_icon",
                    tint = if (item.liked) primaryRed else primaryWhite,
                    modifier = Modifier.clickable {
                        viewModel.likeComment(item.id, commentsIndex)
                    }
                )
                Text(
                    text = item.likeCount.toString(),
                    fontFamily = Pretendard,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = primaryWhite
                )
                Text(
                    text = "답글 달기",
                    fontFamily = Pretendard,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .clickable { onAddSubComment() },
                    color = gray2
                )
            }
            if (item.replyCount != 0) {
                Text(
                    text = "답글 ${item.replyCount}개 보기",
                    fontFamily = Pretendard,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .padding(top = 14.dp)
                        .clickable {
                            onShowReplyClick()
                        },
                    color = primaryGreen
                )
                item.subComments?.let {
                    it.forEachIndexed { subCommentsIndex, subItem ->
                        SubCommentsList(
                            item = subItem,
                            commentsIndex = commentsIndex,
                            subCommentsIndex = subCommentsIndex,
                            myMemberId = myMemberId,
                            voteId = voteId,
                            commentId = item.id,
                            updateSubComment = { subCommentId ->
                                updateSubComment(subCommentId)
                            }
                        )
                    }
                }
            }
        }
    }
    if (showReportCommentBottomSheet) {
        CommentsReportBottomSheet(
            showBottomSheet = {
                showReportCommentBottomSheet = false
            }, onReport = {
                viewModel.reportComment(item.id)
            })
    }

    if (showCommentUpdateBottomSheet) {
        CommentsUpdateBottomSheet(
            showBottomSheet = {
                showCommentUpdateBottomSheet = false
            },
            onClickUpdate = {
                showCommentUpdateBottomSheet = false
                onClickUpdate()
            },
            onClickDelete = {
                showCommentUpdateBottomSheet = false
                viewModel.deleteComment(targetId = item.id, voteId = voteId, subCommentDelete = false)
            }
        )
    }
    if (showReportResultDialog) {
        BasicDialog(content = "해당 댓글을 신고했어요.") {
            showReportResultDialog = false
        }
    }
    if (showAlreadyReportDialog) {
        BasicDialog(content = "이미 신고한 댓글입니다.") {
            showAlreadyReportDialog = false
        }
    }
}