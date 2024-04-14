package kr.lifesemantics.salmal_android.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import kr.lifesemantics.salmal_android.R
import kr.lifesemantics.salmal_android.ui.theme.Pretendard
import kr.lifesemantics.salmal_android.ui.theme.black1b
import kr.lifesemantics.salmal_android.ui.theme.primaryBlack
import kr.lifesemantics.salmal_android.ui.theme.primaryGreen
import kr.lifesemantics.salmal_android.ui.theme.white36

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SetFirstProfileScreen() {
    val (nickName, setNickName) = rememberSaveable {
        mutableStateOf("")
    }
    var isEditing by rememberSaveable {
        mutableStateOf(false)
    }
    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(color = black1b)
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = R.drawable.salmal_icon_circle),
            modifier = Modifier
                .size(89.dp),
            contentScale = ContentScale.Crop,
            contentDescription = "salmal_logo"
        )
        Text(
            text = "닉네임",
            fontFamily = Pretendard,
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp,
            color = white36,
            modifier = Modifier.padding(top = 82.dp)
        )
        if (isEditing) {
            TextField(
                value = nickName,
                onValueChange = setNickName,
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = primaryBlack
                )
            )
        } else {
            Text(
                text = "눌러서 입력",
                fontFamily = Pretendard,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                color = primaryGreen,
                modifier = Modifier
                    .padding(top = 82.dp)
                    .clickable { isEditing = !isEditing }
            )
        }
    }
}

@Preview
@Composable
private fun SetFirstProfileScreenPreview() {
    SetFirstProfileScreen()
}