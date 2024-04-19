package kr.jm.salmal_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import kr.jm.salmal_android.screen.LoginScreen
import kr.jm.salmal_android.screen.SetFirstProfileScreen
import kr.lifesemantics.salmal_android.R

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)
        setContent {
            MaterialTheme {
                LoginScreen()
            }
        }
    }
}

@Composable
fun App() {

}