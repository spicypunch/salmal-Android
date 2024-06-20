package kr.jm.salmal_android.ui.screen.mypage.detail

import AnimatedProgressButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kr.jm.salmal_android.ui.theme.Pretendard
import kr.jm.salmal_android.ui.theme.gray1
import kr.jm.salmal_android.ui.theme.gray2
import kr.jm.salmal_android.ui.theme.primaryBlack
import kr.jm.salmal_android.ui.theme.primaryGreen
import kr.jm.salmal_android.ui.theme.primaryWhite
import kr.lifesemantics.salmal_android.R


@Composable
fun SingleVoteScreen(
    voteId: String,
    viewModel: SingleVoteViewModel = hiltViewModel()
) {
    viewModel.getDetailVote(voteId)
    val voteDetail = viewModel.voteDetail.collectAsState()

    val memberId = remember { mutableIntStateOf(0) }
    val showBottomSheet = remember { mutableStateOf(false) }
    val showCommentsBottomSheet = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.readMyMemberId().firstOrNull()?.let {
            memberId.intValue = it
        }
    }
    voteDetail.value?.let { voteItem ->
        Column(
            modifier = Modifier.background(color = primaryBlack)
        ) {
            Card(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 8.dp)
                    .fillMaxSize(),
                shape = RoundedCornerShape(18.dp)
            ) {
                Box {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = voteItem.imageUrl,
                        ),
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        contentDescription = "imageLoad",
                    )
                    Box(
                        modifier = Modifier
                            .height(50.dp)
                            .width(94.dp)
                            .offset(x = 16.dp, y = 16.dp)
                            .background(
                                color = primaryBlack,
                                shape = RoundedCornerShape(50)
                            ),
                        contentAlignment = Alignment.TopStart
                    ) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .offset(x = 6.dp)
                                .clickable { }
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(
                                    model = voteItem.memberImageUrl
                                ),
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .size(36.dp),
                                contentScale = ContentScale.Crop,
                                contentDescription = "profile"
                            )
                        }
                        Box(
                            modifier = Modifier
                                .width(40.dp)
                                .align(Alignment.CenterEnd)
                                .offset(x = (-8).dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = voteItem.nickName,
                                fontFamily = Pretendard,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = primaryWhite,
                            )
                        }
                    }
                    if (memberId.intValue != voteItem.memberId) {
                        Icon(
                            painter = rememberAsyncImagePainter(model = R.drawable.meetball_icon),
                            modifier = Modifier
                                .padding(top = 18.dp, end = 18.dp)
                                .align(Alignment.TopEnd)
                                .clickable { showBottomSheet.value = true },
                            tint = primaryWhite,
                            contentDescription = "meetball_icon"
                        )
                    }

                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(end = 16.dp, bottom = 22.dp)

                    ) {
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .background(
                                    color = gray1,
                                    shape = RoundedCornerShape(50)
                                )
                                .clickable {
                                    if (!voteItem.bookmarked) {
                                        viewModel.addBookmark(
                                            voteItem.id.toString(),
                                        )
                                    } else {
                                        viewModel.deleteBookmark(
                                            voteItem.id.toString(),
                                        )
                                    }
                                }
                        ) {
                            Icon(
                                painter = if (voteItem.bookmarked) rememberAsyncImagePainter(
                                    model = R.drawable.bookmark_icon_filled
                                ) else rememberAsyncImagePainter(
                                    model = R.drawable.bookmark_icon
                                ),
                                modifier = Modifier.align(Alignment.Center),
                                tint = if (voteItem.bookmarked) primaryGreen else primaryWhite,
                                contentDescription = "bookmark"
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Box {
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .background(
                                        color = gray1,
                                        shape = RoundedCornerShape(50)
                                    )
                            ) {
                                Icon(
                                    painter = rememberAsyncImagePainter(model = R.drawable.reply_icon),
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .clickable { showCommentsBottomSheet.value = true },
                                    tint = primaryWhite,
                                    contentDescription = "bookmark"
                                )
                                if (voteItem.commentCount != 0) {
                                    Box(
                                        modifier = Modifier
                                            .width(23.dp)
                                            .height(16.dp)
                                            .align(Alignment.TopEnd)
                                            .offset(x = 8.dp, y = (-4).dp)
                                            .background(
                                                color = primaryWhite,
                                                shape = RoundedCornerShape(50)
                                            )
                                    ) {
                                        Text(
                                            text = voteItem.commentCount.toString(),
                                            fontFamily = Pretendard,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Normal,
                                            color = gray2,
                                            modifier = Modifier
                                                .align(Alignment.Center)
                                                .offset(y = (-4).dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .offset(y = (-32).dp)
                    .border(width = 3.dp, color = primaryGreen, shape = RoundedCornerShape(50))
                    .background(color = primaryBlack, shape = RoundedCornerShape(50))
                    .align(Alignment.CenterHorizontally)

            ) {
                Text(
                    text = "🔥 현재 ${voteItem.totalEvaluationCnt}명 참여중!",
                    fontFamily = Pretendard,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    color = primaryWhite,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .padding(horizontal = 12.dp)
                        .align(Alignment.Center)
                        .clickable {
                        }
                )
            }

            AnimatedProgressButton(
                progress = voteItem.likeRatio.toFloat(),
                buttonText = "👍🏻살",
                start = 18,
                end = 18,
                status = voteItem.status,
                modifier = Modifier.offset(y = (-28).dp)
            ) {
                when (voteItem.status) {
                    "NONE" -> {
                        viewModel.voteEvaluation(voteId, currentPage.intValue, "LIKE")
                    }

                    "LIKE" -> {
                        viewModel.voteEvaluationDelete(voteId, currentPage.intValue)
                    }

                    "DISLIKE" -> {
                        scope.launch {
                            snackbarHostState.showSnackbar("❌ 이미 반대편에 투표했어요")
                        }
                    }
                }
            }

            AnimatedProgressButton(
                progress = voteItem.disLikeRatio.toFloat(),
                buttonText = "👎🏻말",
                start = 18,
                end = 18,
                top = 9,
                status = voteItem.status,
                modifier = Modifier.offset(y = (-28).dp)
            ) {
                when (voteItem.status) {
                    "NONE" -> {
                        viewModel.voteEvaluation(voteId, currentPage.intValue, "DISLIKE")
                    }

                    "LIKE" -> {
                        scope.launch {
                            snackbarHostState.showSnackbar("❌ 이미 반대편에 투표했어요")
                        }
                    }

                    "DISLIKE" -> {
                        viewModel.voteEvaluationDelete(voteId, currentPage.intValue)
                    }
                }
            }
        }
    }
    if (showCommentsBottomSheet.value) {
//        CommentsBottomSheet(voteId, commentCount) {
//            showCommentsBottomSheet.value = false
//        }
    }
}