package kr.jm.salmal_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kr.jm.salmal_android.screen.agreement.AgreementScreen
import kr.jm.salmal_android.screen.home.HomeScreen
import kr.jm.salmal_android.screen.login.LoginScreen
import kr.jm.salmal_android.screen.webview.WebViewScreen
import kr.lifesemantics.salmal_android.R
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)
        setContent {
            MaterialTheme {
                App()
            }
        }
    }
}

@Composable
fun App() {
    val navController = rememberNavController()
    val context = LocalContext.current
    Scaffold(

    ) {
        Box(modifier = Modifier.padding(it)) {
            NavHost(navController = navController, startDestination = "agreement") {
                composable(route = "login") {
                    LoginScreen { result ->
                        if (result) {
                            navController.navigate("home")
                        } else {
                            navController.navigate("agreement")
                        }
                    }
                }
                composable(route = "agreement") {
                    AgreementScreen { url ->
                        navController.navigate(
                            "webview/${
                                URLEncoder.encode(
                                    url,
                                    StandardCharsets.UTF_8.toString()
                                )
                            }"
                        )
                    }
                }
                composable(route = "home") {
                    HomeScreen()
                }
                composable(route = "webview/{url}") { backStackExtry ->
                    val url = backStackExtry.arguments?.getString("url")
                    if (url != null) {
                        WebViewScreen(url = URLDecoder.decode(url, StandardCharsets.UTF_8.toString())) {
                            navController.popBackStack()
                        }
                    }
                }
            }
        }
    }
}