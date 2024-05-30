package kr.jm.salmal_android.ui.screen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter
import kr.jm.salmal_android.ui.theme.Pretendard
import kr.jm.salmal_android.ui.theme.black50
import kr.jm.salmal_android.ui.theme.gray1
import kr.jm.salmal_android.ui.theme.gray2
import kr.jm.salmal_android.ui.theme.primaryBlack
import kr.lifesemantics.salmal_android.R

@Composable
fun VoteReportDialog(
    showVoteReport: () -> Unit,
    onClickReason: (String) -> Unit
) {
    Dialog(
        onDismissRequest = { showVoteReport() }
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(
                    color = gray1,
                    shape = RoundedCornerShape(5)
                )
                .height(550.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = R.drawable.salmal_icon),
                modifier = Modifier
                    .size(80.dp)
                    .padding(top = 24.dp),
                contentDescription = "salmal_icon"
            )
            Text(
                text = "신고 이유",
                fontFamily = Pretendard,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = primaryBlack,
                modifier = Modifier.padding(top = 20.dp)
            )
            Text(
                text = "욕설/인신공격/비방",
                fontFamily = Pretendard,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                color = black50,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClickReason("욕설/인신공격/비방") }
            )
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 8.dp),
                thickness = 1.dp,
                color = gray2
            )
            Text(
                text = "개인정보/저작권 침해",
                fontFamily = Pretendard,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                color = black50,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClickReason("개인정보/저작권 침해") }
            )
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 8.dp),
                thickness = 1.dp,
                color = gray2
            )
            Text(
                text = "음란성/선정성 게시물",
                fontFamily = Pretendard,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                color = black50,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClickReason("음란성/선정성 게시물") }
            )
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 8.dp),
                thickness = 1.dp,
                color = gray2
            )
            Text(
                text = "영리목적/홍보성 게시물",
                fontFamily = Pretendard,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                color = black50,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClickReason("영리목적/홍보성 게시물") }
            )
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 8.dp),
                thickness = 1.dp,
                color = gray2
            )
            Text(
                text = "기타",
                fontFamily = Pretendard,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                color = black50,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClickReason("기타") }
            )
            Button(
                onClick = { showVoteReport() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 24.dp, bottom = 16.dp),
                shape = RoundedCornerShape(25),
                colors = ButtonDefaults.buttonColors(
                    containerColor = gray2,
                )
            ) {
                Text(text = "취소", color = primaryBlack)
            }
        }
    }
}