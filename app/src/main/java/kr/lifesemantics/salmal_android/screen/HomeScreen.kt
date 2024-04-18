package kr.lifesemantics.salmal_android.screen

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val list = arrayListOf(Manifest.permission.POST_NOTIFICATIONS)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "일기 쓰기") },
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            Text(text = "종마루")
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}