package kr.jm.salmal_android.ui.screen.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import kr.jm.salmal_android.ui.theme.Pretendard
import kr.jm.salmal_android.ui.theme.primaryWhite

@Composable
fun BottomSheetRowComponent(
    iconResource: Int,
    content: String,
    onClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 41.dp, bottom = 8.dp)
            .clickable { onClicked() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            painter = rememberAsyncImagePainter(model = iconResource),
            tint = primaryWhite,
            contentDescription = "user_block_icon"
        )
        Text(
            text = content,
            fontFamily = Pretendard,
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            color = primaryWhite,
            modifier = Modifier.padding(start = 6.dp)
        )
    }
}