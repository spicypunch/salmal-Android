package kr.jm.salmal_android.ui.screen.home

import android.Manifest
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import kr.jm.salmal_android.ui.screen.home.vote.VotesScreen
import kr.jm.salmal_android.ui.theme.primaryBlack
import kr.jm.salmal_android.utils.Utils.requestPermissions
import kr.lifesemantics.salmal_android.R

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    viewModel.getMyInfo()

    val snackbarHostState = remember { SnackbarHostState() }
    val permissionList =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) arrayOf(Manifest.permission.POST_NOTIFICATIONS) else arrayOf()

    var tabIndex by remember {
        mutableIntStateOf(0)
    }

    requestPermissions(
        list = permissionList,
        useDeniedMessage = true,
        onGranted = {
        },
        onDenied = {
        })

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(color = primaryBlack)
        ) {
            Row(modifier = Modifier.padding(top = 8.dp)) {
                Image(
                    painter = rememberAsyncImagePainter(model = if (tabIndex == 0) R.drawable.home_color else R.drawable.home_basic),
                    modifier = Modifier
                        .padding(start = 18.dp)
                        .clickable { tabIndex = 0 },
                    contentDescription = "tab_home"
                )
                Image(
                    painter = rememberAsyncImagePainter(model = if (tabIndex == 1) R.drawable.best_color else R.drawable.best_basic),
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .clickable { tabIndex = 1 },
                    contentDescription = "tab_best"
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = rememberAsyncImagePainter(model = R.drawable.bell_basic),
                    modifier = Modifier.padding(end = 18.dp),
                    contentDescription = "tab_bell"
                )
            }

            when (tabIndex) {
                0 -> {
                    VotesScreen("HOME", snackbarHostState)
                }

                1 -> {
                    VotesScreen("BEST", snackbarHostState)
                }
            }
        }
    }
}