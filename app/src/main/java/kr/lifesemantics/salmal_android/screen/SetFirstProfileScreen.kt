package kr.lifesemantics.salmal_android.screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import kr.lifesemantics.salmal_android.R
import kr.lifesemantics.salmal_android.screen.component.BasicButton
import kr.lifesemantics.salmal_android.ui.theme.Pretendard
import kr.lifesemantics.salmal_android.ui.theme.black1b
import kr.lifesemantics.salmal_android.ui.theme.primaryBlack
import kr.lifesemantics.salmal_android.ui.theme.primaryGreen
import kr.lifesemantics.salmal_android.ui.theme.primaryWhite
import kr.lifesemantics.salmal_android.ui.theme.white36

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SetFirstProfileScreen() {
    val (nickName, setNickName) = rememberSaveable {
        mutableStateOf("")
    }
    var isEditing by rememberSaveable {
        mutableStateOf(false)
    }
    var showText by rememberSaveable {
        mutableStateOf(false)
    }
    var imageUri by rememberSaveable {
        mutableStateOf<String?>(null)
    }

    val galleryLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
            onResult = { uri ->
                imageUri = uri.toString()
            }
        )

    val openGallery = {
        galleryLauncher.launch("image/*")
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(color = black1b)
    ) {
        Image(
            painter = if (imageUri == null) rememberAsyncImagePainter(model = R.drawable.salmal_icon_circle)
            else rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current).data(imageUri).build()
            ),
            modifier = Modifier
                .size(89.dp)
                .clip(CircleShape)
                .clickable { openGallery() },
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
                textStyle = TextStyle(color = primaryGreen, fontSize = 20.sp),
                singleLine = true,
                modifier = Modifier
                    .padding(top = 18.dp)
                    .width(180.dp)
                    .clickable { keyboardController?.show() },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        isEditing = false
                        keyboardController?.hide()
                    }
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = primaryBlack,
                    unfocusedContainerColor = primaryBlack,
                    disabledContainerColor = primaryBlack,
                    focusedIndicatorColor = primaryGreen,
                    unfocusedIndicatorColor = primaryGreen,
                    cursorColor = primaryGreen,
                )
            )
        } else {
            Text(
                text = nickName.ifBlank { "눌러서 입력" },
                fontFamily = Pretendard,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                color = primaryGreen,
                modifier = Modifier
                    .padding(top = 18.dp)
                    .clickable { isEditing = !isEditing }
            )
        }
        Text(
            text = "이미 존재하는 닉네임이에요",
            fontFamily = Pretendard,
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp,
            color = primaryWhite,
            modifier = Modifier
                .padding(top = 259.dp)
                .alpha(if (showText) 1f else 0f)
        )
        BasicButton(text = "확인", start = 18, end = 18, top = 18, bottom = 32) {

        }
    }
}

@Preview
@Composable
private fun SetFirstProfileScreenPreview() {
    SetFirstProfileScreen()
}