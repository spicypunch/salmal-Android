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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import kr.lifesemantics.salmal_android.R
import kr.lifesemantics.salmal_android.ui.theme.Pretendard
import kr.lifesemantics.salmal_android.ui.theme.primaryBlack
import kr.lifesemantics.salmal_android.ui.theme.primaryGreen
import kr.lifesemantics.salmal_android.ui.theme.primaryYellow

@Composable
fun LoginScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(color = primaryBlack),
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = R.drawable.salmal_icon),
            modifier = Modifier.size(size = 86.dp),
            contentDescription = "logo"
        )
        Text(
            text = "든든한 쇼핑 메이트",
            fontFamily = Pretendard,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 48.dp),
            color = primaryGreen
        )
        Text(
            text = "살말",
            fontFamily = Pretendard,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 40.sp,
            modifier = Modifier.padding(top = 12.dp),
            color = primaryGreen
        )
        Image(
            painter = rememberAsyncImagePainter(model = R.drawable.login_image1),
            contentDescription = "카카오 로그인 유도",
            modifier = Modifier.padding(top = 146.dp)
        )
        Button(
            onClick = {
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(top = 16.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = primaryYellow)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = R.drawable.kakao_icon),
                    contentDescription = "kakaoIcon",
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "카카오톡으로 시작하기",
                    fontSize = 16.sp,
                    color = primaryBlack,
                    modifier = Modifier.weight(3f),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    LoginScreen()
}