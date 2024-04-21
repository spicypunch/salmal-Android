package kr.jm.salmal_android.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import kr.jm.salmal_android.screen.component.CircularProgressBar
import kr.jm.salmal_android.ui.theme.Pretendard
import kr.jm.salmal_android.ui.theme.primaryBlack
import kr.jm.salmal_android.ui.theme.primaryWhite
import kr.jm.salmal_android.utils.Utils
import kr.lifesemantics.salmal_android.R

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val loginResponse = viewModel.loginResponse

    if (viewModel.isLoading.value) {
        CircularProgressBar()
    } else {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(color = primaryBlack),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(start = 46.dp)
                ) {
                    Text(
                        text = "당신의 든든한 쇼핑 메이트",
                        fontFamily = Pretendard,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                        color = primaryWhite,
                    )
                    Image(
                        painter = rememberAsyncImagePainter(model = R.drawable.login_text_image),
                        modifier = Modifier.padding(top = 12.dp),
                        contentDescription = "salmal"
                    )
                }
                Image(
                    painter = rememberAsyncImagePainter(model = R.drawable.login_image),
                    contentDescription = "image"
                )
            }
            Image(
                painter = rememberAsyncImagePainter(model = R.drawable.kakao_logo),
                contentDescription = "kakao_logo",
                modifier = Modifier
                    .padding(top = 94.dp)
                    .clickable {
                        viewModel.requestKakaoToken(context)
                    }
            )
            Image(
                painter = rememberAsyncImagePainter(model = R.drawable.login_reccomend_image),
                modifier = Modifier.padding(top = 21.dp),
                contentDescription = "login_reccomend_image"
            )
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    LoginScreen()
}