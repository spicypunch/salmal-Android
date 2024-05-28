package kr.jm.salmal_android.ui.screen.home

import AnimatedProgressButton
import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kr.jm.salmal_android.ui.screen.component.CircularProgressBar
import kr.jm.salmal_android.ui.theme.Gray4
import kr.jm.salmal_android.ui.theme.Pretendard
import kr.jm.salmal_android.ui.theme.gray1
import kr.jm.salmal_android.ui.theme.gray2
import kr.jm.salmal_android.ui.theme.primaryBlack
import kr.jm.salmal_android.ui.theme.primaryGreen
import kr.jm.salmal_android.ui.theme.primaryWhite
import kr.jm.salmal_android.utils.Utils.requestPermissions
import kr.lifesemantics.salmal_android.R

@Composable
fun HomeScreen() {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val permissionList =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) arrayOf(Manifest.permission.POST_NOTIFICATIONS) else arrayOf()

    var tabIndex by remember {
        mutableIntStateOf(0)
    }

    requestPermissions(
        list = permissionList,
        useDeniedMessage = true,
        onGranted = {
//            scope.launch {
//                snackbarHostState.showSnackbar("알림이 허용되었습니다.")
//            }
        },
        onDenied = {
//            scope.launch {
//                snackbarHostState.showSnackbar("알림이 거부되었습니다.")
//            }
        })



    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(color = primaryBlack)
        ) {
            Row(modifier = Modifier.padding(top = 8.dp)) {
                Image(
                    painter = rememberAsyncImagePainter(model = if (tabIndex == 0) R.drawable.home_color else R.drawable.home_basic),
                    modifier = Modifier
                        .padding(start = 18.dp)
                        .clickable { tabIndex = 0 },
                    contentDescription = "tab_home"
                )
                Image(
                    painter = rememberAsyncImagePainter(model = if (tabIndex == 1) R.drawable.best_color else R.drawable.best_basic),
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .clickable { tabIndex = 1 },
                    contentDescription = "tab_best"
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = rememberAsyncImagePainter(model = R.drawable.bell_basic),
                    modifier = Modifier.padding(end = 18.dp),
                    contentDescription = "tab_bell"
                )
            }

            when (tabIndex) {
                0 -> {
                    VotesScreen("HOME", snackbarHostState)
                }

                1 -> {
                    VotesScreen("BEST", snackbarHostState)
                }
            }
        }
    }
}

@SuppressLint("RememberReturnType")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VotesScreen(
    type: String,
    snackbarHostState: SnackbarHostState,
    viewModel: HomeViewModel = hiltViewModel()
) {
    LaunchedEffect(type) {
        viewModel.getVotesList(size = 3, searchType = type)
    }

    val voteList by viewModel.votesList.collectAsState()
    val currentPage = remember {
        mutableIntStateOf(0)
    }

    val scope = rememberCoroutineScope()

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { voteList?.votes?.size ?: 0 }
    )

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }
            .collectLatest { page ->
                currentPage.intValue = page
                val lastPageIndex = voteList?.votes?.size?.minus(1) ?: 0
                if (page == lastPageIndex) {
                    viewModel.getVotesList(
                        cursorId = voteList?.votes?.get(page)?.id.toString(),
                        cursorLikes = voteList?.votes?.get(page)?.likeCount.toString(),
                        size = 3,
                        searchType = type
                    )
                }
            }
    }

    Column(modifier = Modifier.background(color = Gray4)) {
        VerticalPager(state = pagerState, modifier = Modifier.weight(1f)) { page ->
            val voteItem = voteList?.votes?.getOrNull(page)
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
                            model = voteItem?.imageUrl,
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
                                .clickable {  }
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(
                                    model = voteItem?.memberImageUrl
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
                            voteItem?.nickName?.let {
                                Text(
                                    text = it,
                                    fontFamily = Pretendard,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = primaryWhite,
                                )
                            }
                        }
                    }

                    Icon(
                        painter = rememberAsyncImagePainter(model = R.drawable.meetball_icon),
                        modifier = Modifier
                            .padding(top = 18.dp, end = 18.dp)
                            .align(Alignment.TopEnd),
                        tint = primaryWhite,
                        contentDescription = "meetball_icon"
                    )
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
                                    if (voteItem?.bookmarked == false) {
                                        viewModel.addBookmark(voteItem.id.toString(), currentPage.intValue)
                                    } else {
                                        viewModel.deleteBookmark(voteItem?.id.toString(), currentPage.intValue)
                                    }
                                }
                        ) {
                            Icon(
                                painter = if (voteItem?.bookmarked == true) rememberAsyncImagePainter(
                                    model = R.drawable.bookmark_icon_filled
                                ) else rememberAsyncImagePainter(
                                    model = R.drawable.bookmark_icon
                                ),
                                modifier = Modifier.align(Alignment.Center),
                                tint = if (voteItem?.bookmarked == true) primaryGreen else primaryWhite,
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
                                    modifier = Modifier.align(Alignment.Center),
                                    tint = primaryWhite,
                                    contentDescription = "bookmark"
                                )
                                if (voteItem?.commentCount != 0) {
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
                                            text = voteItem?.commentCount.toString(),
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
        }

        voteList?.let {
            val totalEvaluationCount =
                voteList!!.votes.get(currentPage.intValue).totalEvaluationCnt ?: 0
            val likeCount = voteList!!.votes.get(currentPage.intValue).likeCount ?: 0
            val disLikeCount = voteList!!.votes.get(currentPage.intValue).disLikeCount ?: 0
            val myVoteStatus = voteList!!.votes.get(currentPage.intValue).status
            val voteId = voteList!!.votes.get(currentPage.intValue).id.toString()
            Box(
                modifier = Modifier
                    .offset(y = (-32).dp)
                    .border(width = 3.dp, color = primaryGreen, shape = RoundedCornerShape(50))
                    .background(color = primaryBlack, shape = RoundedCornerShape(50))
                    .align(Alignment.CenterHorizontally)

            ) {
                Text(
                    text = "🔥 현재 ${voteList!!.votes.get(currentPage.intValue).totalEvaluationCnt}명 참여중!",
                    fontFamily = Pretendard,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    color = primaryWhite,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .padding(horizontal = 12.dp)
                        .align(Alignment.Center)
                        .clickable {
                            viewModel.getVotesList(size = 1, searchType = "HOME")
                        }
                )
            }

            AnimatedProgressButton(
                progress = likeCount.toFloat() / totalEvaluationCount.toFloat(),
                buttonText = "👍🏻살",
                start = 18,
                end = 18,
                status = myVoteStatus,
                modifier = Modifier.offset(y = (-28).dp)
            ) {
                when (myVoteStatus) {
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
                progress = disLikeCount.toFloat() / totalEvaluationCount.toFloat(),
                buttonText = "👎🏻말",
                start = 18,
                end = 18,
                top = 9,
                status = myVoteStatus,
                modifier = Modifier.offset(y = (-28).dp)
            ) {
                when (myVoteStatus) {
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
}