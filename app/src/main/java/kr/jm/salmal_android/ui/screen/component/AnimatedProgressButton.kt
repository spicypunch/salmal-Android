import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kr.jm.salmal_android.ui.theme.gray3
import kr.jm.salmal_android.ui.theme.primaryGreen
import kr.jm.salmal_android.ui.theme.primaryWhite

@Composable
fun AnimatedProgressButton(
    modifier: Modifier = Modifier,
    progress: Float,
    buttonText: String,
    top: Int = 0,
    bottom: Int = 0,
    start: Int = 0,
    end: Int = 0,
    status: String,
    onClicked: () -> Unit
) {
    val animatedProgress by animateFloatAsState(
        targetValue = if (status == "NONE") 0f else progress,
        animationSpec = tween(
            durationMillis = 1000,
            easing = LinearOutSlowInEasing
        ),
        label = ""
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = start.dp, end = end.dp, top = top.dp, bottom = bottom.dp)
            .clickable {
                onClicked()
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(color = gray3, shape = RoundedCornerShape(50.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(animatedProgress)
                    .background(color = primaryGreen, shape = RoundedCornerShape(50.dp))
            )
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = if (status == "NONE") Arrangement.Center else Arrangement.SpaceBetween
            ) {
                if (status == "NONE") {
                    Text(
                        text = buttonText,
                        fontSize = 24.sp,
                        color = primaryWhite,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                } else {
                    Text(
                        text = buttonText,
                        fontSize = 24.sp,
                        color = primaryWhite,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                    Text(
                        text = "${(progress * 100).toInt()}%",
                        fontSize = 24.sp,
                        color = primaryWhite,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                }
            }
        }
    }
}