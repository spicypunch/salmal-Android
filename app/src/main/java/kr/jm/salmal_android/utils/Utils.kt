package kr.jm.salmal_android.utils

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Utils {

    fun requestPermissions(list: Array<String>, useDeniedMessage: Boolean, onGranted: () -> Unit, onDenied: (List<String>) -> Unit) {
        val permissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                onGranted()
            }

            override fun onPermissionDenied(deniedPermissions: List<String>) {
                onDenied(deniedPermissions)
            }
        }

        val builder = TedPermission.create()
            .setPermissionListener(permissionListener)

        builder.setPermissions(*list)

        if (useDeniedMessage) {
            builder.setDeniedMessage("권한을 허용해주세요. [설정] > [앱 및 알림] > [고급] > [앱 권한]")
        }

        builder.check()
    }

    fun createImageFile(context: Context): Uri? {
        val timeStamp = SimpleDateFormat("yyMMdd_HHmm ss", Locale.KOREA).format(Date())
        val content = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "img_$timeStamp.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
        }
        return context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, content)
    }

    @Composable
    fun ScreenTransition(navController: NavController, route: String, content: @Composable () -> Unit) {
        val currentRoute = navController.currentDestination?.route == route
        AnimatedVisibility(
            visible = currentRoute,
            enter = slideInHorizontally { fullWidth -> fullWidth },
            exit = slideOutHorizontally { fullWidth -> -fullWidth }
        ) {
            content()
        }
    }
}
