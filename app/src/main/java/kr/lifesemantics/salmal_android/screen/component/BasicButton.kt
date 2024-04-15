package kr.lifesemantics.salmal_android.screen.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kr.lifesemantics.salmal_android.ui.theme.primaryBlack
import kr.lifesemantics.salmal_android.ui.theme.primaryGreen

@Composable
fun BasicButton(
    text: String,
    top: Int = 0,
    bottom: Int = 0,
    start: Int= 0,
    end: Int = 0,
    onClicked: () -> Unit
) {
    Button(
        onClick = {
            onClicked()
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = start.dp)
            .padding(end = end.dp)
            .padding(top = top.dp)
            .padding(bottom = bottom.dp)
            .height(60.dp),
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(containerColor = primaryGreen)
    ) {
        Text(
            text = text,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = primaryBlack,
            modifier = Modifier.weight(3f),
            textAlign = TextAlign.Center
        )

    }
}