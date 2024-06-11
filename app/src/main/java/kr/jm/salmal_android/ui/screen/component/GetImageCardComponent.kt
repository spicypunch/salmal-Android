package kr.jm.salmal_android.ui.screen.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import kr.jm.salmal_android.ui.theme.primaryBlack
import kr.jm.salmal_android.ui.theme.primaryWhite

@Composable
fun GetImageCardComponent(
    iconResource: Int,
    text: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .height(150.dp)
            .width(110.dp)
            .clickable {
                onClick()
            },
        colors = CardDefaults.cardColors(primaryWhite)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = rememberAsyncImagePainter(model = iconResource),
                modifier = Modifier.size(40.dp),
                tint = primaryBlack,
                contentDescription = "icon"
            )
            Text(
                text = text,
                fontFamily = Pretendard,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = primaryBlack,
                modifier = Modifier.padding(vertical = 26.dp)

            )
        }
    }
}