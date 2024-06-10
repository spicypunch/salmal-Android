package kr.jm.salmal_android.ui.screen.home.comments

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
fun CommentsUpdateBottomSheet(
    showBottomSheet: () -> Unit,
    onClickUpdate: () -> Unit,
    onClickDelete: () -> Unit,
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
                iconResource = R.drawable.pencil_icon,
                content = "수정",
            ) {
                onClickUpdate()
            }
            BottomSheetRowComponent(
                iconResource = R.drawable.delete_icon,
                content = "삭제",
            ) {
                onClickDelete()
            }
        }
    }

}