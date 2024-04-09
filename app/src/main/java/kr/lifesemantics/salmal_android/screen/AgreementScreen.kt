package kr.lifesemantics.salmal_android.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import kr.lifesemantics.salmal_android.R
import kr.lifesemantics.salmal_android.ui.theme.Gray2
import kr.lifesemantics.salmal_android.ui.theme.Gray3
import kr.lifesemantics.salmal_android.ui.theme.Pretendard
import kr.lifesemantics.salmal_android.ui.theme.primaryBlack
import kr.lifesemantics.salmal_android.ui.theme.primaryGreen
import kr.lifesemantics.salmal_android.ui.theme.primaryWhite
import kr.lifesemantics.salmal_android.ui.theme.primaryYellow

@Composable
fun AgreementScreen() {
    var isChecked = rememberSaveable {
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
            painter = rememberAsyncImagePainter(model = R.drawable.salmal_icon_transparen),
            modifier = Modifier.size(size = 94.dp),
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
            Checkbox(checked = isChecked.value, onCheckedChange = {
                isChecked.value = !isChecked.value
            })
            Text(
                text = "약관 전체동의",
                fontFamily = Pretendard,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = primaryWhite,
                modifier = Modifier.padding(start = 14.dp)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = isChecked.value, onCheckedChange = {
                isChecked.value = !isChecked.value
            })
            Text(
                text = "이용약관동의(필수)",
                fontFamily = Pretendard,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = primaryWhite,
                modifier = Modifier.padding(start = 14.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                tint = Gray3,
                modifier = Modifier.padding(end = 18.dp),
                contentDescription = "rightArrow"
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = isChecked.value, onCheckedChange = {
                isChecked.value = !isChecked.value
            })
            Text(
                text = "개인정보 수집 밎 이용동의(필수)",
                fontFamily = Pretendard,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = primaryWhite,
                modifier = Modifier.padding(start = 14.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                tint = Gray3,
                modifier = Modifier.padding(end = 18.dp),
                contentDescription = "rightArrow"
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = isChecked.value, onCheckedChange = {
                isChecked.value = !isChecked.value
            })
            Text(
                text = "E-mail 및 SMS 광고성 정보 수신동의(선택)",
                fontFamily = Pretendard,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = primaryWhite,
                modifier = Modifier.padding(start = 14.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                tint = Gray3,
                modifier = Modifier.padding(end = 18.dp),
                contentDescription = "rightArrow"
            )
        }
        Button(
            onClick = {
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 18.dp)
                .padding(top = 44.dp)
                .padding(bottom = 32.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = primaryGreen)
        ) {
            Text(
                text = "다음",
                fontSize = 24.sp,
                color = primaryBlack,
                modifier = Modifier.weight(3f),
                textAlign = TextAlign.Center
            )

        }
    }
}

@Preview
@Composable
private fun AgreementScreenPreview() {
    AgreementScreen()
}