package kr.jm.salmal_android.ui.screen.mypage

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import kr.jm.salmal_android.ui.screen.component.LazyGridView
import kr.jm.salmal_android.ui.theme.Pretendard
import kr.jm.salmal_android.ui.theme.gray2
import kr.jm.salmal_android.ui.theme.primaryBlack
import kr.jm.salmal_android.ui.theme.primaryGreen
import kr.jm.salmal_android.ui.theme.primaryWhite
import kr.jm.salmal_android.ui.theme.white36
import kr.lifesemantics.salmal_android.R

@Composable
fun MyPageScreen(
    onClick: (String) -> Unit,
    onClickSetting: () -> Unit,
    onClickBookmark: () -> Unit,
    goToDeleteMyVote: () -> Unit
) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier.padding(horizontal = 18.dp)
            ) {
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { onClickBookmark() }) {
                    Icon(
                        painter = rememberAsyncImagePainter(model = R.drawable.bookmark_icon),
                        tint = primaryWhite,
                        modifier = Modifier.size(24.dp),
                        contentDescription = "bookmark_icon"
                    )
                }
                IconButton(onClick = { onClickSetting() }) {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        tint = primaryWhite,
                        modifier = Modifier.size(24.dp),
                        contentDescription = "settings_icon"
                    )
                }
            }
        },
        containerColor = primaryBlack,
    ) { innerPadding ->
        MyInfoCardView(
            innerPadding,
            onClick = {
                onClick(it)
            },
            goToDeleteMyVote = {
                goToDeleteMyVote()
            }
        )
    }
}

@SuppressLint("RememberReturnType")
@Composable
fun MyInfoCardView(
    innerPadding: PaddingValues,
    onClick: (String) -> Unit,
    goToDeleteMyVote: () -> Unit,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    remember {
        viewModel.getMyInfo()
    }

    val myInfo = viewModel.myInfo.collectAsState()
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize(),
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(all = 18.dp),
            colors = CardDefaults.cardColors(primaryGreen),
            shape = RoundedCornerShape(30.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                myInfo.value?.let {
                    Image(
                        painter = rememberAsyncImagePainter(model = it.imageUrl),
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape),
                        contentDescription = "my_image_url"
                    )
                    Text(
                        text = it.nickName,
                        fontFamily = Pretendard,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = primaryBlack,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    Text(
                        text = it.introduction,
                        fontFamily = Pretendard,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal,
                        color = primaryBlack,
                        modifier = Modifier.padding(top = 6.dp)
                    )
                    Row(
                        modifier = Modifier
                            .padding(top = 30.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Box {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(model = R.drawable.sal_icon),
                                    modifier = Modifier.size(45.dp),
                                    contentDescription = "sal_icon"
                                )
                                Text(
                                    text = "${it.likeCount}",
                                    fontFamily = Pretendard,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = primaryBlack,
                                    modifier = Modifier.padding(start = 6.dp)
                                )
                            }
                        }

                        Box {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(model = R.drawable.mal_icon),
                                    modifier = Modifier.size(45.dp),
                                    contentDescription = "mal_icon"
                                )
                                Text(
                                    text = "${it.disLikeCount}",
                                    fontFamily = Pretendard,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = primaryBlack,
                                    modifier = Modifier.padding(start = 6.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
        TabBar(
            onClick = {
                onClick(it)
            },
            goToDeleteMyVote = {
                goToDeleteMyVote()
            }
        )
    }
}

@Composable
fun TabBar(
    onClick: (String) -> Unit,
    goToDeleteMyVote: () -> Unit
) {
    var tabIndex by remember {
        mutableIntStateOf(0)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp)
            .padding(horizontal = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "업로드",
            fontFamily = Pretendard,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (tabIndex == 0) primaryWhite else white36,
            modifier = Modifier.clickable {
                tabIndex = 0
            }
        )
        Text(
            text = "투표",
            fontFamily = Pretendard,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (tabIndex == 1) primaryWhite else white36,
            modifier = Modifier
                .padding(start = 12.dp)
                .clickable {
                    tabIndex = 1
                }
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "편집",
            fontFamily = Pretendard,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = gray2,
            modifier = Modifier.clickable {
                goToDeleteMyVote()
            }
        )

    }
    when (tabIndex) {
        0 -> {
            GetMyVotes(onClick = {
                onClick(it)
            })
        }

        1 -> {
            GetMyEvaluations(onClick = {
                onClick(it)
            })
        }
    }
}

@SuppressLint("RememberReturnType")
@Composable
fun GetMyVotes(
    onClick: (String) -> Unit,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    remember {
        viewModel.getMyVotes()
    }

    val myVotes = viewModel.myVotes.collectAsState()
    LazyGridView(
        myVotes.value?.votes ?: emptyList(),
    ) {
        onClick(it)
    }
}

@Composable
fun GetMyEvaluations(
    onClick: (String) -> Unit,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    viewModel.getMyEvaluations()
    val myEvaluations = viewModel.myEvaluations.collectAsState()
    LazyGridView(
        myEvaluations.value?.votes ?: emptyList(),
    ) {
        onClick(it)
    }
}