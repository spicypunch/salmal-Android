package kr.jm.salmal_android.ui.screen.mypage.setting.edit

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kr.jm.salmal_android.data.request.UpdateMyInfoRequest
import kr.jm.salmal_android.ui.screen.component.CancelButtonDialog
import kr.jm.salmal_android.ui.theme.Pretendard
import kr.jm.salmal_android.ui.theme.gray2
import kr.jm.salmal_android.ui.theme.gray4
import kr.jm.salmal_android.ui.theme.primaryBlack
import kr.jm.salmal_android.ui.theme.primaryGreen
import kr.jm.salmal_android.ui.theme.primaryWhite
import kr.jm.salmal_android.ui.theme.transparent
import kr.jm.salmal_android.ui.theme.white80

@SuppressLint("RememberReturnType", "CoroutineCreationDuringComposition")
@Composable
fun EditMyInfoScreen(
    goToLogin: () -> Unit,
    goBack: () -> Unit,
    viewModel: EditMyInfoViewModel = hiltViewModel()
) {

    remember {
        viewModel.getMyInfo()
    }
    val myInfo = viewModel.myInfo.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    val (nickName, setNickName) = remember {
        mutableStateOf(myInfo.value?.nickName ?: "")
    }

    val (introduce, setIntroduce) = remember {
        mutableStateOf(myInfo.value?.introduction ?: "")
    }

    var cancelDialog by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(viewModel.logoutResult) {
        viewModel.logoutResult.collectLatest {
            if (it) {
                goToLogin()
            }
        }
    }

    LaunchedEffect(viewModel.withdrawalResult) {
        viewModel.withdrawalResult.collectLatest {
            if (it) {
                goToLogin()
            }
        }
    }

    LaunchedEffect(myInfo.value) {
        myInfo.value?.let {
            setNickName(it.nickName)
            setIntroduce(it.introduction)
        }
    }

    LaunchedEffect(viewModel.updateMyInfoResult) {
        viewModel.updateMyInfoResult.collectLatest {
            if (it) {
                goBack()
            }
        }
    }

    LaunchedEffect(viewModel.duplicateNickname) {
        viewModel.duplicateNickname.collectLatest {
            if (it) {
                snackbarHostState.showSnackbar("중복된 닉네임입니다.")
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(primaryBlack)
                .padding(it)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "취소",
                    fontFamily = Pretendard,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    color = white80,
                    modifier = Modifier
                        .clickable {
                            goBack()
                        }
                )
                Text(
                    text = "개인정보 수정",
                    fontFamily = Pretendard,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = primaryWhite,
                    modifier = Modifier
                )

                Text(
                    text = "확인",
                    fontFamily = Pretendard,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    color = primaryGreen,
                    modifier = Modifier
                        .clickable {
                            viewModel.updateMyInfo(
                                UpdateMyInfoRequest(
                                    nickName = nickName,
                                    introduction = introduce
                                )
                            )
                        }
                )
            }
            Spacer(modifier = Modifier.height(100.dp))
            Image(
                painter = rememberAsyncImagePainter(model = myInfo.value?.imageUrl ?: ""),
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                contentDescription = "profile_image"
            )
            Text(
                text = "사진 변경",
                fontFamily = Pretendard,
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal,
                color = primaryGreen,
                modifier = Modifier
                    .clickable {

                    }
            )

            Spacer(modifier = Modifier.height(130.dp))

            OutlinedTextField(
                value = nickName,
                onValueChange = setNickName,
                textStyle = TextStyle(
                    color = primaryWhite,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start
                ),
                label = {
                    Text(
                        text = "닉네임",
                        fontFamily = Pretendard,
                        fontSize = 16.sp,
                        color = primaryWhite
                    )
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = gray4,
                    unfocusedContainerColor = gray4,
                    focusedIndicatorColor = primaryGreen,
                    unfocusedIndicatorColor = transparent
                )
            )

            Spacer(modifier = Modifier.height(70.dp))

            OutlinedTextField(
                value = introduce,
                onValueChange = setIntroduce,
                textStyle = TextStyle(
                    color = primaryWhite,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start
                ),
                label = {
                    Text(
                        text = "한줄 소개",
                        fontFamily = Pretendard,
                        fontSize = 16.sp,
                        color = primaryWhite
                    )
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = gray4,
                    unfocusedContainerColor = gray4,
                    focusedIndicatorColor = primaryGreen,
                    unfocusedIndicatorColor = transparent
                )
            )

            Spacer(modifier = Modifier.height(150.dp))

            Text(
                text = "로그아웃",
                fontFamily = Pretendard,
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal,
                color = primaryGreen,
                modifier = Modifier
                    .clickable {
                        viewModel.logout()
                    }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "서비스 탈퇴",
                fontFamily = Pretendard,
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal,
                color = gray2,
                modifier = Modifier
                    .clickable {
                        cancelDialog = true
                    }
            )
        }
    }
    if (cancelDialog) {
        CancelButtonDialog(content = "정말로 탈퇴하시겠습니까?") {
            if (it) {
                viewModel.withdrawal()
            }
            cancelDialog = false
        }
    }
}