package kr.jm.salmal_android.ui.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kr.jm.salmal_android.ui.theme.primaryBlack
import kr.lifesemantics.salmal_android.R

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    loginResult: (Boolean) -> Unit
) {
    viewModel.attemptLogin()

    LaunchedEffect(Unit) {
        viewModel.loginResult.collectLatest {
            delay(2000)
            loginResult(it)
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(color = primaryBlack)
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = R.drawable.salmal_icon),
            contentDescription = "splashIcon"
        )
    }
}