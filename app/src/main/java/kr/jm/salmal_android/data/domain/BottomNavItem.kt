package kr.jm.salmal_android.data.domain

import kr.lifesemantics.salmal_android.R

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: Int
) {
    data object Home : BottomNavItem(route = "home", title = "Home", icon = R.drawable.home_false)
    data object Add : BottomNavItem(route = "add", title = "Add", icon = R.drawable.add_btn)
    data object MyPage : BottomNavItem(route = "myPage", title = "MyPage", icon = R.drawable.mypage_false)
}