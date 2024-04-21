package kr.jm.salmal_android

import android.app.Application
import android.util.Log
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, "5d5170916ea2715b891b88b5dc7cba0f")
        Log.e(" ",Utility.getKeyHash(this))
    }
}