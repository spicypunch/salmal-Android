package kr.jm.salmal_android.ui.screen.mypage

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
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
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import kr.jm.salmal_android.data.response.MyVotesResponse
import kr.jm.salmal_android.ui.theme.Pretendard
import kr.jm.salmal_android.ui.theme.gray2
import kr.jm.salmal_android.ui.theme.primaryBlack
import kr.jm.salmal_android.ui.theme.primaryGreen
import kr.jm.salmal_android.ui.theme.primaryWhite
import kr.jm.salmal_android.ui.theme.white20
import kr.jm.salmal_android.ui.theme.white36
import kr.lifesemantics.salmal_android.R

@Composable
fun MyPageScreen(
) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier.padding(horizontal = 18.dp)
            ) {
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = rememberAsyncImagePainter(model = R.drawable.bookmark_icon),
                        tint = primaryWhite,
                        modifier = Modifier.size(24.dp),
                        contentDescription = "bookmark_icon"
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
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
        MyInfoCardView(innerPadding)
    }
}

@Composable
fun MyInfoCardView(
    innerPadding: PaddingValues,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    viewModel.getMyInfo()

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
                        modifier = Modifier.size(80.dp),
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
        TabBar()
    }
}

@Composable
fun TabBar() {
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
            modifier = Modifier
        )

    }
    when (tabIndex) {
        0 -> {
            GetMyVotes()
        }

        1 -> {
            GetMyEvaluations()
        }
    }
}

@Composable
fun GetMyVotes(
    viewModel: MyPageViewModel = hiltViewModel()
) {
    viewModel.getMyInfo()
    val myVotes = viewModel.myVotes.collectAsState()
    LazyGridView(
        myVotes.value?.let {
            it.votes
        }

    )
}

@Composable
fun GetMyEvaluations(
    viewModel: MyPageViewModel = hiltViewModel()
) {
    viewModel.getMyEvaluations()
    val myEvaluations = viewModel.myEvaluations.collectAsState()
    LazyGridView(
        myEvaluations
    )
}

@Composable
fun <T> LazyGridView(
    myVotes: List<T>
) {
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        myVotes.value?.let {
            itemsIndexed(it.votes) { index, item ->

            }
        }
    }
}