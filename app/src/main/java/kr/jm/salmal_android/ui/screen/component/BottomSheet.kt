package kr.jm.salmal_android.ui.screen.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import kr.jm.salmal_android.ui.theme.gray4
import kr.jm.salmal_android.ui.theme.Pretendard
import kr.jm.salmal_android.ui.theme.primaryWhite
import kr.lifesemantics.salmal_android.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    showBottomSheet: (Boolean) -> Unit,
    voteReport: () -> Unit,
    userBan: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { showBottomSheet(false) },
        sheetState = sheetState,
        containerColor = gray4
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickable { voteReport() }, // Ensure consistent padding
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            )
            {
                Icon(
                    painter = rememberAsyncImagePainter(model = R.drawable.user_report_icon),
                    tint = primaryWhite,
                    contentDescription = "user_report_icon"
                )
                Text(
                    text = "해당 게시물 신고하기",
                    fontFamily = Pretendard,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    color = primaryWhite,
                    modifier = Modifier.padding(start = 6.dp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 41.dp)
                    .clickable { userBan() }, // Ensure consistent padding
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    painter = rememberAsyncImagePainter(model = R.drawable.user_block_icon),
                    tint = primaryWhite,
                    contentDescription = "user_block_icon"
                )
                Text(
                    text = "이 사용자 차단하기",
                    fontFamily = Pretendard,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    color = primaryWhite,
                    modifier = Modifier.padding(start = 6.dp)
                )
            }
            Spacer(modifier = Modifier.height(85.dp))
        }
    }
}