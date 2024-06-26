package kr.jm.salmal_android.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kr.jm.salmal_android.ui.screen.component.BottomSheetRowComponent
import kr.jm.salmal_android.ui.theme.gray4
import kr.lifesemantics.salmal_android.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportBottomSheet(
    showBottomSheet: () -> Unit,
    voteReport: () -> Unit,
    userBan: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(true)

    ModalBottomSheet(
        onDismissRequest = { showBottomSheet() },
        sheetState = sheetState,
        containerColor = gray4,
        modifier = Modifier.navigationBarsPadding()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(WindowInsets.navigationBars.asPaddingValues())
        ) {
            BottomSheetRowComponent(
                iconResource = R.drawable.user_report_icon,
                content = "해당 게시물 신고하기",
            ) {
                voteReport()
            }
            BottomSheetRowComponent(
                iconResource = R.drawable.user_block_icon,
                content = "이 사용자 차단하기",
            ) {
                userBan()
            }
        }
    }
}