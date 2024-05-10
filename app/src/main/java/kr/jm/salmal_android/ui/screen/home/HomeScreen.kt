package kr.jm.salmal_android.ui.screen.home

import android.Manifest
import android.os.Build
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import kotlinx.coroutines.launch
import kr.jm.salmal_android.ui.screen.component.BasicButton
import kr.jm.salmal_android.ui.theme.Pretendard
import kr.jm.salmal_android.ui.theme.gray1
import kr.jm.salmal_android.ui.theme.gray2
import kr.jm.salmal_android.ui.theme.primaryBlack
import kr.jm.salmal_android.ui.theme.primaryGreen
import kr.jm.salmal_android.ui.theme.primaryWhite
import kr.jm.salmal_android.ui.theme.white20
import kr.jm.salmal_android.utils.Utils
import kr.lifesemantics.salmal_android.R

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val permissionList =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) arrayOf(Manifest.permission.POST_NOTIFICATIONS) else arrayOf()

    var tabIndex by remember {
        mutableIntStateOf(0)
    }

    Utils.requestPermissions(
        list = permissionList,
        useDeniedMessage = false,
        onGranted = {
            scope.launch {
                snackbarHostState.showSnackbar("알림이 거부되었습니다.")
            }
        },
        onDenied = {
            scope.launch {
                snackbarHostState.showSnackbar("알림이 허용되었습니다.")
            }
        })



    Scaffold {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(color = primaryBlack)
        ) {
            Row {
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
                    Home()
                }

                1 -> {
                    Best()
                }
            }
        }
    }
}

@Composable
fun Home() {
    Column {
        Card(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp)
                .weight(1f)
                .fillMaxWidth(),
            shape = RoundedCornerShape(18.dp)
        ) {
            Box {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("https://www.kukinews.com/data/kuk/image/2023/08/07/kuk202308070046.680x.0.jpg")
                            .crossfade(true).build()
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
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(
                                model = R.drawable.mypage_filled_icon
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
                            .width(46.dp)
                            .align(Alignment.CenterEnd)
                            .offset(x = (-8).dp),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Text(
                            text = "김종민",
                            fontFamily = Pretendard,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = primaryWhite,
                        )
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
                    ) {
                        Icon(
                            painter = rememberAsyncImagePainter(model = R.drawable.bookmark_icon),
                            modifier = Modifier.align(Alignment.Center),
                            tint = primaryWhite,
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
                                    text = "42",
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

        Box(
            modifier = Modifier
                .offset(y = (-32).dp)
                .border(width = 3.dp, color = primaryGreen, shape = RoundedCornerShape(50))
                .background(color = primaryBlack, shape = RoundedCornerShape(50))
                .align(Alignment.CenterHorizontally)

        ) {
            Text(
                text = "🔥 현재 3,200명 참여중!",
                fontFamily = Pretendard,
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal,
                color = primaryWhite,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .padding(horizontal = 12.dp)
                    .align(Alignment.Center)
            )
        }

        BasicButton(
            text = "👍🏻살",
            start = 18,
            end = 18,
            enabled = true,
            color = white20,
            textColor = primaryWhite,
            modifier = Modifier.offset(y = (-28).dp)
        ) {

        }

        BasicButton(
            text = "👎🏻말",
            start = 18,
            end = 18,
            top = 9,
            enabled = true,
            color = white20,
            textColor = primaryWhite,
            modifier = Modifier.offset(y = (-28).dp)
        ) {

        }
    }
}

@Composable
fun Best() {

}

@Preview
@Composable
private fun HomePreview() {
    HomeScreen()
}