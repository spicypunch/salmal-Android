package kr.jm.salmal_android.ui.screen.register

import android.Manifest
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kr.jm.salmal_android.ui.screen.component.GetImageCardComponent
import kr.jm.salmal_android.ui.theme.Pretendard
import kr.jm.salmal_android.ui.theme.primaryBlack
import kr.jm.salmal_android.ui.theme.primaryWhite
import kr.jm.salmal_android.ui.theme.white36
import kr.jm.salmal_android.utils.Utils
import kr.lifesemantics.salmal_android.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetImageUriScreen(
    onClickCancel: () -> Unit
) {
    val permissionList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
        arrayOf(
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED,
            Manifest.permission.CAMERA
        )
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arrayOf(
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.CAMERA
        )
    } else {
        arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        )
    }

    val context = LocalContext.current

    var imageUri: Uri? by remember {
        mutableStateOf(null)
    }
    var cameraImageFile: Uri? by remember {
        mutableStateOf(null)
    }

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
    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                imageUri = cameraImageFile
            }
        }

    LaunchedEffect(imageUri) {
        imageUri?.let {
            Log.e("imageUri", imageUri.toString())
        }
    }


    Utils.requestPermissions(
        list = permissionList,
        useDeniedMessage = true,
        onGranted = {
        },
        onDenied = {
            Toast.makeText(context, "권한을 허용해 주세요.", Toast.LENGTH_SHORT).show()
        })

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "사진 추가",
                        fontFamily = Pretendard,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = primaryWhite
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(primaryBlack),
                actions = {
                    IconButton(onClick = { onClickCancel() }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            tint = primaryWhite,
                            contentDescription = "close"
                        )
                    }
                }
            )
        },
        containerColor = primaryBlack
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "투표 등록 사진 선택",
                fontFamily = Pretendard,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = primaryWhite
            )
            Text(
                text = "투표 등록에 사용할 사진을 선택해 주세요",
                fontFamily = Pretendard,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = white36
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                GetImageCardComponent(R.drawable.camera_icon, "카메라") {
                    cameraImageFile = Utils.createImageFile(context)
                    cameraImageFile?.let { uri ->
                        cameraLauncher.launch(uri)
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                GetImageCardComponent(R.drawable.gallery_icon, "갤러리") {
                    openGallery()
                }
            }
        }
    }
}