package kr.jm.salmal_android.ui.screen.mypage.setting.delete

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kr.jm.salmal_android.ui.screen.component.CancelButtonDialog
import kr.jm.salmal_android.ui.screen.component.LazyGridView
import kr.jm.salmal_android.ui.theme.Pretendard
import kr.jm.salmal_android.ui.theme.primaryBlack
import kr.jm.salmal_android.ui.theme.primaryWhite

@SuppressLint("RememberReturnType", "UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteMyVoteScreen(
    onClickBack: () -> Unit,
    viewModel: DeleteMyVoteViewModel = hiltViewModel()
) {
    remember {
        viewModel.getMyVotes()
    }
    val myVotes = viewModel.myVotes.collectAsState().value

    var cancelDialog by remember {
        mutableStateOf(false)
    }

    var voteId by remember {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "삭제",
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
                            contentDescription = "취소",
                            tint = primaryWhite
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(primaryBlack)
            )
        }
    ) {
        Box(
            modifier = Modifier
                .background(primaryBlack)
                .fillMaxSize()
                .padding(it)
        ) {
            LazyGridView(items = myVotes?.votes ?: emptyList()) { id ->
                cancelDialog = true
                voteId = id
            }
        }
    }

    if (cancelDialog) {
        CancelButtonDialog(content = "삭제하시겠습니까?") {
            if (it) {
                viewModel.deleteVote(voteId)
            }
            cancelDialog = false
        }
    }
}