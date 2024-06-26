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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import kr.jm.salmal_android.data.domain.BottomNavItem
import kr.jm.salmal_android.ui.screen.agreement.AgreementScreen
import kr.jm.salmal_android.ui.screen.component.MyBottomNavigation
import kr.jm.salmal_android.ui.screen.home.HomeScreen
import kr.jm.salmal_android.ui.screen.login.LoginScreen
import kr.jm.salmal_android.ui.screen.mypage.MyPageScreen
import kr.jm.salmal_android.ui.screen.mypage.bookmark.BookMarkScreen
import kr.jm.salmal_android.ui.screen.mypage.detail.SingleVoteScreen
import kr.jm.salmal_android.ui.screen.mypage.setting.SettingScreen
import kr.jm.salmal_android.ui.screen.mypage.setting.ban_list.BanListScreen
import kr.jm.salmal_android.ui.screen.mypage.setting.delete.DeleteMyVoteScreen
import kr.jm.salmal_android.ui.screen.mypage.setting.edit.EditMyInfoScreen
import kr.jm.salmal_android.ui.screen.profile.SetFirstProfileScreen
import kr.jm.salmal_android.ui.screen.register.GetImageUriScreen
import kr.jm.salmal_android.ui.screen.register.ImageRegisterScreen
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
                composable(route = BottomNavItem.Register.route) {
                    GetImageUriScreen(onClickCancel = {
                        navController.popBackStack()
                    },
                        sendUriInfo = { uri ->
                            val uriEncode =
                                URLEncoder.encode(uri.toString(), StandardCharsets.UTF_8.toString())
                            navController.navigate("register/$uriEncode")
                        })
                }
                composable(
                    route = "register/{uri}",
                    arguments = listOf(navArgument("uri") {
                        type = NavType.StringType
                    })
                ) { backStackEntry ->
                    val uri = backStackEntry.arguments?.getString("uri")
                    uri?.let { uriInfo ->
                        val uriDecode =
                            URLDecoder.decode(uriInfo, StandardCharsets.UTF_8.toString())
                        ImageRegisterScreen(uri = uriDecode, onClickCancel = {
                            navController.popBackStack()
                        })
                    }
                }
                composable(route = BottomNavItem.MyPage.route) {
                    MyPageScreen(
                        onClick = { voteId ->
                            navController.navigate(
                                "single_vote/$voteId"
                            )
                        },
                        onClickSetting = {
                            navController.navigate(
                                "setting"
                            )
                        },
                        onClickBookmark = {
                            navController.navigate(
                                "bookmark"
                            )
                        },
                        goToDeleteMyVote = {
                            navController.navigate(
                                "delete_my_vote"
                            )
                        }
                    )
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
                                navController.navigate("set_profile")
                            })
                    }
                }
                composable(route = "set_profile") {
                    Utils.ScreenTransition(navController = navController, route = "set_profile") {
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
                composable(route = "single_vote/{voteId}") { backStackEntry ->
                    val voteId = backStackEntry.arguments?.getString("voteId")
                    if (voteId != null) {
                        SingleVoteScreen(voteId = voteId)
                    }
                }
                composable(route = "bookmark") {
                    Utils.ScreenTransition(navController = navController, route = "bookmark") {
                        BookMarkScreen(
                            onClickBack = {
                                navController.popBackStack()
                            },
                            onClickItem = { id ->
                                navController.navigate(
                                    "single_vote/$id"
                                )
                            }
                        )
                    }
                }
                composable(route = "setting") {
                    SettingScreen(
                        onClickBack = {
                            navController.popBackStack()
                        },
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
                        moveToEditInfo = {
                            navController.navigate("edit_my_info")
                        },
                        moveToUserBan = {
                            navController.navigate("ban_list")
                        }
                    )
                }
                composable(route = "edit_my_info") {
                    Utils.ScreenTransition(navController = navController, route = "edit_my_info") {
                        EditMyInfoScreen(
                            goToLogin = {
                                navController.navigate("login") {
                                    // 전체 내비게이션 스택을 지움
                                    popUpTo(0) { inclusive = true }
                                    // 새 목적지를 스택의 맨 위에 단일 인스턴스로 시작함
                                    launchSingleTop = true
                                    // 이전 상태를 복원하지 않음
                                    restoreState = true
                                }
                            },
                            goBack = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
                composable(route = "ban_list") {
                    Utils.ScreenTransition(navController = navController, route = "ban_list") {
                        BanListScreen(
                            onClickBack = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
                composable(route = "delete_my_vote") {
                    DeleteMyVoteScreen(onClickBack = {
                        navController.popBackStack()
                    })
                }
            }
        }
    }
}