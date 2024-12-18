package kr.jm.salmal_android.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

object Utils {

    fun requestPermissions(
        list: Array<String>,
        useDeniedMessage: Boolean,
        onGranted: () -> Unit,
        onDenied: (List<String>) -> Unit
    ) {
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
    fun ScreenTransition(
        navController: NavController,
        route: String,
        content: @Composable () -> Unit
    ) {
        val currentRoute = navController.currentDestination?.route == route
        AnimatedVisibility(
            visible = currentRoute,
            enter = slideInHorizontally { fullWidth -> fullWidth },
            exit = slideOutHorizontally { fullWidth -> -fullWidth }
        ) {
            content()
        }
    }

    fun showBottomBar(currentRoute: String?): Boolean {
        return currentRoute in listOf("home", "add", "myPage")
    }

    fun calculateRelativeTime(updateAt: String): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val formatter = DateTimeFormatter.ISO_DATE_TIME
            val updateTime = LocalDateTime.parse(updateAt, formatter)

            val currentTime = LocalDateTime.now()

            val duration = Duration.between(updateTime, currentTime)

            return when {
                duration.toMinutes() < 1 -> "방금 전"
                duration.toHours() < 1 -> "${duration.toMinutes()} 분 전"
                duration.toDays() < 1 -> "${duration.toHours()} 시간 전"
                duration.toDays() < 7 -> "${duration.toDays()} 일 전"
                else -> "${duration.toDays() / 7} 주 전"
            }
        }
        return updateAt
    }

    fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
        return outputStream.toByteArray()
    }

    fun uriToByteArray(context: Context, uri: Uri): ByteArray {
        val inputStream = context.contentResolver.openInputStream(uri) ?: throw IOException("InputStream is null")
        val bitmap = BitmapFactory.decodeStream(inputStream)
        inputStream.close()

        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
        return outputStream.toByteArray()
    }

    fun String.parseID(): Int? {

        val value = this.split(".")[1]

        val replacedValue = value
            .replace("-", "+")
            .replace("_", "/")

        val finalValue = replacedValue.padEnd((replacedValue.length + 3) / 4 * 4, '=')

        val data = try {
            Base64.decode(finalValue, Base64.DEFAULT)
        } catch (e: IllegalArgumentException) {
            return null
        }

        val payload = try {
            JSONObject(String(data))
        } catch (e: Exception) {
            return null
        }

        return payload.optInt("id", -1).takeIf { it != -1 }
    }
}
