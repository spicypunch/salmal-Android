package kr.jm.salmal_android.screen

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    moveToWebView: (String) -> Unit
) {
    val selectAll = rememberSaveable {
        mutableStateOf(false)
    }
    val isCheckedFirst = rememberSaveable {
        mutableStateOf(false)
    }
    val isCheckedSecond = rememberSaveable {
        mutableStateOf(false)
    }
    val isCheckedThird = rememberSaveable {
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
                painter = if (!selectAll.value) rememberAsyncImagePainter(model = R.drawable.checkbox_false) else rememberAsyncImagePainter(
                    model = R.drawable.checkbox_true
                ),
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        selectAll.value = !selectAll.value
                        if (selectAll.value) {
                            isCheckedFirst.value = true
                            isCheckedSecond.value = true
                            isCheckedThird.value = true
                        } else {
                            isCheckedFirst.value = false
                            isCheckedSecond.value = false
                            isCheckedThird.value = false
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
                        selectAll.value = !selectAll.value
                    }
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 41.dp)
        ) {
            Image(
                painter = if (!isCheckedFirst.value) rememberAsyncImagePainter(model = R.drawable.checkbox_false) else rememberAsyncImagePainter(
                    model = R.drawable.checkbox_true
                ),
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        isCheckedFirst.value = !isCheckedFirst.value
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
                        isCheckedFirst.value = !isCheckedFirst.value
                    },
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                tint = Gray3,
                modifier = Modifier
                    .padding(end = 18.dp)
                    .clickable { moveToWebView("https://honorable-overcoat-a54.notion.site/1b14e3eedc6a4bf3ac8d4a7aad484328?pvs=4") },
                contentDescription = "rightArrow"
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 41.dp)
        ) {
            Image(
                painter = if (!isCheckedSecond.value) rememberAsyncImagePainter(model = R.drawable.checkbox_false) else rememberAsyncImagePainter(
                    model = R.drawable.checkbox_true
                ),
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        isCheckedSecond.value = !isCheckedSecond.value
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
                        isCheckedSecond.value = !isCheckedSecond.value
                    },
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                tint = Gray3,
                modifier = Modifier
                    .padding(end = 18.dp)
                    .clickable { moveToWebView("https://honorable-overcoat-a54.notion.site/ff45b483da3942558a17c20dca1c4538") },
                contentDescription = "rightArrow"
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 41.dp)
        ) {
            Image(
                painter = if (!isCheckedThird.value) rememberAsyncImagePainter(model = R.drawable.checkbox_false) else rememberAsyncImagePainter(
                    model = R.drawable.checkbox_true
                ),
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        isCheckedThird.value = !isCheckedThird.value
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
                        isCheckedThird.value = !isCheckedThird.value
                    },
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                tint = Gray3,
                modifier = Modifier
                    .padding(end = 18.dp)
                    .clickable { moveToWebView("http://honorable-overcoat-a54.notion.site/8f5c915278a14733b90ef93a7e4af8ec?pvs=4") },
                contentDescription = "rightArrow"
            )
        }
        BasicButton(
            text = "다음",
            end = 18,
            top = 44,
            bottom = 32,
            enabled = isCheckedFirst.value && isCheckedSecond.value
        ) {

        }
    }
}

@Preview
@Composable
private fun AgreementScreenPreview() {
    AgreementScreen() {

    }
}