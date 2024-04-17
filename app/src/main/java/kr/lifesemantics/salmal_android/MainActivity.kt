package kr.lifesemantics.salmal_android

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import kr.lifesemantics.salmal_android.screen.SetFirstProfileScreen
import kr.lifesemantics.salmal_android.utils.requestPermissions

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)
        val list = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU) {
            arrayListOf(
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.CAMERA,
                Manifest.permission.POST_NOTIFICATIONS
            )
        } else {
            arrayListOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
            )
        }
        requestPermissions(list, false, onGranted = {}, onDenied = {})

        setContent {
            MaterialTheme {
                SetFirstProfileScreen()
            }
        }
    }
}

@Composable
fun App() {

}