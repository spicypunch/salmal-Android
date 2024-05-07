package kr.jm.salmal_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kr.jm.salmal_android.data.domain.BottomNavItem
import kr.jm.salmal_android.screen.agreement.AgreementScreen
import kr.jm.salmal_android.screen.home.HomeScreen
import kr.jm.salmal_android.screen.login.LoginScreen
import kr.jm.salmal_android.screen.profile.SetFirstProfileScreen
import kr.jm.salmal_android.screen.splash.SplashScreen
import kr.jm.salmal_android.screen.webview.WebViewScreen
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
    val navItems = listOf(
        BottomNavItem.Home,
        BottomNavItem.Add,
        BottomNavItem.MyPage
    )
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                navItems.forEach { screen ->
                    NavigationBarItem(
                        selected = currentRoute == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                navController.graph.startDestinationRoute?.let {
                                    popUpTo(it) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        alwaysShowLabel = false,
                        icon = {
                            Icon(painter = painterResource(id = screen.icon), contentDescription = screen.title)
                        }
                    )
                }
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
                composable(route = "home") {
                    Utils.ScreenTransition(navController = navController, route = "home") {
                        HomeScreen()
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