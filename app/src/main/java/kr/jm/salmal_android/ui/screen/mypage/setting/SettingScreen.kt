package kr.jm.salmal_android.ui.screen.mypage.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import kr.jm.salmal_android.ui.theme.Pretendard
import kr.jm.salmal_android.ui.theme.gray3
import kr.jm.salmal_android.ui.theme.primaryBlack
import kr.jm.salmal_android.ui.theme.primaryWhite
import kr.lifesemantics.salmal_android.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    onClickBack: () -> Unit,
    moveToWebView: (String) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "설정",
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
        Column(
            modifier = Modifier
                .background(primaryBlack)
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            SettingRowItem(
                title = "개인정보 수정",
                iconResource = R.drawable.edit_icon,
                onClick = {

                }
            )
            SettingRowItem(
                title = "이용약관",
                iconResource = R.drawable.terms_icon,
                onClick = {
                    moveToWebView("https://sites.google.com/view/salmalterms/%ED%99%88")
                }
            )
            SettingRowItem(
                title = "개인정보 처리 방침",
                iconResource = R.drawable.terms_icon,
                onClick = {
                    moveToWebView("https://sites.google.com/view/salmalterms2/%ED%99%88")
                }
            )
            SettingRowItem(
                title = "개발자한테 연락하기",
                iconResource = R.drawable.send_icon,
                onClick = {
                    moveToWebView("https://open.kakao.com/o/sVNGzjSf")
                }
            )
            SettingRowItem(
                title = "E-mail 및 SMS 광고성 정보 수신동의",
                iconResource = R.drawable.send_icon,
                onClick = {
                    moveToWebView("https://sites.google.com/view/salmalterms3/%ED%99%88")
                }
            )
            SettingRowItem(
                title = "차단한 사용자",
                iconResource = R.drawable.send_icon,
                onClick = {

                }
            )
        }
    }
}

@Composable
fun SettingRowItem(
    title: String,
    iconResource: Int,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .padding(top = 12.dp)
            .clickable { onClick() }
    ) {
        Icon(
            painter = rememberAsyncImagePainter(model = iconResource),
            modifier = Modifier.size(32.dp),
            tint = primaryWhite,
            contentDescription = "edit_icon"
        )
        Text(
            text = title,
            fontFamily = Pretendard,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = primaryWhite,
            modifier = Modifier.padding(start = 8.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.Filled.KeyboardArrowRight,
            modifier = Modifier.size(32.dp),
            tint = gray3,
            contentDescription = "right_arrow"
        )
    }
}