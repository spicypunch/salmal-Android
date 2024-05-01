package kr.jm.salmal_android.screen.agreement

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import kr.jm.salmal_android.screen.component.BasicButton
import kr.jm.salmal_android.ui.theme.Gray2
import kr.jm.salmal_android.ui.theme.Gray3
import kr.jm.salmal_android.ui.theme.Pretendard
import kr.jm.salmal_android.ui.theme.primaryBlack
import kr.jm.salmal_android.ui.theme.primaryWhite
import kr.lifesemantics.salmal_android.R

@Composable
fun AgreementScreen(
    viewModel: AgreementViewModel = hiltViewModel(),
    moveToWebView: (String) -> Unit,
    moveToSetProfile: () -> Unit
) {
    var selectAll by rememberSaveable {
        mutableStateOf(false)
    }
    var isCheckedTerms by rememberSaveable {
        mutableStateOf(false)
    }
    var isCheckedCollectInfo by rememberSaveable {
        mutableStateOf(false)
    }
    var isCheckedMarketing by rememberSaveable {
        mutableStateOf(false)
    }
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .fillMaxSize()
            .background(color = primaryBlack)
            .padding(start = 18.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = R.drawable.salmal_icon_transparent),
            modifier = Modifier
                .size(size = 94.dp),
            contentDescription = "salmalIconBlack"
        )
        Text(
            text = "환영합니다!",
            fontFamily = Pretendard,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = primaryWhite
        )
        Text(
            text = "지금까지의 쇼핑 고민, 살말이 해결해드릴게요!",
            fontFamily = Pretendard,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Gray2,
            modifier = Modifier.padding(top = 12.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 180.dp)
        ) {
            Image(
                painter = if (!selectAll) rememberAsyncImagePainter(model = R.drawable.checkbox_false) else rememberAsyncImagePainter(
                    model = R.drawable.checkbox_true
                ),
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        selectAll = !selectAll
                        if (selectAll) {
                            isCheckedTerms = true
                            isCheckedCollectInfo = true
                            isCheckedMarketing = true
                        } else {
                            isCheckedTerms = false
                            isCheckedCollectInfo = false
                            isCheckedMarketing = false
                        }
                    },
                contentDescription = "checkBox"
            )
            Text(
                text = "약관 전체동의",
                fontFamily = Pretendard,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = primaryWhite,
                modifier = Modifier
                    .padding(start = 14.dp)
                    .clickable {
                        selectAll = !selectAll
                    }
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 41.dp)
        ) {
            Image(
                painter = if (!isCheckedTerms) rememberAsyncImagePainter(model = R.drawable.checkbox_false) else rememberAsyncImagePainter(
                    model = R.drawable.checkbox_true
                ),
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        isCheckedTerms = !isCheckedTerms
                    },
                contentDescription = "checkBox"
            )
            Text(
                text = "이용약관동의(필수)",
                fontFamily = Pretendard,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = primaryWhite,
                modifier = Modifier
                    .padding(start = 14.dp)
                    .clickable {
                        isCheckedTerms = !isCheckedTerms
                    },
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                tint = Gray3,
                modifier = Modifier
                    .padding(end = 18.dp)
                    .clickable { moveToWebView("https://sites.google.com/view/salmalterms/%ED%99%88") },
                contentDescription = "rightArrow"
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 41.dp)
        ) {
            Image(
                painter = if (!isCheckedCollectInfo) rememberAsyncImagePainter(model = R.drawable.checkbox_false) else rememberAsyncImagePainter(
                    model = R.drawable.checkbox_true
                ),
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        isCheckedCollectInfo = !isCheckedCollectInfo
                    },
                contentDescription = "checkBox"
            )
            Text(
                text = "개인정보 수집 밎 이용동의(필수)",
                fontFamily = Pretendard,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = primaryWhite,
                modifier = Modifier
                    .padding(start = 14.dp)
                    .clickable {
                        isCheckedCollectInfo = !isCheckedCollectInfo
                    },
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                tint = Gray3,
                modifier = Modifier
                    .padding(end = 18.dp)
                    .clickable { moveToWebView("https://sites.google.com/view/salmalterms2/%ED%99%88") },
                contentDescription = "rightArrow"
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 41.dp)
        ) {
            Image(
                painter = if (!isCheckedMarketing) rememberAsyncImagePainter(model = R.drawable.checkbox_false) else rememberAsyncImagePainter(
                    model = R.drawable.checkbox_true
                ),
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        isCheckedMarketing = !isCheckedMarketing
                    },
                contentDescription = "checkBox"
            )
            Text(
                text = "E-mail 및 SMS 광고성 정보 수신동의(선택)",
                style = TextStyle(letterSpacing = (-0.7).sp),
                fontFamily = Pretendard,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = primaryWhite,
                modifier = Modifier
                    .padding(start = 14.dp)
                    .clickable {
                        isCheckedMarketing = !isCheckedMarketing
                    },
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                tint = Gray3,
                modifier = Modifier
                    .padding(end = 18.dp)
                    .clickable { moveToWebView("https://sites.google.com/view/salmalterms3/%ED%99%88") },
                contentDescription = "rightArrow"
            )
        }
        BasicButton(
            text = "다음",
            end = 18,
            top = 44,
            bottom = 32,
            enabled = isCheckedTerms && isCheckedCollectInfo
        ) {
            viewModel.saveMarketingInformationConsent(isCheckedMarketing)
            moveToSetProfile()
        }
    }
}
