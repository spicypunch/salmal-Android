package kr.jm.salmal_android.data.domain

import kr.lifesemantics.salmal_android.R

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val basicIcon: Int,
    val selectedIcon: Int,
) {
    data object Home : BottomNavItem(route = "home", title = "Home", basicIcon = R.drawable.home_icon, selectedIcon = R.drawable.home_filled_icon)
    data object Register : BottomNavItem(route = "register", title = "Register", basicIcon = R.drawable.add_btn, selectedIcon = R.drawable.add_btn)
    data object MyPage : BottomNavItem(route = "myPage", title = "MyPage", basicIcon = R.drawable.mypage_icon, selectedIcon = R.drawable.mypage_filled_icon)
}