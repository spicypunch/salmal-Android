package kr.jm.salmal_android.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import kr.jm.salmal_android.ui.theme.primaryBlack
import kr.lifesemantics.salmal_android.R

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.background(color = primaryBlack)
    ) {
        Image(painter = rememberAsyncImagePainter(model = R.drawable.salmal_icon), contentDescription = "splashIcon")
    }
}

@Preview
@Composable
private fun SplashPreview() {
    SplashScreen()
}