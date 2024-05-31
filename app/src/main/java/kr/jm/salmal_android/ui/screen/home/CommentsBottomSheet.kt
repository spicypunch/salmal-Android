package kr.jm.salmal_android.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kr.jm.salmal_android.ui.theme.Pretendard
import kr.jm.salmal_android.ui.theme.gray4
import kr.jm.salmal_android.ui.theme.primaryWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentsBottomSheet() {
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { /*TODO*/ },
        sheetState = sheetState,
        contentColor = gray4
    ) {
        Column {
            Row {
                Text(
                    text = "댓글",
                    fontFamily = Pretendard,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = primaryWhite,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}