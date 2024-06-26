package kr.jm.salmal_android.ui.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kr.jm.salmal_android.ui.theme.primaryBlack
import kr.jm.salmal_android.ui.theme.primaryGreen

@Composable
fun CircularProgressBar() {
    Box(
        modifier = Modifier.fillMaxSize().background(primaryBlack),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = primaryGreen
        )
    }
}

@Preview
@Composable
fun CircularProgressBarPreview() {
    CircularProgressBar()
}