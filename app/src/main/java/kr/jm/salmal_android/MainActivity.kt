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
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kr.jm.salmal_android.data.domain.BottomNavItem
import kr.jm.salmal_android.ui.screen.add.AddScreen
import kr.jm.salmal_android.ui.screen.agreement.AgreementScreen
import kr.jm.salmal_android.ui.screen.component.MyBottomNavigation
import kr.jm.salmal_android.ui.screen.home.HomeScreen
import kr.jm.salmal_android.ui.screen.login.LoginScreen
import kr.jm.salmal_android.ui.screen.mypage.MyPageScreen
import kr.jm.salmal_android.ui.screen.profile.SetFirstProfileScreen
import kr.jm.salmal_android.ui.screen.splash.SplashScreen
import kr.jm.salmal_android.ui.screen.webview.WebViewScreen
import kr.jm.salmal_android.utils.Utils
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
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    Scaffold(
        bottomBar = {
            if (currentRoute != null && Utils.showBottomBar(currentRoute)) {
                MyBottomNavigation(navController)
            }
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            NavHost(navController = navController, startDestination = "splash") {
                composable(route = "splash") {
                    Utils.ScreenTransition(navController = navController, route = "splash") {
                        SplashScreen { result ->
                            if (result) {
                                navController.navigate("home") {
                                    popUpTo("splash") { inclusive = true }
                                }
                            } else {
                                navController.navigate("login") {
                                    popUpTo("splash") { inclusive = true }
                                }
                            }
                        }
                    }
                }
                composable(route = "login") {
                    Utils.ScreenTransition(navController = navController, route = "login") {
                        LoginScreen { result ->
                            if (result) {
                                navController.navigate("home")
                            } else {
                                navController.navigate("agreement")
                            }
                        }
                    }
                }
                composable(route = BottomNavItem.Home.route) {
                    HomeScreen()
                }
                composable(route = BottomNavItem.Add.route) {
                    AddScreen()
                }
                composable(route = BottomNavItem.MyPage.route) {
                    MyPageScreen()
                }
                composable(route = "agreement") {
                    Utils.ScreenTransition(navController = navController, route = "agreement") {
                        AgreementScreen(
                            moveToWebView = { url ->
                                navController.navigate(
                                    "webview/${
                                        URLEncoder.encode(
                                            url,
                                            StandardCharsets.UTF_8.toString()
                                        )
                                    }"
                                )
                            },
                            moveToSetProfile = {
                                navController.navigate("setprofile")
                            })
                    }
                }
                composable(route = "setprofile") {
                    Utils.ScreenTransition(navController = navController, route = "setprofile") {
                        SetFirstProfileScreen() {
                            navController.navigate("home")
                        }
                    }
                }
                composable(route = "webview/{url}") { backStackEntry ->
                    Utils.ScreenTransition(navController = navController, route = "webview/{url}") {
                        val url = backStackEntry.arguments?.getString("url")
                        if (url != null) {
                            WebViewScreen(
                                url = URLDecoder.decode(
                                    url,
                                    StandardCharsets.UTF_8.toString()
                                )
                            ) {
                                navController.popBackStack()
                            }
                        }
                    }
                }
            }
        }
    }
}