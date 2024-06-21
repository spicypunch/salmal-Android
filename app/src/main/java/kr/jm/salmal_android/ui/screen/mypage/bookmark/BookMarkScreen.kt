package kr.jm.salmal_android.ui.screen.mypage.bookmark

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kr.jm.salmal_android.ui.screen.component.LazyGridView
import kr.jm.salmal_android.ui.theme.Pretendard
import kr.jm.salmal_android.ui.theme.primaryBlack
import kr.jm.salmal_android.ui.theme.primaryWhite

@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookMarkScreen(
    onClickBack: () -> Unit,
    onClickItem: (String) -> Unit,
    viewModel: BookMarkViewModel = hiltViewModel()
) {
    remember {
        viewModel.getMyBookMarks()
    }

    val myBookMarks by viewModel.myBookMarks.collectAsState()


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "북마크",
                        fontFamily = Pretendard,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = primaryWhite
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onClickBack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "뒤로가기",
                            tint = primaryWhite
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(primaryBlack)
            )
        }
    ) {
        Box(modifier = Modifier.padding(it).background(primaryBlack).fillMaxSize()) {
            LazyGridView(items = myBookMarks?.votes ?: emptyList()) { id ->
                onClickItem(id)
            }
        }
    }
}