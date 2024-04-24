package kr.jm.salmal_android.screen

import android.Manifest
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import kr.jm.salmal_android.screen.component.BasicButton
import kr.jm.salmal_android.ui.theme.Gray4
import kr.jm.salmal_android.ui.theme.Pretendard
import kr.jm.salmal_android.ui.theme.black1b
import kr.jm.salmal_android.ui.theme.primaryBlack
import kr.jm.salmal_android.ui.theme.primaryGreen
import kr.jm.salmal_android.ui.theme.primaryWhite
import kr.jm.salmal_android.ui.theme.white36
import kr.jm.salmal_android.utils.Utils
import kr.lifesemantics.salmal_android.R

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
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
    var imageUri: Uri? by rememberSaveable {
        mutableStateOf(null)
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    /**
     * Modal
     */
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    /**
     * Gallery
     */
    val galleryLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
            onResult = { uri ->
                if (uri != null) {
                    imageUri = uri
                }
            }
        )
    val openGallery = {
        galleryLauncher.launch("image/*")
    }
    /**
     * Camera
     */
    var cameraImageFile: Uri? by rememberSaveable {
        mutableStateOf(null)
    }
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            imageUri = cameraImageFile
        }
    }

    /**
     * PermissionList
     */
    val list = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU) {
        arrayListOf(
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.CAMERA,
        )
    } else {
        arrayListOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
        )
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {
        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(color = black1b)
                .padding(it)
        ) {
            Image(
                painter = if (imageUri == null) rememberAsyncImagePainter(
                    model = R.drawable.salmal_icon_circle
                )
                else rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current).data(imageUri).build()
                ),
                modifier = Modifier
                    .size(89.dp)
                    .clip(CircleShape)
                    .clickable {
                        Utils.requestPermissions(
                            list,
                            true,
                            onGranted = {
                                showBottomSheet = true
                            },
                            onDenied = {
                            }
                        )
                    },
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
            BasicButton(text = "확인", start = 18, end = 18, top = 18, bottom = 32, enabled = true) {

            }

            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showBottomSheet = false },
                    sheetState = sheetState,
                    containerColor = Gray4
                ) {
                    Column(
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable {
                                openGallery()
                                showBottomSheet = false
                            }
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(model = R.drawable.modal_gallery_icon),
                                modifier = Modifier.padding(start = 18.dp),
                                contentDescription = "modal_gallery_icon"
                            )
                            Text(
                                text = "사진첩에서 선택하기",
                                fontFamily = Pretendard,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp,
                                color = primaryWhite,
                                modifier = Modifier.padding(start = 6.dp)
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(top = 32.dp)
                                .clickable {
                                    cameraImageFile = Utils.createImageFile(context)
                                    cameraImageFile?.let {
                                        cameraLauncher.launch(it)
                                    }
                                    showBottomSheet = false
                                }
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(model = R.drawable.modal_camera_icon),
                                modifier = Modifier.padding(start = 18.dp),
                                contentDescription = "modal_camera_icon"
                            )
                            Text(
                                text = "촬영하기",
                                fontFamily = Pretendard,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp,
                                color = primaryWhite,
                                modifier = Modifier.padding(start = 6.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(64.dp))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun SetFirstProfileScreenPreview() {
    SetFirstProfileScreen()
}