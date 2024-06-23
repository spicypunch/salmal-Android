package kr.jm.salmal_android.ui.screen.mypage.setting.ban_list

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import kr.jm.salmal_android.data.response.BanListResponse
import kr.jm.salmal_android.ui.theme.Pretendard
import kr.jm.salmal_android.ui.theme.primaryBlack
import kr.jm.salmal_android.ui.theme.primaryGreen
import kr.jm.salmal_android.ui.theme.primaryWhite

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("RememberReturnType")
@Composable
fun BanListScreen(
    onClickBack: () -> Unit,
    viewModel: BanListViewModel = hiltViewModel()
) {
    remember {
        viewModel.getBanList()
    }

    val banList = viewModel.banList.collectAsState().value

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "차단한 사용자",
                        fontFamily = Pretendard,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = primaryWhite
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onClickBack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "뒤로가기",
                            tint = primaryWhite
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(primaryBlack)
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .background(primaryBlack)
                .padding(it)
                .fillMaxSize(),
        ) {
            if (banList != null) {
                itemsIndexed(banList.blockedMembers) { index, item ->
                    BanItems(item = item)
                }
            }
        }
    }
}

@Composable
fun BanItems(
    item: BanListResponse.BlockedMember,
    viewModel: BanListViewModel = hiltViewModel()
) {
    Row(
        modifier = Modifier.padding(horizontal = 18.dp).padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = item.imageUrl),
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            contentDescription = "imageUrl"
        )
        Text(
            text = item.nickName,
            fontFamily = Pretendard,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 12.dp),
            color = primaryWhite,
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { viewModel.cancelUserBan(item.id.toString()) },
            modifier = Modifier
                .width(94.dp)
                .height(40.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                containerColor = primaryGreen,
                disabledContentColor = primaryGreen
            )
        ) {
            Text(
                text = "차단해제",
                fontFamily = Pretendard,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = primaryBlack
            )
        }
    }
}